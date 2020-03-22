package com.yc.aop1;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 应用模块名称<p>
 * 代码描述<p>
 * Copyright: Copyright (C) 2020 XXX, Inc. All rights reserved. <p>
 *
 * @author yuche
 * @since 2020/3/13 13:03
 */
public class HelloWorldTest {
    public static void main(String[] args) {
        ApplicationContext ac = new ClassPathXmlApplicationContext("aop.xml");
        HelloWorld helloWorld1 = (HelloWorld) ac.getBean("helloWorldImpl1");
        HelloWorld helloWorld2 = (HelloWorld) ac.getBean("helloWorldImpl2");
        helloWorld1.pringHelloWorld();
        System.out.println();
        helloWorld1.doprint();
        System.out.println();
        helloWorld2.pringHelloWorld();
        System.out.println();
        helloWorld2.doprint();
    }
}
