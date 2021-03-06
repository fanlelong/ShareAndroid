package com.ancely.share.bean;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/*
 *  @项目名：  ShareAndroid
 *  @包名：    com.ancely.share.bean
 *  @文件名:   HomeBanner
 *  @创建者:   fanlelong
 *  @创建时间:  2019/6/3 11:00 AM
 *  @描述：    首页BANNER
 */
@Entity(tableName = "home_banner")
public class HomeBanner {
    /**
     * desc : Android高级进阶直播课免费学习
     * <p>
     * id : 22
     * imagePath : https://wanandroid.com/blogimgs/fbed8f14-1043-4a43-a7ee-0651996f7c49.jpeg
     * isVisible : 1
     * order : 0
     * title : Android高级进阶直播课免费学习
     * type : 0
     * url : https://url.163.com/4bj
     */

    @ColumnInfo(name = "desc")
    private String desc;
    @PrimaryKey
    private int id;
    @ColumnInfo(name = "image_path")
    private String imagePath;
    @ColumnInfo(name = "isVisible")
    private int isVisible;
    @ColumnInfo(name = "order")
    private int order;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "type")
    private int type;
    @ColumnInfo(name = "url")
    private String url;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(int isVisible) {
        this.isVisible = isVisible;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
