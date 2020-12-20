
package com.bootx.service.impl;
import com.bootx.dao.BitCoinAccountMoneyDao;
import com.bootx.entity.BitCoinAccount;
import com.bootx.entity.BitCoinAccountMoney;
import com.bootx.entity.Member;
import com.bootx.service.BitCoinAccountMoneyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Service - 广告
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Service
public class BitCoinAccountMoneyServiceImpl extends BaseServiceImpl<BitCoinAccountMoney, Long> implements BitCoinAccountMoneyService {

    @Autowired
    private BitCoinAccountMoneyDao bitCoinAccountMoneyDao;

    @Override
    public void init(Member member, BitCoinAccount bitCoinAccount) {
        BitCoinAccountMoney bitCoinAccountMoney = new BitCoinAccountMoney();
        bitCoinAccountMoney.setUserId(member.getId());
        bitCoinAccountMoney.setBitCoinAccountId(bitCoinAccount.getId());
        bitCoinAccountMoney.setAssetType(bitCoinAccount.getAssetType());
        bitCoinAccountMoney.setMoney(new BigDecimal("0"));
        bitCoinAccountMoney.setFrozenMoney(new BigDecimal("0"));
        bitCoinAccountMoney.setState(0);
        bitCoinAccountMoney.setName(bitCoinAccount.getName());
        bitCoinAccountMoney.setPrice(new BigDecimal("0"));
        super.save(bitCoinAccountMoney);
    }

    @Override
    public BitCoinAccountMoney findByBitCoinAccountIdAndUserId(Long bitCoinAccountId, Long userId) {
        return bitCoinAccountMoneyDao.findByBitCoinAccountIdAndUserId( bitCoinAccountId, userId);
    }
}