package cn.e3mall.manager.controller;

import cn.e3mall.common.po.E3Result;
import cn.e3mall.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by wangji on 2017/1/17.
 */
@Controller
@RequestMapping("/index")
public class IndexController {
    @Autowired
    private SearchService service;

    @RequestMapping("/importAll")
    @ResponseBody
    public E3Result importAll() {
        E3Result result = null;
        try {
            //如果操作超时，会报异常
            result = service.importAll();
        } catch (Exception e) {
            result = E3Result.ok();
            e.printStackTrace();
        }
        return result;
    }
}
