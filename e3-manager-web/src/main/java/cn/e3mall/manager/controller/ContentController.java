package cn.e3mall.manager.controller;

import cn.e3mall.cms.service.ContentService;
import cn.e3mall.common.po.DatagridResult;
import cn.e3mall.common.po.E3Result;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.manager.po.TbContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by wangji on 2017/1/16.
 */
@Controller
@RequestMapping("/content")
public class ContentController {
    @Autowired
    private ContentService service;

    @RequestMapping("/query/list")
    @ResponseBody
    public DatagridResult queryContentList(@RequestParam(defaultValue = "1") Integer page, Integer rows, Long categoryId) {
        return service.queryContentList(page, rows, categoryId);
    }

    @RequestMapping("/save")
    @ResponseBody
    public E3Result save(TbContent content) {
        return service.save(content);
    }

    @RequestMapping("/edit")
    @ResponseBody
    public E3Result edit(TbContent content) {
        return service.update(content);
    }

    @RequestMapping("/delete")
    @ResponseBody
    public E3Result delete(Long[] ids) {
        E3Result e3result = service.deleteByIds(ids);
        return e3result;
    }
}
