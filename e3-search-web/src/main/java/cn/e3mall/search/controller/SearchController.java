package cn.e3mall.search.controller;

import cn.e3mall.search.service.SearchService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * Created by wangji on 2017/1/17.
 */
@Controller
public class SearchController {
    @Autowired
    private SearchService service;

    @RequestMapping("/search")
    public String search(@RequestParam(value = "q",required = false) String query, @RequestParam(defaultValue = "1") Integer page, Model model) {
        //调用service方法
        try {
            //problems of messy code by method of get
            if (StringUtils.isNotBlank(query)) {
                query = new String(query.getBytes("iso-8859-1"),"utf-8");
            }
            //invoke service to perform searching
            Map<String, Object> map = service.search(query, page);
            //基本类型不会自动回显到model
            model.addAttribute("query",query);
            model.addAttribute("page",page);
            //map类型批量存入model，key一致
            //model.addAttribute("totalPages",map.get("totalPages"));
            //model.addAttribute("totalRecords",map.get("totalRecords"));
            //model.addAttribute("itemList",map.get("itemList"));
            model.addAllAttributes(map);
        } catch (Exception e){
            model.addAttribute("message","sorry,I'm tired");
            return "error/exception";
        }
        return "search";
    }

}
