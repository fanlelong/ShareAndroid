package com.ancely.share.bean;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.List;

/*
 *  @项目名：  ShareAndroid
 *  @包名：    com.ancely.share.bean
 *  @文件名:   Article
 *  @创建者:   fanlelong
 *  @创建时间:  2019/6/2 3:55 PM
 *  @描述：    文章列表
 */
@Entity(tableName = "article")
public class Article {
    @ColumnInfo(name = "apk_link")
    private String apkLink;
    @ColumnInfo(name = "author")
    private String author;
    @ColumnInfo(name = "chapter_id")
    private int chapterId;
    @ColumnInfo(name = "chapter_name")
    private String chapterName;
    @Ignore
    private boolean collect;
    @ColumnInfo(name = "courseId")
    private int courseId;
    @ColumnInfo(name = "desc")
    private String desc;
    @ColumnInfo(name = "envelopePic")
    private String envelopePic;
    @ColumnInfo(name = "fresh")
    private boolean fresh;//是否新文章
    @PrimaryKey
    private int id;
    @ColumnInfo(name = "link")
    private String link;
    @ColumnInfo(name = "nic_date")
    private String niceDate;
    @ColumnInfo(name = "origin")
    private String origin;
    @ColumnInfo(name = "prefix")
    private String prefix;
    @ColumnInfo(name = "project_link")
    private String projectLink;
    @ColumnInfo(name = "publish_time")
    private long publishTime;
    @ColumnInfo(name = "super_chapter_id")
    private int superChapterId;
    @ColumnInfo(name = "super_chapter_name")
    private String superChapterName;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "type")
    private int type;
    @ColumnInfo(name = "user_id")
    private int userId;
    @ColumnInfo(name = "visible")
    private int visible;
    @ColumnInfo(name = "zan")
    private int zan;
    @Ignore
    private List<Tags> tags;
    @ColumnInfo(name = "is_top")
    private boolean isTop;

    public void setTop(boolean top) {
        isTop = top;
    }

    public boolean isTop() {
        return isTop;
    }

    public String getApkLink() {
        return apkLink;
    }

    public void setApkLink(String apkLink) {
        this.apkLink = apkLink;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public boolean isCollect() {
        return collect;
    }

    public void setCollect(boolean collect) {
        this.collect = collect;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getEnvelopePic() {
        return envelopePic;
    }

    public void setEnvelopePic(String envelopePic) {
        this.envelopePic = envelopePic;
    }

    public boolean isFresh() {
        return fresh;
    }

    public void setFresh(boolean fresh) {
        this.fresh = fresh;
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

    public String getNiceDate() {
        return niceDate;
    }

    public void setNiceDate(String niceDate) {
        this.niceDate = niceDate;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getProjectLink() {
        return projectLink;
    }

    public void setProjectLink(String projectLink) {
        this.projectLink = projectLink;
    }

    public long getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(long publishTime) {
        this.publishTime = publishTime;
    }

    public int getSuperChapterId() {
        return superChapterId;
    }

    public void setSuperChapterId(int superChapterId) {
        this.superChapterId = superChapterId;
    }

    public String getSuperChapterName() {
        return superChapterName;
    }

    public void setSuperChapterName(String superChapterName) {
        this.superChapterName = superChapterName;
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

    public int getZan() {
        return zan;
    }

    public void setZan(int zan) {
        this.zan = zan;
    }

    public List<Tags> getTags() {
        return tags;
    }

    public void setTags(List<Tags> tags) {
        this.tags = tags;
    }

    public static class Tags {
        private String name;
        private String url;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
