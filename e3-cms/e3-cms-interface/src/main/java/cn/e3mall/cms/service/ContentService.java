package cn.e3mall.cms.service;

import cn.e3mall.common.po.DatagridResult;
import cn.e3mall.common.po.E3Result;
import cn.e3mall.manager.po.TbContent;

import java.util.List;

/**
 * Created by wangji on 2017/1/16.
 */
public interface ContentService {
    public DatagridResult queryContentList(Integer page, Integer rows, Long categoryId);
    public E3Result save(TbContent content);
    public E3Result update(TbContent content);
    public E3Result delete(Long id);
    public E3Result deleteByIds(Long[] ids);
    public List<TbContent> queryContentListByCat(Long categoryId);
}
