package com.yc.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * 应用模块名称<p>
 * 代码描述<p>
 * Copyright: Copyright (C) 2020 XXX, Inc. All rights reserved. <p>
 *
 * @author yuche
 * @since 2020/4/4 10:51
 */
@Component
public class RedisLockUtil {

    @Autowired
    private RedisTemplate redisTemplate;

    private final Long SUCCESS = 1L;

    private final Long FAILED = -1L;

    //定义获取锁的lua脚本
    private final static DefaultRedisScript<Long> LOCK_LUA_SCRIPT = new DefaultRedisScript<>(
            "if redis.call('setnx', KEYS[1], ARGV[1]) == 1 then return redis.call('pexpire', KEYS[1], ARGV[2]) else return 0 end"
            , Long.class
    );


    //定义释放锁的lua脚本
    private final static DefaultRedisScript<Long> UNLOCK_LUA_SCRIPT = new DefaultRedisScript<>(
            "if redis.call('get',KEYS[1]) == ARGV[1] then return redis.call('del',KEYS[1]) else return -1 end"
            , Long.class
    );

    /**
     * 获取redis锁
     *
     * @param key        唯一ID
     * @param value      线程ID
     * @param timeOut    超时时间
     * @param retryTimes 重试次数
     * @return
     */
    public boolean getLock(String key, String value, long timeOut, int retryTimes) {
        try {
            System.out.println(Thread.currentThread().getId() + "开始进行加锁");
            List<String> keys = Arrays.asList(key);
            Object result = redisTemplate.execute(LOCK_LUA_SCRIPT, keys, value, timeOut);
            if (SUCCESS.equals(result)) {
                System.out.println(Thread.currentThread().getId() + "加锁成功");
                return true;
            } else if (retryTimes == 0) {
                System.out.println(Thread.currentThread().getId() + "加锁失败");
                return false;
            } else {
                int count = 0;
                while (true) {
                    try {
                        Thread.sleep(1000);
                        result = redisTemplate.execute(LOCK_LUA_SCRIPT, keys, value, timeOut);
                        if (FAILED.equals(result)) {
                            System.out.println(Thread.currentThread().getId() + "重试加锁失败");
                            count++;
                            if (count == retryTimes) {
                                System.out.println(Thread.currentThread().getId() + "重试次数耗尽");
                                return false;
                            } else {
                                continue;
                            }

                        } else if (SUCCESS.equals(result)) {
                            System.out.println(Thread.currentThread().getId() + "重试加锁成功");
                            return true;
                        }
                    } catch (Exception e2) {
                        System.out.println(Thread.currentThread().getId() + "重试加锁异常");
                    }
                }
            }
        } catch (Exception e1) {
            System.out.println(Thread.currentThread().getId() + "加锁异常");
        }
        return false;
    }

    /**
     * 释放锁
     *
     * @param key   唯一ID
     * @param value 线程ID
     * @return
     */
    public boolean unLock(String key, String value) {
        try {
            List<String> keys = Arrays.asList(key);
            Object result = redisTemplate.execute(UNLOCK_LUA_SCRIPT, keys, value);
            if (SUCCESS.equals(result)) {
                System.out.println(Thread.currentThread().getId() + "解锁成功");
                return true;
            } else if (FAILED.equals(result)) {
                System.out.println(Thread.currentThread().getId() + "解锁失败");
            } else {
                System.out.println(Thread.currentThread().getId() + "删除key失败");
            }
        } catch (Exception e) {
            System.out.println(Thread.currentThread().getId() + "解锁异常");
        }
        return false;
    }
}
