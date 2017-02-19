package cn.e3mall.manager.po.ext;

import cn.e3mall.manager.po.TbItem;

/**
 * Created by wangji on 2017/1/17.
 */
public class TbItemExt extends TbItem {
    private String catName;
    private String[] images;

    public String[] getImages() {
        if (this.images !=null && images.length>0) {
            return this.images;
        }
        if (this.getImage() == null) {
            //设置一个图片默认值
            return new String[]{"www.baidu.com/"};
        }
        return this.getImage().split(",");
    }

    public void setImages(String[] images) {
        this.images = images;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }
}
