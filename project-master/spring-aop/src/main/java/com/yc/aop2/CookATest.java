package com.yc.aop2;

import com.yc.aop1.HelloWorld;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 应用模块名称<p>
 * 代码描述<p>
 * Copyright: Copyright (C) 2020 XXX, Inc. All rights reserved. <p>
 *
 * @author yuche
 * @since 2020/3/21 14:18
 */
public class CookATest {
    public static void main(String[] args) {

        ApplicationContext ac = new ClassPathXmlApplicationContext("aop.xml");
        Cook cookA = (Cook) ac.getBean("cookA");
        cookA.make();
        cookA.make("煮面条");
    }
}
