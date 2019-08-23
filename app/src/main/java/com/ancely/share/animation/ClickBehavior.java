package com.ancely.share.animation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
 *  @项目名：  ShareAndroid
 *  @包名：    com.ancely.share.animation
 *  @文件名:   ClickBehavior
 *  @创建者:   fanlelong
 *  @创建时间:  2019/7/15 4:58 PM
 *  @描述：    用户点击行为统计
 */
@Target(ElementType.METHOD)//作用在方法之上
@Retention(RetentionPolicy.RUNTIME)
public @interface ClickBehavior {

    String value();//
}
