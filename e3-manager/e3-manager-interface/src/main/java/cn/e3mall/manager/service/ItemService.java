package cn.e3mall.manager.service;

import cn.e3mall.common.po.DatagridResult;
import cn.e3mall.common.po.E3Result;
import cn.e3mall.manager.po.TbItem;

/**
 * Created by wangji on 2017/1/10.
 */
public interface ItemService {

    public TbItem queryItemById(Long itemId);

    public DatagridResult queryItemList(Integer page, Integer rows);

    public E3Result addItem(TbItem item);
}
