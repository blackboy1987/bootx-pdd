
package com.bootx.dao;

import com.bootx.entity.Member;
import com.bootx.entity.Store;

import java.util.List;

/**
 * Dao - 审计日志
 * 
 * @author 好源++ Team
 * @version 6.1
 */
public interface StoreDao extends BaseDao<Store, Long> {

    List<Store> findList(Member member);

}