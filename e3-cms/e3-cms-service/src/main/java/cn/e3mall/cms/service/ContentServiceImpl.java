package cn.e3mall.cms.service;

import cn.e3mall.common.po.DatagridResult;
import cn.e3mall.common.po.E3Result;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.manager.mapper.TbContentMapper;import cn.e3mall.manager.po.TbContent;
import cn.e3mall.manager.po.TbContentExample;
import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

import java.util.Date;
import java.util.List;

/**
 * Created by wangji on 2017/1/16.
 */
@Service
public class ContentServiceImpl implements ContentService {
    @Autowired
    private TbContentMapper mapper;
    @Autowired
    private JedisCluster cluster;
    @Value("${REDIS_CONTENT_KEY:content}")
    private String REDIS_CONTENT_KEY;

    @Override
    public DatagridResult queryContentList(Integer page, Integer rows, Long categoryId) {

        //启用分页
        PageHelper.startPage(page,rows);

        //查询
        TbContentExample example = new TbContentExample();

        example.createCriteria().andCategoryIdEqualTo(categoryId);

        List<TbContent> list = mapper.selectByExample(example);

        //分页信息
        PageInfo<TbContent> pageInfo = new PageInfo(list);

        //封装到DatagridResult
        DatagridResult results = new DatagridResult();
        results.setTotal(pageInfo.getTotal());
        results.setRows(pageInfo.getList());
        return results;
    }

    @Override
    public E3Result save(TbContent content) {
        if(content==null) {
            return E3Result.build(400,"操作非法");
        }
        content.setId(null);
        Date date = new Date();
        content.setCreated(date);
        content.setUpdated(date);
        mapper.insert(content);
        return E3Result.ok(content);
    }

    @Override
    public E3Result update(TbContent content) {
        if(content==null) {
            return E3Result.build(400,"操作非法");
        }
        content.setUpdated(new Date());
        mapper.updateByPrimaryKeySelective(content);
        return E3Result.ok(content);
    }

    @Override
    public E3Result delete(Long id) {
        if (id==null) {
            return E3Result.build(400,"请选择要删除的内容");
        }
        mapper.deleteByPrimaryKey(id);
        return E3Result.ok();
    }

    @Override
    public E3Result deleteByIds(Long[] ids) {
        if(ids==null || ids.length==0) {
            return E3Result.build(400,"请选择要删除的内容");
        }
        int count = 0;
        for (Long id : ids) {
            if (id!=null) {
                int i = mapper.deleteByPrimaryKey(id);
                if (i>0) {
                    count += i;
                }
            }
        }
        if (count<1) {
            return E3Result.build(400,"要删除的内容不存在");
        }
        return E3Result.build(200,count+"");
    }

    @Override
    public List<TbContent> queryContentListByCat(Long categoryId) {
        if (categoryId==null) {
            return null;
        }
        List<TbContent> list = null;
        //先查询缓存
        String json = null;
        try {
            //Jedis正常开启的情况
            json = cluster.hget(REDIS_CONTENT_KEY, categoryId+"" );
            if (StringUtils.isNotEmpty(json)) {

                return JsonUtils.jsonToList(json,TbContent.class);

            } else {

                TbContentExample example = new TbContentExample();
                example.createCriteria().andCategoryIdEqualTo(categoryId);
                list = mapper.selectByExample(example);

                //保存到redis
                String jsonString = JsonUtils.objectToJson(list);
                try {
                    cluster.hset(REDIS_CONTENT_KEY, categoryId + "", jsonString);
                } catch (Exception e2) {
                    System.err.println(e2.getMessage());
                } finally {
                    return list;
                }

            }

        } catch (Exception e) {
            //Jedis出现异常的情况
            System.err.println(e.getMessage());
            TbContentExample example = new TbContentExample();
            example.createCriteria().andCategoryIdEqualTo(categoryId);
            list = mapper.selectByExample(example);
            return list;
        }
    }
}
