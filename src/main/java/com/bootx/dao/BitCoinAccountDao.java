
package com.bootx.dao;

import com.bootx.entity.BitCoinAccount;

import java.util.List;

/**
 * Dao - 广告
 * 
 * @author 好源++ Team
 * @version 6.1
 */
public interface BitCoinAccountDao extends BaseDao<BitCoinAccount, Long> {

    List<BitCoinAccount> findByUserId(Long userId);

    BitCoinAccount findByUserIdAndName(Long userId, String name);
}