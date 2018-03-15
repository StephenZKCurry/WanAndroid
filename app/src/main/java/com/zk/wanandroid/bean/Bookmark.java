package com.zk.wanandroid.bean;

/**
 * @description: 我的书签实体类
 * @author: zhukai
 * @date: 2018/3/15 9:53
 */
public class Bookmark {

    /**
     * desc :
     * icon :
     * id : 282
     * link : https://www.baidu.com
     * name : 百度
     * order : 0
     * userId : 3753
     * visible : 1
     */
    private String desc;
    private String icon;
    private int id;
    private String link;
    private String name;
    private int order;
    private int userId;
    private int visible;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getVisible() {
        return visible;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }
}
