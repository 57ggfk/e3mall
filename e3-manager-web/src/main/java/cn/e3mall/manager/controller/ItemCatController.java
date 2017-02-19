package cn.e3mall.manager.controller;

import cn.e3mall.common.po.TreeNodeResult;
import cn.e3mall.manager.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by wangji on 2017/1/12.
 */
@Controller
@RequestMapping("/itemCat")
public class ItemCatController {
    @Autowired
    private ItemCatService service;

    @RequestMapping("/list")
    @ResponseBody
    public List<TreeNodeResult> queryItemCatList(@RequestParam(value="id", defaultValue = "0") Long parentId) {
        List<TreeNodeResult> results = service.queryItemCatList(parentId);
        return results;
    }
}
