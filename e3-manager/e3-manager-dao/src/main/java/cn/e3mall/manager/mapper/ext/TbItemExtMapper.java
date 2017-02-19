package cn.e3mall.manager.mapper.ext;

import cn.e3mall.manager.mapper.TbItemMapper;
import cn.e3mall.manager.po.TbItem;
import cn.e3mall.manager.po.ext.TbItemExt;

import java.util.List;

/**
 * Created by wangji on 2017/1/17.
 */
public interface TbItemExtMapper {

    public List<TbItemExt> queryItemAll();
    public TbItemExt queryItemById(Long id);
}
