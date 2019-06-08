package com.ancely.share.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.ancely.share.bean.Article;

import java.util.List;

import io.reactivex.Flowable;
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
public interface ArticleDao {

    //简单sql语句，查询article表所有的column
    @Query("SELECT * FROM article")
    Flowable<List<Article>> getAll();

    @Query("SELECT * FROM article")
    Single<List<Article>> getArticleAll();

    @Query("SELECT * FROM article WHERE is_top = 1")
    Single<List<Article>> getArticleTopAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Article article);

    //返回List<Long>数据表示被插入数据的主键uid列表
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertAll(Article... users);

    //返回List<Long>数据表示被插入数据的主键uid列表
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertAll(List<Article> users);

    //---------------------update------------------------
    //更新已有数据，根据主键（uid）匹配，而非整个user对象
    //返回类型int代表更新的条目数目，而非主键uid的值。
    //表示更新了多少条目
    @Update()
    int update(Article user);

    @Update()
    int updateAll(Article... user);

    //同上
    @Update()
    int updateAll(List<Article> user);

    @Query("DELETE FROM article")
    void deleteAllArticle();
}
