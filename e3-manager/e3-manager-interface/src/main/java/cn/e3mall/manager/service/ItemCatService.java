package cn.e3mall.manager.service;

import cn.e3mall.common.po.TreeNodeResult;
import cn.e3mall.manager.po.TbItemCat;

import java.util.List;

/**
 * Created by wangji on 2017/1/11.
 */
public interface ItemCatService {
    /**
     * 根据父节点id查询所有的子节点
     * @param parentId
     * @return
     */
    public List<TreeNodeResult> queryItemCatList(Long parentId);
}
