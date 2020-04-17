package com.yc.zooKeeper;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * 应用模块名称<p>
 * 代码描述<p>
 * Copyright: Copyright (C) 2020 XXX, Inc. All rights reserved. <p>
 *
 * @author yuche
 * @since 2020/4/5 10:26
 */
@Component
public class ZkLockUtil extends AbstractLock {

    private CountDownLatch countDownLatch = null;

    protected static final String PATH2 = "/PATH2";

    protected static final String ZkIpAddr = "192.168.211.128:2181";

    protected ZkClient zkClient = new ZkClient(ZkIpAddr);

    private ThreadLocal<String> nodeId = new ThreadLocal<>();

    /***
     * 节点名称
     */
    private static final String NODE_NAME = "/test_simple_lock3";


    public ZkLockUtil() {
        this.zkClient = zkClient;
    }

    @Override
    public boolean tryLock1() {
        System.out.println("lock threadId =" + Thread.currentThread().getId());

        String currentPath = null;
        String beforePath = null;

        if (!zkClient.exists(PATH2)) {
            zkClient.createPersistent(PATH2);
        }

        //如果currentPath为0.则尝试进行上锁
        if (currentPath == null || currentPath.length() < 1) {
            currentPath = zkClient.createEphemeralSequential(PATH2 + "/", "lock");
        }
        //获取最近一个节点判断是否获取节点
        List<String> childrenList = zkClient.getChildren(PATH2);
        if (null == childrenList || childrenList.size() == 0) {
            return true;
        } else {
            Collections.sort(childrenList);
            //如果等于当前节点表示获取到锁
            if (currentPath.equals(PATH2 + "/" + childrenList.get(0))) {
                nodeId.set(currentPath);
                return true;
            } else {
                int wz = Collections.binarySearch(childrenList, currentPath.substring(7));
                beforePath = PATH2 + "/" + childrenList.get(wz - 1);

                IZkDataListener listener = new IZkDataListener() {
                    @Override
                    public void handleDataChange(String s, Object o) throws Exception {
                    }

                    @Override
                    public void handleDataDeleted(String s) throws Exception {
                        if (countDownLatch != null) {
                            countDownLatch.countDown();
                        }
                    }
                };
                zkClient.subscribeDataChanges(beforePath, listener);
                if (zkClient.exists(beforePath)) {
                    countDownLatch = new CountDownLatch(1);
                    try {
                        countDownLatch.await();
                        nodeId.set(currentPath);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                zkClient.unsubscribeDataChanges(beforePath, listener);
                return true;
            }
        }
    }

    @Override
    public boolean tryLock2() {
        try {
            zkClient.createEphemeral(NODE_NAME);
            System.out.println("lock threadId =" + Thread.currentThread().getId());
//            nodeId.set(String.valueOf(System.currentTimeMillis()));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void unLock() {
        System.out.println("unLock threadId =" + Thread.currentThread().getId());
        if (null != zkClient && null != nodeId && zkClient.exists(PATH2)) {
            zkClient.delete(NODE_NAME);
        }
        nodeId.remove();
    }

    @Override
    void waitLock() {
        IZkDataListener listener = new IZkDataListener() {
            @Override
            public void handleDataChange(String s, Object o) throws Exception {
            }

            @Override
            public void handleDataDeleted(String s) throws Exception {
                if (countDownLatch != null) {
                    countDownLatch.countDown();
                }
            }
        };
        zkClient.subscribeDataChanges(NODE_NAME, listener);
        if (zkClient.exists(NODE_NAME)) {
            countDownLatch = new CountDownLatch(1);
            try {
                countDownLatch.await();
                System.out.println("等待获取锁资源");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        zkClient.unsubscribeDataChanges(NODE_NAME, listener);
    }

}
