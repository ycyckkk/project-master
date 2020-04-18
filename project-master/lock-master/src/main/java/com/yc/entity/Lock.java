package com.yc.entity;

import lombok.Builder;
import lombok.Data;

/**
 * 应用模块名称<p>
 * 代码描述<p>
 *
 * @author yuche
 * @since 2020/4/18 11:25
 */
@Data
@Builder
public class Lock {
    private Integer uid;
    private String lockName;
    private Integer times;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getLockName() {
        return lockName;
    }

    public void setLockName(String lockName) {
        this.lockName = lockName;
    }

    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }
}
