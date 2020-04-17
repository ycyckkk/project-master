package com.yc.controller;

import com.yc.entity.Order;
import com.yc.redis.RedisLockUtil;
import com.yc.zooKeeper.ZkLockUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 应用模块名称<p>
 * 代码描述<p>
 * Copyright: Copyright (C) 2020 XXX, Inc. All rights reserved. <p>
 *
 * @author yuche
 * @since 2020/4/4 10:43
 */
@RestController
public class LockController {
    @Autowired
    private RedisLockUtil redisLockUtil;

    @Autowired
    private ZkLockUtil zkLock;

    @RequestMapping("/testGetRedisLock")
    public String testLock1(@RequestBody Order order) {
        String value = String.valueOf(System.currentTimeMillis());
        boolean isLockSuccess = redisLockUtil.getLock(order.getOrderId(), value, 10000, 2);
        return String.valueOf(isLockSuccess);
    }

    @GetMapping("/testGetZkLock")
    public String testLock2() {
        zkLock.lock();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        zkLock.unLock();
        return "success";
    }
}
