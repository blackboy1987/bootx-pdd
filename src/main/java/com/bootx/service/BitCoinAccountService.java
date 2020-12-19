
package com.bootx.service;

import com.bootx.entity.BitCoinAccount;
import com.bootx.entity.Member;

import java.util.List;

/**
 * Service - 广告
 * 
 * @author 好源++ Team
 * @version 6.1
 */
public interface BitCoinAccountService extends BaseService<BitCoinAccount, Long> {

    List<BitCoinAccount> findByUserId(Long userId);

    BitCoinAccount findByUserIdAndName(Long userId,String name);

    void initAccount(Member member);

    BitCoinAccount findByUserIdAndAssetType(Long userId, Integer assetType);
}