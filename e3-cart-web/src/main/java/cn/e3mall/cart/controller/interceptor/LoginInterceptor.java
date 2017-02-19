package cn.e3mall.cart.controller.interceptor;

import cn.e3mall.cart.service.CartService;
import cn.e3mall.cart.utils.CookieUtils;
import cn.e3mall.common.po.E3Result;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.manager.po.TbUser;
import cn.e3mall.manager.po.ext.TbItemExt;
import cn.e3mall.sso.service.SsoService;
import com.alibaba.dubbo.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by wangji on 2017/2/8.
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Value("${E3_TOKEN:E3_TOKEN")
    private String E3_TOKEN;
    @Value("${REQUEST_USER_NAME:user}")
    private String REQUEST_USER_NAME;
    @Value("${COOKIE_CART_NAME:COOKIE_CART}")
    private String COOKIE_CART_NAME;

    @Autowired
    private SsoService ssoService;
    @Autowired
    private CartService cartService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        //获取登录用户token值
        String token = CookieUtils.getCookieValue(request, E3_TOKEN);

        //如果为空，说明没有登录，则放行（将购物车添加到cookie中）
        if (StringUtils.isBlank(token)) {
            return true;
        }
        //如果登录用户为空，则说明登录超时,需要放行
        E3Result result = ssoService.loginCheck(token);
        if (result.getData() == null) {
            return true;
        }

        //登录成功，需要将用户信息写入request域中，key为REQUEST_USER_NAME的值,value为user对象
        //目的是在Controller中获取
        TbUser user = (TbUser) result.getData();
        request.setAttribute(REQUEST_USER_NAME,user);

        //登录成功，需要在第一次拦截时，合并cookie购物车和redis购物车
        String cookieValue = CookieUtils.getCookieValue(request, COOKIE_CART_NAME, true);
        if (StringUtils.isNotEmpty(cookieValue)) {
            List<TbItemExt> itemList = JsonUtils.jsonToList(cookieValue, TbItemExt.class);
            //合并购物车
            cartService.mergeCart(user.getId(), itemList);
            //合并购物车成功之后，清空cookie中的购物车
            CookieUtils.deleteCookie(request,response,COOKIE_CART_NAME);
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
