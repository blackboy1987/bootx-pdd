
package com.bootx.service;

import com.bootx.entity.BitCoinAccount;
import com.bootx.entity.BitCoinAccountBank;
import com.bootx.entity.Member;

/**
 * Service - 广告
 * 
 * @author 好源++ Team
 * @version 6.1
 */
public interface BitCoinAccountBankService extends BaseService<BitCoinAccountBank, Long> {

    void init(Member member, BitCoinAccount bitCoinAccount);

    BitCoinAccountBank findByBitCoinAccountIdAndUserId(Long bitCoinAccountId, Long userId);
}