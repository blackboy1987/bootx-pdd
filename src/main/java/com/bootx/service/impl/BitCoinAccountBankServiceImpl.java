
package com.bootx.service.impl;
import com.bootx.dao.BitCoinAccountBankDao;
import com.bootx.entity.BitCoinAccount;
import com.bootx.entity.BitCoinAccountBank;
import com.bootx.entity.Member;
import com.bootx.service.BitCoinAccountBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service - 广告
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Service
public class BitCoinAccountBankServiceImpl extends BaseServiceImpl<BitCoinAccountBank, Long> implements BitCoinAccountBankService {

    @Autowired
    private BitCoinAccountBankDao bitCoinAccountBankDao;

    @Override
    public void init(Member member, BitCoinAccount bitCoinAccount) {
        BitCoinAccountBank bitCoinAccountBank = new BitCoinAccountBank();
        bitCoinAccountBank.setUserId(member.getId());
        bitCoinAccountBank.setBitCoinAccountId(bitCoinAccount.getId());
        bitCoinAccountBank.setBankType(null);
        bitCoinAccountBank.setName(null);
        bitCoinAccountBank.setPhone(null);
        bitCoinAccountBank.setBankCode(null);
        bitCoinAccountBank.setBankAddr(null);
        bitCoinAccountBank.setIsDefault(true);
        bitCoinAccountBank.setIsDel(false);
        super.save(bitCoinAccountBank);
    }

    @Override
    public BitCoinAccountBank findByBitCoinAccountIdAndUserId(Long bitCoinAccountId, Long userId) {
        return bitCoinAccountBankDao.findByBitCoinAccountIdAndUserId( bitCoinAccountId, userId);
    }
}