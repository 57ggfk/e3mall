package cn.e3mall.common.po;

import java.io.Serializable;

/**
 * Created by wangji on 2017/1/11.
 */
public class TreeNodeResult implements Serializable{

    final public static String IS_PARENT = "closed";
    final public static String IS_NOT_PARENT = "open";

    // 节点id
    private Long id;
    // 节点文本
    private String text;
    // 节点状态 open 默认 closed 不自动展开改节点
    private String state = "open";

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
