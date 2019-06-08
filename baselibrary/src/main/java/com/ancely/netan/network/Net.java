package com.ancely.netan.network;

/*
 *  @项目名：  ModlerApp
 *  @包名：    luckly.ancely.com.network
 *  @文件名:   Network
 *  @创建者:   fanlelong
 *  @创建时间:  2019/2/27 4:00 PM
 *  @描述：    TODO
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)// 在运行进通过反身获取
public @interface Net {
    NetType netType() default NetType.AUTO;

    boolean isManThread() default false;
}
