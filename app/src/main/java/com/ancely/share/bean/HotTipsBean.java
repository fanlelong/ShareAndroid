package com.ancely.share.bean;

/*
 *  @项目名：  ShareAndroid
 *  @包名：    com.ancely.share.bean
 *  @文件名:   SearchHotBean
 *  @创建者:   fanlelong
 *  @创建时间:  2019/6/5 4:19 PM
 *  @描述：    热门搜索
 */
public class HotTipsBean {
    /**
     * id : 6
     * link :
     * name : 面试
     * order : 1
     * visible : 1
     */

    private int id;
    private String link;
    private String name;
    private int order;
    private int visible;

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

    public int getVisible() {
        return visible;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }
}
