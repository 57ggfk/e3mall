package cn.e3mall.manager.controller;

import cn.e3mall.common.po.DatagridResult;
import cn.e3mall.common.po.E3Result;
import cn.e3mall.manager.po.TbItem;
import cn.e3mall.manager.service.ItemCatService;
import cn.e3mall.manager.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by wangji on 2017/1/10.
 */
@Controller
@RequestMapping("item")
public class ItemController {
    @Autowired
    private ItemService itemService;
    @RequestMapping("/test/{itemId}")
    @ResponseBody
    public TbItem queryItemById(@PathVariable Long itemId) {
        return itemService.queryItemById(itemId);
    }

    @RequestMapping("/list")
    @ResponseBody
    public DatagridResult list(@RequestParam(value = "page",defaultValue = "1") Integer page, @RequestParam(value = "rows",defaultValue = "30") Integer rows) {
        DatagridResult datagridResult = itemService.queryItemList(page, rows);
        return datagridResult;
    }

    @RequestMapping("/save")
    @ResponseBody
    public E3Result list(TbItem item) {
        return itemService.addItem(item);
    }
}
