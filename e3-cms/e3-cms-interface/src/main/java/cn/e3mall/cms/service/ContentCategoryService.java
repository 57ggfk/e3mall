package cn.e3mall.cms.service;

import cn.e3mall.common.po.E3Result;
import cn.e3mall.common.po.TreeNodeResult;

import java.util.List;

/**
 * Created by wangji on 2017/1/16.
 */
public interface ContentCategoryService {
    List<TreeNodeResult> queryCategoryList(Long parentId);
    E3Result insert(Long parentId, String name);
    E3Result update(Long id, String name);
    E3Result delete(Long parentId,Long id);
}
