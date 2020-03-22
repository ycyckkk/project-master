package com.yc.aop2;

/**
 * 应用模块名称<p>
 * 代码描述<p>
 * Copyright: Copyright (C) 2020 XXX, Inc. All rights reserved. <p>
 *
 * @author yuche
 * @since 2020/3/21 12:43
 */
// 切点
public class CookA implements Cook {
    @Override
    public void make() {
        System.out.println("制作食品");
    }

    @Override
    public void make(String name) {
        System.out.println(name + "制作食品");

    }
}
