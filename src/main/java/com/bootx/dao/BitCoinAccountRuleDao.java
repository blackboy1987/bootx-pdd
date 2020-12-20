
package com.bootx.dao;

import com.bootx.entity.BitCoinAccountRule;

/**
 * Dao - 广告
 * 
 * @author 好源++ Team
 * @version 6.1
 */
public interface BitCoinAccountRuleDao extends BaseDao<BitCoinAccountRule, Long> {

    BitCoinAccountRule findByBitCoinAccountIdAndUserId(Long bitCoinAccountId, Long userId);
}