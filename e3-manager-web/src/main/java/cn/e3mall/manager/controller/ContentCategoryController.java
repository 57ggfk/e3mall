package cn.e3mall.manager.controller;

import cn.e3mall.cms.service.ContentCategoryService;
import cn.e3mall.common.po.E3Result;
import cn.e3mall.common.po.TreeNodeResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.swing.tree.TreeNode;
import java.util.List;

/**
 * Created by wangji on 2017/1/16.
 */
@Controller
@RequestMapping("/contentCategory")
public class ContentCategoryController {

    @Autowired
    private ContentCategoryService service;

    @RequestMapping("/list")
    @ResponseBody
    public List<TreeNodeResult> list(@RequestParam(value = "id",defaultValue = "0") Long parentId) {
        System.err.println("pring this");
        return service.queryCategoryList(parentId);
    }

    @RequestMapping("create")
    @ResponseBody
    public E3Result create(@RequestParam(value = "parentId", defaultValue = "0") Long parentId, String name) {
        return service.insert(parentId,name);
    }

    @RequestMapping("update")
    @ResponseBody
    public E3Result update(Long id, String name) {
        return service.update(id,name);
    }

    @RequestMapping("delete")
    @ResponseBody
    public E3Result delete(Long parentId,Long id) {
        return service.delete(parentId,id);
    }
}
