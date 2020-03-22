package com.yc.aop1;

/**
 * 应用模块名称<p>
 * 代码描述<p>
 * Copyright: Copyright (C) 2020 XXX, Inc. All rights reserved. <p>
 *
 * @author yuche
 * @since 2020/3/13 12:33
 */
public class HelloWorldImpl2 implements HelloWorld {
    @Override
    public void pringHelloWorld() {
        System.out.println("enter helloWorldImpl2 pringHelloWorld");

    }

    @Override
    public void doprint() {
        System.out.println("enter helloWorldImpl2 doPrint");
    }
}
