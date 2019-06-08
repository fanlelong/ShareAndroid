package com.ancely.share.bean;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/*
 *  @项目名：  ShareAndroid
 *  @包名：    com.ancely.share.bean
 *  @文件名:   SearchHotBean
 *  @创建者:   fanlelong
 *  @创建时间:  2019/6/5 4:19 PM
 *  @描述：    热门搜索
 */
@Entity(tableName = "hot_tips")
public class HotTipsBean {
    /**
     * id : 6
     * link :
     * name : 面试
     * order : 1
     * visible : 1
     */

    @PrimaryKey(autoGenerate = true)
    public long hotTip;
    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "link")
    private String link;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "order")
    private int order;
    @ColumnInfo(name = "visible")
    private int visible;

    @ColumnInfo(name = "link_type")
    private int linkType;

    public int getLinkType() {
        return linkType;
    }

    public void setLinkType(int linkType) {
        this.linkType = linkType;
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

    public int getVisible() {
        return visible;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }
}
