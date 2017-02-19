package cn.e3mall.cms.service;

import cn.e3mall.common.po.E3Result;
import cn.e3mall.common.po.TreeNodeResult;
import cn.e3mall.manager.mapper.TbContentCategoryMapper;
import cn.e3mall.manager.mapper.TbContentMapper;
import cn.e3mall.manager.po.TbContent;
import cn.e3mall.manager.po.TbContentCategory;
import cn.e3mall.manager.po.TbContentCategoryExample;
import cn.e3mall.manager.po.TbContentExample;
import com.alibaba.dubbo.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wangji on 2017/1/16.
 */
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {
    @Autowired
    private TbContentCategoryMapper mapper;
    @Autowired
    private TbContentMapper contentMapper;
    @Override
    public List<TreeNodeResult> queryCategoryList(Long parentId) {

        if (parentId == null ) {
            return null;
        }
        TbContentCategoryExample example = new TbContentCategoryExample();
        example.createCriteria().andParentIdEqualTo(parentId);
        List<TbContentCategory> list = mapper.selectByExample(example);
        List<TreeNodeResult> results = new ArrayList<>();

        for (TbContentCategory category : list) {
            TreeNodeResult result = new TreeNodeResult();
            result.setId(category.getId());
            result.setText(category.getName());
            //健壮性判断
            if (category.getIsParent() == null) {
                category.setIsParent(false);
            }
            if (category.getIsParent()) {
                //如果是父节点状态是closed
                result.setState(TreeNodeResult.IS_PARENT);
            } else {
                //如果是子节点状态是open
                result.setState(TreeNodeResult.IS_NOT_PARENT);
            }
            results.add(result);
        }
        return results;
    }

    @Override
    public E3Result insert(Long parentId, String name) {
        if (parentId == null || parentId == 0L) {
            return E3Result.build(400,"请选择有效的父层分类");
        }
        if (StringUtils.isBlank(name)) {
            return E3Result.build(400,"请输入分类名称");
        }
        TbContentCategory category = new TbContentCategory();
        category.setName(name);
        category.setParentId(parentId);
        category.setIsParent(false); //新插入的绝对不是父节点
        category.setSortOrder(1);
        category.setStatus(1);
        Date date = new Date();
        category.setCreated(date);
        category.setUpdated(date);

        mapper.insert(category);
        //这是它的父节点isParent为true
        TbContentCategory parent = new TbContentCategory();
        parent.setId(parentId);
        parent.setIsParent(true);
        mapper.updateByPrimaryKeySelective(parent);

        return E3Result.ok(category);
    }

    @Override
    public E3Result update(Long id, String name) {
        if (id == null || id == 0L) {
            return E3Result.build(400,"请选择要修改的分类");
        }
        if (StringUtils.isBlank(name)) {
            return E3Result.build(400, "请输入分类名称");
        }
        TbContentCategory category = mapper.selectByPrimaryKey(id);
        category.setName(name);
        category.setUpdated(new Date());
        mapper.updateByPrimaryKeySelective(category);
        return E3Result.ok(category);
    }

    @Override
    public E3Result delete(Long parentId, Long id) {
        if (id == null || id == 0L) {
            return E3Result.build(400, "请选择要删除的分类");
        }

        TbContentCategory category = mapper.selectByPrimaryKey(id);
        if (category == null) {
            return E3Result.build(404,"要删除的分类已不存在");
        }
        if (category.getParentId() == 0L) {
            return E3Result.build(400, "根分类不允许删除");
        }

        //判断有没有子分类
        TbContentCategoryExample example = new TbContentCategoryExample();
        example.createCriteria().andParentIdEqualTo(id);
        int count = mapper.countByExample(example);
        if (count>0) {
            return E3Result.build(400,"该分类下存在子分类，不允许删除");
        }

        //判断分类下有没有内容
        TbContentExample contentExample = new TbContentExample();
        contentExample.createCriteria().andCategoryIdEqualTo(id);
        int countContent = contentMapper.countByExample(contentExample);
        if (countContent>0) {
            return E3Result.build(400,"该分类下存在内容，不允许删除");
        }

        mapper.deleteByPrimaryKey(id);
        return E3Result.ok();
    }


}
