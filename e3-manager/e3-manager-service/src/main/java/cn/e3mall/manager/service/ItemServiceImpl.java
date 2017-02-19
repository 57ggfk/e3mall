package cn.e3mall.manager.service;

import cn.e3mall.common.po.DatagridResult;
import cn.e3mall.common.po.E3Result;
import cn.e3mall.common.utils.IDUtils;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.manager.mapper.TbItemMapper;
import cn.e3mall.manager.po.TbItem;
import cn.e3mall.manager.po.TbItemExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import javax.jms.*;
import java.util.Date;
import java.util.List;

/**
 * Created by wangji on 2017/1/10.
 */
@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private TbItemMapper itemMapper;
    //注入消息订阅
    @Autowired
    private JmsTemplate jmsTemplate;
    //注入消息主题
    @Resource(name = "itemTopic")
    private Destination itemTopic;
    //Redis连接池
    @Autowired
    private JedisPool pool;
    @Autowired
    private JedisCluster cluster;

    //商品信息在Redis中的key名前缀
    @Value(value = "${REDIS_KEY_ITEM_PRE:item:}")
    private String REDIS_KEY_ITEM_PRE;
    //商品信息在Redis中的有效期秒数
    @Value(value = "${REDIS_ITEM_EXPIRE_SECOND:86400}")
    private int REDIS_ITEM_EXPIRE_SECOND;

    @Override
    public TbItem queryItemById(Long itemId) {
        if (itemId ==null) {
            return null;
        }
        //获取jedis
        //Jedis jedis =  null;
        JedisCluster jedis = null;
        try {
            //jedis = pool.getResource();
            jedis = cluster;
        } catch (Exception e) {
            System.err.println("redis connection exception: "+e.getMessage());
        }
        //查询缓存
        if (jedis != null) {
            String itemJson;
            try {

                itemJson = jedis.get(REDIS_KEY_ITEM_PRE+itemId);
                if (StringUtils.isNotBlank(itemJson)) {
                    TbItem item = JsonUtils.jsonToPojo(itemJson, TbItem.class);
                    return item;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //缓存没有则查询数据库
        TbItem item = itemMapper.selectByPrimaryKey(itemId);
        //如果查询到商品信息就存入缓存
        if (jedis != null && item != null) {
            try {

                jedis.set(REDIS_KEY_ITEM_PRE+itemId,JsonUtils.objectToJson(item));
                jedis.expire(REDIS_KEY_ITEM_PRE+itemId,REDIS_ITEM_EXPIRE_SECOND);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return item;
    }

    @Override
    public DatagridResult queryItemList(Integer page, Integer rows) {
        if (page==null) {
            page = 1;
        }
        if (rows==null) {
            rows = 30;
        }
        // 初始化分页信息
        PageHelper.startPage(page,rows);

        // 执行查询方法，暂时没有查询条件
        TbItemExample example = new TbItemExample();
        List<TbItem> list = itemMapper.selectByExample(example);

        // 封装PageInfo对象
        PageInfo<TbItem> pageInfo = new PageInfo<TbItem>(list);

        // 封装返回对象DatagridResult
        DatagridResult result = new DatagridResult();
        result.setRows(list);
        result.setTotal(pageInfo.getTotal());
        return result;

    }

    @Override
    public E3Result addItem(TbItem item) {
        final long itemId = IDUtils.genItemId();

        item.setId(itemId);         //设置商品id
        item.setStatus((byte)1);   //设置商品状态 1-正常，2-下架，0-删除
        Date date = new Date();
        item.setCreated(date);
        item.setUpdated(date);

        itemMapper.insert(item);
        // 通过ActiveMQ发送消息
        jmsTemplate.send(itemTopic, new MessageCreator() {

            @Override
            public Message createMessage(Session session) throws JMSException {
                // 将商品ID发送出去
                TextMessage message = session.createTextMessage(itemId + "");
                return message;
            }
        });

        return E3Result.ok();

    }
}
