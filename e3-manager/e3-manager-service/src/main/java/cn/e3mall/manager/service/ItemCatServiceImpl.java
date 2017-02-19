package cn.e3mall.manager.service;

import cn.e3mall.common.po.TreeNodeResult;
import cn.e3mall.manager.mapper.TbItemCatMapper;
import cn.e3mall.manager.mapper.TbItemMapper;
import cn.e3mall.manager.po.TbItem;
import cn.e3mall.manager.po.TbItemCat;
import cn.e3mall.manager.po.TbItemCatExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangji on 2017/1/12.
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {
    @Autowired
    private TbItemCatMapper mapper;

    @Override
    public List<TreeNodeResult> queryItemCatList(Long parentId) {
        if (parentId == null) {
            parentId = 0L;
            //return null;
        }
        // 根据父节点id，查询子节点列表
        TbItemCatExample example = new TbItemCatExample();

        TbItemCatExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);

        List<TbItemCat> tbItemCats = mapper.selectByExample(example);

        // 封装查询结果
        List<TreeNodeResult> results = new ArrayList<>();
        for (TbItemCat tbItemCat : tbItemCats) {

            TreeNodeResult result = new TreeNodeResult();
            result.setId(tbItemCat.getId());
            result.setText(tbItemCat.getName());
            if (tbItemCat.getIsParent()) {
                // 父节点，状态是封闭的
                result.setState("closed");
            } else {
                // 子节点，状态是开放的
                result.setState("open");
            }
            results.add(result);
        }
        return results;
    }
}
