package cn.e3mall.search.service;

import cn.e3mall.common.po.E3Result;
import cn.e3mall.manager.po.ext.TbItemExt;

import java.io.IOException;
import java.util.Map;

/**
 * Created by wangji on 2017/1/17.
 */
public interface SearchService {
    public E3Result importAll();
    public Map<String, Object> search(String q, Integer page) throws Exception;
}
