package com.yc.zooKeeper;


public abstract class AbstractLock implements ZkLock {

    @Override
    public void lock() {
        if (tryLock()) {
            return;
        }
        waitLock();
        lock();
    }

    @Override
    public void unLock() {
    }

    abstract boolean tryLock();

    abstract void waitLock();

}
