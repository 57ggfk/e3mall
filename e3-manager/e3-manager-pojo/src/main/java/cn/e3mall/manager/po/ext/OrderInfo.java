package cn.e3mall.manager.po.ext;

import cn.e3mall.manager.po.TbOrder;
import cn.e3mall.manager.po.TbOrderItem;
import cn.e3mall.manager.po.TbOrderShipping;

import java.util.List;

/**
 * Created by wangji on 2017/2/9.
 */
public class OrderInfo extends TbOrder {

    private List<TbOrderItem> orderItems;

    private TbOrderShipping orderShipping;

    public List<TbOrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<TbOrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public TbOrderShipping getOrderShipping() {
        return orderShipping;
    }

    public void setOrderShipping(TbOrderShipping orderShipping) {
        this.orderShipping = orderShipping;
    }
}
