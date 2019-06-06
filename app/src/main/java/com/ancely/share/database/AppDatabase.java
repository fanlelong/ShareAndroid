package com.ancely.share.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.ancely.share.bean.Article;
import com.ancely.share.bean.HomeBanner;
import com.ancely.share.bean.HotTipsBean;
import com.ancely.share.database.dao.ArticleDao;
import com.ancely.share.database.dao.HomeBannerDao;
import com.ancely.share.database.dao.HotTipsDao;

//注解指定了database的表映射实体数据以及版本等信息
@Database(entities = {Article.class,HomeBanner.class,HotTipsBean.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase INSTANCE;

    //RoomDatabase提供直接访问底层数据库实现，我们通过定义抽象方法返回具体Dao
    //然后进行数据库增删该查的实现。
    public abstract ArticleDao articleDao();
    public abstract HomeBannerDao getBannerDao();
    public abstract HotTipsDao getHotTipsDao();

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "share_android.db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}