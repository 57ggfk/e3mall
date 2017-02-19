package cn.e3mall.order.controller.interceptor;

import cn.e3mall.common.po.E3Result;
import cn.e3mall.order.utils.CookieUtils;
import cn.e3mall.sso.service.SsoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by wangji on 2017/2/9.
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Value("${E3_TOKEN:E3_TOKEN}")
    private String E3_TOKEN;


    @Value("${REQUEST_USER_NAME:user}")
    private String REQUEST_USER_NAME;

    @Autowired
    private SsoService ssoService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取登录令牌Token
        String token = CookieUtils.getCookieValue(request, E3_TOKEN);

        // token为空，跳转到登录页面，带上确认url
        if (StringUtils.isBlank(token)) {
            String redirectUrl = request.getRequestURL().toString();
            response.sendRedirect(""+redirectUrl);
            return false;
        }

        // 查询用户
        E3Result result = ssoService.loginCheck(token);

        // 用户为空，跳转到登录页面，url
        if (result.getStatus() == E3Result.ok().getStatus()) {
            if (result.getData() == null) {
                String redirectUrl = request.getRequestURL().toString();
                response.sendRedirect(""+redirectUrl);
                return false;
            }
        }

        // 登录，用户写入request域中，
        request.setAttribute(REQUEST_USER_NAME,result.getData());
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
