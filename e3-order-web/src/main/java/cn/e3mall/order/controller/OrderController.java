package cn.e3mall.order.controller;

import cn.e3mall.cart.service.CartService;
import cn.e3mall.common.po.E3Result;
import cn.e3mall.manager.po.TbUser;
import cn.e3mall.manager.po.ext.OrderInfo;
import cn.e3mall.manager.po.ext.TbItemExt;
import cn.e3mall.order.service.OrderService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by wangji on 2017/2/9.
 */
@Controller
@RequestMapping("/order")
public class OrderController {

    @Value("${REQUEST_USER_NAME:user}")
    private String REQUEST_USER_NAME;

    @Autowired
    private CartService cartService;
    @Autowired
    private OrderService orderService;

    @RequestMapping("/order-cart")
    public String showOrderCart(HttpServletRequest request) {
        TbUser user = (TbUser) request.getAttribute(REQUEST_USER_NAME);
        List<TbItemExt> cartList = cartService.getCartListFromRedis(user.getId());
        request.setAttribute("cartList",cartList);
        return "order-cart";
    }

    @RequestMapping("/create")
    public String createOrderCart(OrderInfo orderInfo,HttpServletRequest request) {
        //设置用户ID
        TbUser user = (TbUser) request.getAttribute(REQUEST_USER_NAME);
        orderInfo.setUserId(user.getId());
        orderInfo.setBuyerNick(user.getUsername());
        E3Result result = orderService.saveOrder(orderInfo);
        //需要在Request域中设置orderId payment date
        if (result.getData() != null) {
            request.setAttribute("orderId", result.getData());
        }
        request.setAttribute("payment",orderInfo.getPayment());

        //计算预计时间
        DateTime dateTime = new DateTime();
        dateTime = dateTime.plusDays(3);

        request.setAttribute("date",dateTime.toString("yyyy-MM-dd"));

        return "order-create";
    }
}
