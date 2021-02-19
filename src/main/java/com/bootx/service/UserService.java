
package com.bootx.service;

import com.bootx.audit.AuditorProvider;
import com.bootx.entity.User;

import javax.persistence.LockModeType;

/**
 * Service - 用户
 * 
 * @author 好源++ Team
 * @version 6.1
 */
public interface UserService extends BaseService<User, Long>, AuditorProvider<User> {


    /**
     * 获取当前登录用户
     *
     * @return 当前登录用户，若不存在则返回null
     */
    User getCurrent();

    /**
     * 获取当前登录用户
     *
     * @param userClass
     *            用户类型
     * @return 当前登录用户，若不存在则返回null
     */
    <T extends User> T getCurrent(Class<T> userClass);

    /**
     * 获取当前登录用户
     *
     * @param userClass
     *            用户类型
     * @param lockModeType
     *            锁定方式
     * @return 当前登录用户，若不存在则返回null
     */
    <T extends User> T getCurrent(Class<T> userClass, LockModeType lockModeType);
}