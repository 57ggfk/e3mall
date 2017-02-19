package cn.e3mall.sso.controller;

import cn.e3mall.common.po.E3Result;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.manager.po.TbUser;
import cn.e3mall.sso.service.SsoService;
import cn.e3mall.sso.utils.CookieUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by wangji on 2017/2/5.
 */
@Controller
@RequestMapping("/sso")
public class SsoController {
    @Autowired
    private SsoService service;

    /**
     * 显示注册页面
     */
    @RequestMapping("/showRegister")
    public String toRegisterPage() {
        return "register";
    }

    /**
     * 注册提交
     */
    @RequestMapping("/register")
    @ResponseBody
    public E3Result register(TbUser user) {
        return service.register(user);
    }

    /**
     * 显示登录页面
     */
    @RequestMapping("/showLogin")
    public String showLogin(String redirectUrl, Model model) {
        //如果有重定向url
        if (StringUtils.isNotBlank(redirectUrl)) {
            model.addAttribute("redirect",redirectUrl);
        }
        return "login";
    }

    @Value("${E3_TOKEN:E3_TOKEN}")
    private String E3_TOKEN;

    /**
     * 登录操作
     */
    @RequestMapping("/login")
    @ResponseBody
    public E3Result login(String username, String password, HttpServletRequest request, HttpServletResponse response) {
        E3Result result = service.login(username, password);
        if (result.getData() == null) {
            return result;
        }
        //登录成功写入cookie
        CookieUtils.setCookie(request,response,E3_TOKEN,result.getData().toString());

        return service.login(username,password);
    }

    /**
     * 检测登录
     */
    @RequestMapping("/token/{token}")
    @ResponseBody
    public String getUserByToken(@PathVariable String token,String callback){
        E3Result result = service.loginCheck(token);
        if (StringUtils.isBlank(callback)) {
            return JsonUtils.objectToJson(result);
        }
        return callback + "("+ JsonUtils.objectToJson(result) +")";
    }
}
