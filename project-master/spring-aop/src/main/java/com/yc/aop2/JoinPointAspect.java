package com.yc.aop2;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * 应用模块名称<p>
 * 代码描述<p>
 * Copyright: Copyright (C) 2020 XXX, Inc. All rights reserved. <p>
 *
 * @author yuche
 * @since 2020/3/21 12:42
 */
@Aspect
public class JoinPointAspect {

    //找到
    @Around("within(com.yc.aop2.CookA)")
    public void test(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("获取连接点开始");
        System.out.println("参数：" + pjp.getArgs());
        System.out.println("对象：" + pjp.getTarget().getClass());
        pjp.proceed();
        System.out.println("获取连接点结束");

    }

}
