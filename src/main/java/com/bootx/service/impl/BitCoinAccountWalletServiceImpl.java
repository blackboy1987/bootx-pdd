
package com.bootx.service.impl;
import com.bootx.dao.BitCoinAccountWalletDao;
import com.bootx.entity.BitCoinAccount;
import com.bootx.entity.BitCoinAccountWallet;
import com.bootx.entity.Member;
import com.bootx.service.BitCoinAccountWalletService;
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
public class BitCoinAccountWalletServiceImpl extends BaseServiceImpl<BitCoinAccountWallet, Long> implements BitCoinAccountWalletService {
    @Autowired
    private BitCoinAccountWalletDao bitCoinAccountWalletDao;

    @Override
    public void init(Member member, BitCoinAccount bitCoinAccount) {
        BitCoinAccountWallet bitCoinAccountWallet = new BitCoinAccountWallet();
        bitCoinAccountWallet.setUserId(member.getId());
        bitCoinAccountWallet.setBitCoinAccountId(bitCoinAccount.getId());
        bitCoinAccountWallet.setAssetType(bitCoinAccount.getAssetType());
        bitCoinAccountWallet.setWalletAdd("");
        bitCoinAccountWallet.setMoney(new BigDecimal("0"));
        bitCoinAccountWallet.setFrozenMoney(new BigDecimal("0"));
        bitCoinAccountWallet.setState(0);
        bitCoinAccountWallet.setName(bitCoinAccount.getName());
        bitCoinAccountWallet.setMinLength(0);
        super.save(bitCoinAccountWallet);

    }

    @Override
    public BitCoinAccountWallet findByBitCoinAccountIdAndUserId(Long bitCoinAccountId, Long userId) {
        return bitCoinAccountWalletDao.findByBitCoinAccountIdAndUserId( bitCoinAccountId, userId);
    }
}