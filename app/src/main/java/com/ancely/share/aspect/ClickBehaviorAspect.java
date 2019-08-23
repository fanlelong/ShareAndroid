package com.ancely.share.aspect;

import android.util.Log;

import com.ancely.share.animation.ClickBehavior;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

/*
 *  @项目名：  ShareAndroid
 *  @包名：    com.ancely.share.aspect
 *  @文件名:   ClickBehaviorAspect
 *  @创建者:   fanlelong
 *  @创建时间:  2019/7/15 5:02 PM
 *  @描述：    AOP
 */
@Aspect//定义一个切面类
public class ClickBehaviorAspect {

    //execution 以方法执行时,进行切入点,触发aspect
    //应用中用到哪些注解,就作用在他身上

    //处理ClickBehavior这个类所有的方法
    @Pointcut("execution(@com.ancely.share.animation.ClickBehavior * *(..))")
    public void behaviorPointcut() {

    }

    //对这些切入点进入处理
    @Around("behaviorPointcut()")
    public Object jointPoint(final ProceedingJoinPoint joinPoint) throws Throwable {
        //获取签名方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        //获取方法类名
        String className = signature.getDeclaringType().getSimpleName();
        //获取方法名
        String methodName = signature.getName();
        //获取方法的注解值
        String funName = signature.getMethod().getAnnotation(ClickBehavior.class).value();

        //统计方法的执行时间
        long time = System.currentTimeMillis();
        //统计用户点击一些功能的行为
        Object proceed = joinPoint.proceed();
        long finishTime = System.currentTimeMillis() - time;
        Log.e("behaviorPointcut", "className: " + className);
        Log.e("behaviorPointcut", "methodName: " + methodName);
        Log.e("behaviorPointcut", "funName: " + funName);
        Log.e("behaviorPointcut", "finishTime: " + finishTime);
        return proceed;
    }
}
