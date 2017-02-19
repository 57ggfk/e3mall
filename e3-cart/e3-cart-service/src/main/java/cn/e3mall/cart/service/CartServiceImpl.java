package cn.e3mall.cart.service;

import cn.e3mall.common.po.E3Result;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.manager.mapper.TbItemMapper;
import cn.e3mall.manager.po.TbItem;
import cn.e3mall.manager.po.ext.TbItemExt;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import redis.clients.jedis.JedisCluster;

import java.util.List;

/**
 * Created by wangji on 2017/2/8.
 */
public class CartServiceImpl implements CartService {

    @Value("${REDIS_CART_KEY_PRE:cart:}")
    private String REDIS_CART_KEY_PRE;
    @Value("${REDIS_CART_EXPIRE_SECOND:604800}")
    private int REDIS_CART_EXPIRE_SECOND;

    @Autowired
    private JedisCluster cluster;

    @Autowired
    private TbItemMapper itemMapper;

    @Override
    public List<TbItemExt> getCartListFromRedis(Long userId) {
        String json = cluster.get(REDIS_CART_KEY_PRE + userId);
        List<TbItemExt> itemList = null;
        if (StringUtils.isNotBlank(json)) {
            itemList = JsonUtils.jsonToList(json, TbItemExt.class);
        }
        return itemList;
    }

    @Override
    public E3Result mergeCart(Long userId, List<TbItemExt> itemList) {
        for (TbItemExt item : itemList) {
            addCart(userId,item.getId(),item.getNum());
        }
        return E3Result.ok();
    }

    @Override
    public E3Result addCart(Long userId, Long itemId, Integer num) {
        //获取购物车中的商品集合
        List<TbItemExt> cartList = getCartListFromRedis(userId);
        //是否包含此商品的标志位
        boolean flag = false;

        for (TbItemExt item : cartList) {
            if (item.getId().equals(itemId)) {
                //增加商品数量
                item.setNum(item.getNum()+num);
                flag = true;
                break;
            }
        }

        //如果不包含此商品就从数据库查询，然后添加到cartList
        if (!flag) {
            TbItem tbItem = itemMapper.selectByPrimaryKey(itemId);

            TbItemExt itemExt = new TbItemExt();
            BeanUtils.copyProperties(tbItem,itemExt);
            //不需要显示在购物车的商品属性设置为null
            itemExt.setDescription(null);

        }

        //把修改后的购物车集合存入Redis
        cluster.set(REDIS_CART_KEY_PRE+userId,JsonUtils.objectToJson(cartList));
        //设置有效期
        cluster.expire(REDIS_CART_KEY_PRE+userId,REDIS_CART_EXPIRE_SECOND);
        return E3Result.ok();
    }

    @Override
    public E3Result updateCartItem(Long userId, Long itemId, Integer num) {
        //获取商品集合
        String json = cluster.get(REDIS_CART_KEY_PRE + userId);
        List<TbItemExt> itemList = JsonUtils.jsonToList(json, TbItemExt.class);
        for (TbItemExt tbItemExt : itemList) {

            if (tbItemExt.getId().equals(itemId)) {
                tbItemExt.setNum(num);
            }

        }

        //修改后的购物车集合保存
        cluster.set(REDIS_CART_KEY_PRE+userId,JsonUtils.objectToJson(itemList));
        //重新设置有效期
        cluster.expire(REDIS_CART_KEY_PRE+userId,REDIS_CART_EXPIRE_SECOND);
        return E3Result.ok();
    }

    @Override
    public E3Result deleteCartItem(Long userId, Long itemId) {
        //获取购物车商品集合
        String json = cluster.get(REDIS_CART_KEY_PRE + userId);
        List<TbItemExt> itemList = JsonUtils.jsonToList(json, TbItemExt.class);
        for (TbItemExt tbItemExt : itemList) {

            if (tbItemExt.getId().equals(itemId)) {
                itemList.remove(tbItemExt);
            }

        }
        //保存修改后的购物车
        cluster.set(REDIS_CART_KEY_PRE+userId,JsonUtils.objectToJson(itemList));
        //重新设置有效期
        cluster.expire(REDIS_CART_KEY_PRE+userId,REDIS_CART_EXPIRE_SECOND);
        return E3Result.ok();
    }
}
