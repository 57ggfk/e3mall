package cn.e3mall.portal.controller;

import cn.e3mall.cms.service.ContentService;
import cn.e3mall.common.po.AdResult;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.manager.po.TbContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangji on 2017/1/16.
 */
@Controller
public class IndexController {
    @Autowired
    private ContentService service;

    @Value(value = "${AD1_CATEGORY_ID}")
    private Long AD1_CATEGORY_ID;
    @Value(value = "${AD1_HEIGHT}")
    private Integer AD1_HEIGHT;
    @Value(value = "${AD1_HEIGHTB}")
    private Integer AD1_HEIGHTB;
    @Value(value = "${AD1_WIDTH}")
    private Integer AD1_WIDTH;
    @Value(value = "${AD1_WIDTHB}")
    private Integer AD1_WIDTHB;

    @RequestMapping("/")
    public String index(Model model) {
        List<TbContent> list = service.queryContentListByCat(89L);
        List<AdResult> results = new ArrayList<>();
        AdResult result;
        for (TbContent content : list) {
            result = new AdResult();
            //广告信息
            result.setAlt(content.getTitle());
            result.setHref(content.getUrl());
            result.setSrc(content.getPic());
            result.setSrcB(content.getPic2());
            //页面信息
            result.setHeight(AD1_HEIGHT);
            result.setHeightB(AD1_HEIGHTB);
            result.setWidth(AD1_WIDTH);
            result.setWidthB(AD1_WIDTHB);
            results.add(result);
        }

        model.addAttribute("ad1",JsonUtils.objectToJson(results));
        return "index";
    }

}
