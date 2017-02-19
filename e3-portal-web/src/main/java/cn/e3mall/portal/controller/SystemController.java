package cn.e3mall.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by wangji on 2017/1/16.
 */
@Controller
@RequestMapping("/")
public class SystemController {

/*
    @RequestMapping("/")
    public String index() {
        return "index";
    }
*/

    @RequestMapping("/{jsp}")
    public String showJsp(@PathVariable String jsp) {
        return jsp;
    }

}
