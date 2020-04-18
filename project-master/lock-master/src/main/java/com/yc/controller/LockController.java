package com.yc.controller;

import com.alibaba.fastjson.JSON;
import com.yc.entity.Lock;
import com.yc.entity.Order;
import com.yc.mapper.LockMapper;
import com.yc.redis.RedisLockUtil;
import com.yc.zooKeeper.ZkLockUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private LockMapper lockMapper;

    @RequestMapping("/testRedisLock")
    public String testRedisLock(@RequestParam(value = "uid", required = true) String uid) {
        String value = String.valueOf(Thread.currentThread().getId());
        boolean isLockSuccess = redisLockUtil.getLock(uid, value, 1000, 2);
        if (isLockSuccess) {
            Lock lock = lockMapper.getLockById(Integer.valueOf(uid));
            Integer times = lock.getTimes();
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Integer updateTimes = times + 1;
            lock.setTimes(updateTimes);
            lockMapper.updateLock1(lock);
        }
        redisLockUtil.unLock(uid, value);
        return "success";
    }

    @GetMapping("/testZkLock")
    public String testLock2(@RequestParam(value = "uid", required = true) String uid) {
        zkLock.lock();
        Lock lock = lockMapper.getLockById(Integer.valueOf(uid));
        Integer times = lock.getTimes();
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Integer updateTimes = times + 1;
        lock.setTimes(updateTimes);
        lockMapper.updateLock1(lock);
        zkLock.unLock();
        return "success";
    }


    @GetMapping("/testDbLock")
    @Transactional
    public String testDbLock(@RequestParam(value = "uid", required = true) String uid) {
        System.out.println(Thread.currentThread().getId() + "进入方法");
        Lock lock = lockMapper.getLockByIdForUpdate(Integer.valueOf(uid));
        System.out.println(Thread.currentThread().getId() + "查询结果");

        if (lock != null) {
            Integer times = lock.getTimes();
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Integer updateTimes = times + 1;
            lock.setTimes(updateTimes);
            lockMapper.updateLock1(lock);
            System.out.println(Thread.currentThread().getId() + "更新结果");
        }
        return "success";
    }

    @PostMapping("/testAddLock")
    public String testAddLock(@RequestBody Lock lock) {
        lockMapper.addLock(lock);
        return "success";
    }

    @GetMapping("/testGetLock")
    public String testGetLock(@RequestParam(value = "uid", required = true) String uid) {
        Lock lock = lockMapper.getLockById(Integer.valueOf(uid));
        return JSON.toJSONString(lock);
    }
}
