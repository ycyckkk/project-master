package com.yc.zooKeeper;


public abstract class AbstractLock implements ZkLock {

    @Override
    public void lock() {
        if (tryLock2()) {
            return;
        }
        waitLock();
        lock();
    }

    @Override
    public void unLock() {
    }

    abstract boolean tryLock1();

    abstract boolean tryLock2();

    abstract void waitLock();

}
