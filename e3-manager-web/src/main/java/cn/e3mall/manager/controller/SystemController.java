package cn.e3mall.manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by wangji on 2017/1/11.
 */
@Controller
public class SystemController {
    @RequestMapping("/")
    public String index() {
        return "index";
    }

    /**
     * 目的是访问WEB-INF/jsp里的jsp文件
     * @param page
     * @return
     */
    @RequestMapping("/system/{page}")
    public String showPage(@PathVariable String page) {
        return page;
    }
}
