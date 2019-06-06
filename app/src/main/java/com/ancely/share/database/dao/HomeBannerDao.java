package com.ancely.share.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.ancely.share.bean.HomeBanner;

import java.util.List;

import io.reactivex.Single;

/*
 *  @项目名：  ShareAndroid
 *  @包名：    com.ancely.share.database.dao
 *  @文件名:   ArticleDao
 *  @创建者:   fanlelong
 *  @创建时间:  2019/6/6 10:39 AM
 *  @描述：    TODO
 */
@Dao
public interface HomeBannerDao {

    //简单sql语句，查询article表所有的column
    @Query("SELECT * FROM home_banner")
    Single<List<HomeBanner>> getBanners();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(HomeBanner article);

    //返回List<Long>数据表示被插入数据的主键uid列表
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertAll(HomeBanner... users);

    //返回List<Long>数据表示被插入数据的主键uid列表
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertAll(List<HomeBanner> users);

    //---------------------update------------------------
    //更新已有数据，根据主键（uid）匹配，而非整个user对象
    //返回类型int代表更新的条目数目，而非主键uid的值。
    //表示更新了多少条目
    @Update()
    int update(HomeBanner user);

    @Update()
    int updateAll(HomeBanner... user);

    //同上
    @Update()
    int updateAll(List<HomeBanner> user);

    @Query("DELETE FROM home_banner")
    void deleteAllArticle();
}
