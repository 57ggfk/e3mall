package cn.e3mall.cart.controller;

import cn.e3mall.cart.service.CartService;
import cn.e3mall.cart.utils.CookieUtils;
import cn.e3mall.common.po.E3Result;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.manager.po.TbItem;
import cn.e3mall.manager.po.TbUser;
import cn.e3mall.manager.po.ext.TbItemExt;
import cn.e3mall.manager.service.ItemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangji on 2017/2/6.
 */
@Controller
@RequestMapping("/cart")
public class CartController {

    @Value("${COOKIE_CART_NAME:COOKIE_CART}")
    private String COOKIE_CART_NAME;
    @Value("${E3_TOKEN:E3_TOKEN}")
    private String E3_TOKEN;
    @Value("${REQUEST_USER_NAME:user}")
    private String REQUEST_USER_NAME;

    @Autowired
    private ItemService itemService;

    @Autowired
    private CartService cartService;

    /**
     * 显示购物车
     */
    @RequestMapping("/showCart")
    public String showCart(HttpServletRequest request) {
        //获取登录的用户
        TbUser user = (TbUser) request.getAttribute(REQUEST_USER_NAME);

        List<TbItemExt> list;
        if (user == null) {
            //如果用户未登录就从Cookie中获取
            list = getCartListFromCookie(request);
        } else {
            // 如果用户登录就从Redis中获取
            list = cartService.getCartListFromRedis(user.getId());
        }

        //存入域中，页面读取
        request.setAttribute("cartList", list);

        return "cart";
    }

    /**
     * 从Cookie中获取购物车商品列表
     *
     * @param request
     * @return
     */
    private List<TbItemExt> getCartListFromCookie(HttpServletRequest request) {
        //从Cookie中取出购物车
        String cookieValue = CookieUtils.getCookieValue(request, COOKIE_CART_NAME, true);

        //获取cookie中的集合
        List<TbItemExt> list = new ArrayList<TbItemExt>();
        if (StringUtils.isNotBlank(cookieValue)) {
            list = JsonUtils.jsonToList(cookieValue, TbItemExt.class);
        }
        return list;
    }

    @RequestMapping("/add/{itemId}/{num}")
    public String addCart(@PathVariable Long itemId, @PathVariable Integer num,
                          HttpServletRequest request, HttpServletResponse response) {
        //获取登录的用户
        TbUser user = (TbUser) request.getAttribute(REQUEST_USER_NAME);
        if (user != null) {
            //如果用户登录，从Redis中添加
            E3Result result = cartService.addCart(user.getId(), itemId, num);
            if (result!=null) {
                if (result.getStatus()==E3Result.ok().getStatus()) {
                    return "cartSuccess";
                }
            }
        }

        //从cookie中取出购物车集合
        List<TbItemExt> cartList = getCartListFromCookie(request);
        if (cartList == null) {
            cartList = new ArrayList<TbItemExt>();
        }
        //如果购物车中有商品，数量追加
        boolean flag = false; //标志位，是否有商品
            for (TbItemExt item : cartList) {
                //引用类型使用equals判断
                if (itemId.equals(item.getId())) {
                    item.setNum(item.getNum() + num);
                    flag = true;
                    break;
                }
            }

        //如果购物车中没有，查询数据库，将查询结果添加到购物车中，并设置商品数量num
        if (!flag) {
            TbItem item = itemService.queryItemById(itemId);
            item.setNum(num);
            TbItemExt itemExt = new TbItemExt();
            BeanUtils.copyProperties(item, itemExt);
            //把不需要再购物车显示的属性设置为null
            itemExt.setDescription(null);

            cartList.add(itemExt);
        }

        //将修改后的购物车存入Cookie
        CookieUtils.setCookie(request, response, COOKIE_CART_NAME, JsonUtils.objectToJson(cartList), true);

        return "cartSuccess";
    }

    @RequestMapping("/update/num/{itemId}/{num}")
    public E3Result updateCartItem(@PathVariable Long itemId, @PathVariable Integer num,
                                   HttpServletRequest request, HttpServletResponse response) {
        //获取登录的用户
        TbUser user = (TbUser) request.getAttribute(REQUEST_USER_NAME);
        if (user != null) {
            //如果用户登录，修改Redis中的购物车信息
            E3Result result = cartService.updateCartItem(user.getId(),itemId,num);
            return result;
        }

        //修改Cookie中购物车指定商品的数量
        List<TbItemExt> cartList = getCartListFromCookie(request);
        for (TbItemExt itemExt : cartList) {
            if (itemExt.getId().equals(itemId)) {
                itemExt.setNum(itemExt.getNum() + num);
            }
        }

        //将修改的购物车存入Cookie
        CookieUtils.setCookie(request, response, COOKIE_CART_NAME, JsonUtils.objectToJson(cartList), true);

        return E3Result.ok();
    }

    @RequestMapping("/delete/{itemId}")
    public String deleteCartItem(@PathVariable Long itemId, HttpServletRequest request, HttpServletResponse response) {
        //获取登录的用户
        TbUser user = (TbUser) request.getAttribute(REQUEST_USER_NAME);
        if (user != null) {
            //如果用户登录，删除Redis中购物车商品
            E3Result result = cartService.deleteCartItem(user.getId(), itemId);
            if (result.getStatus() == E3Result.ok().getStatus()) {
                return "redirect:/cart/showCart";
            }
        }

        //从Cookie查找指定的商品
        List<TbItemExt> cartList = getCartListFromCookie(request);
        for (TbItemExt itemExt : cartList) {
            if (itemExt.getId().equals(itemId)) {
                cartList.remove(itemExt);
            }
        }

        //将修改后的购物车存入Cookie
        CookieUtils.setCookie(request, response, COOKIE_CART_NAME, JsonUtils.objectToJson(cartList), true);

        return "redirect:/cart/showCart";
    }


}
