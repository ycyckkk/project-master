package com.yc;

import com.yc.redis.RedisLockUtil;
import com.yc.zooKeeper.ZkLockUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 应用模块名称<p>
 * 代码描述<p>
 * Copyright: Copyright (C) 2020 XXX, Inc. All rights reserved. <p>
 *
 * @author yuche
 * @since 2020/4/3 22:02
 */
@SpringBootApplication
@EnableTransactionManagement(proxyTargetClass = true)   //开启事物管理功能
//@MapperScan(value = "com.yc.mapper")
//(exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
// implements CommandLineRunner
public class Application{
    @Autowired
    private ZkLockUtil zkLock;

    @Autowired
    private RedisLockUtil redisLock;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

//    @Override
//    public void run(String... strings) throws Exception {
//        ExecutorService executorService = Executors.newFixedThreadPool(2);
//        Thread thread = new Thread(){
//            @Override
//            public void run() {
//                super.run();
//            }
//        };
//        List<Thread> threadList = new ArrayList<Thread>(100);
//        for (int i = 0; i < 10; i++) {
//            Thread lockTestThread = new Thread(() -> {
//                zkLock.lock();
//                redisLock.getLock("i", String.valueOf(Thread.currentThread().getId()), 1000, 2);

//                zkLock.unLock();
//
//            });
//            try {
//                lockTestThread.start();
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            threadList.add(lockTestThread);
//            executorService.submit(lockTestThread);
//}
//        executorService.shutdown();
//    }
}
