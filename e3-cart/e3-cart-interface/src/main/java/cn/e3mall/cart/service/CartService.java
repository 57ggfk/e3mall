package cn.e3mall.cart.service;

import cn.e3mall.common.po.E3Result;
import cn.e3mall.manager.po.ext.TbItemExt;

import java.util.List;

/**
 * Created by wangji on 2017/2/8.
 */
public interface CartService {

    public List<TbItemExt> getCartListFromRedis(Long userId);

    public E3Result mergeCart(Long userId, List<TbItemExt> itemList);

    public E3Result addCart(Long userId, Long itemId, Integer num);

    public E3Result updateCartItem(Long userId, Long itemId, Integer num);

    public E3Result deleteCartItem(Long userId, Long itemId);
}
