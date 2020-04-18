package com.yc.mapper;

import com.yc.entity.Lock;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 应用模块名称<p>
 * 代码描述<p>
 *
 * @author yuche
 * @since 2020/4/18 11:30
 */
@Mapper
public interface LockMapper {

    /**
     * 查询
     *
     * @param id 主键
     * @return
     */
    public Lock getLockById(Integer id);

    /***
     * 新增
     * @param lock
     * @return
     */
    public void addLock(Lock lock);

    /***
     * 更新
     * @param lock
     * @return
     */
    public void updateLock1(Lock lock);

    /***
     * 查询
     * @param lock
     * @return
     */
    public Lock getLockByIdForUpdate(Integer id);
}
