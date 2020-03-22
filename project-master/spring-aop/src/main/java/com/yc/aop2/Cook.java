package com.yc.aop2;

/**
 * 应用模块名称<p>
 * 代码描述<p>
 * Copyright: Copyright (C) 2020 XXX, Inc. All rights reserved. <p>
 *
 * @author yuche
 * @since 2020/3/21 12:42
 */
public interface Cook {

    /**
     * 制作食品
     */
    void make();

    /**
     * 制作
     * @param name 食品名称
     */
    void make(String name);

}
