package cn.e3mall.item.controller;

import cn.e3mall.manager.po.TbItem;
import cn.e3mall.manager.po.ext.TbItemExt;
import cn.e3mall.manager.service.ItemService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by wangji on 2017/1/20.
 */
@Controller
@RequestMapping("/item")
public class ItemController {
    @Autowired
    private ItemService service;

    @RequestMapping("/{itemId}")
    public String showDetail(@PathVariable Long itemId, Model model) {
        TbItem item = service.queryItemById(itemId);

        TbItemExt itemExt = new TbItemExt();
        if (item != null) {
            BeanUtils.copyProperties(item, itemExt);
        }
        model.addAttribute("item", itemExt);
        return "item";
    }
}
