
package com.bootx.service.impl;

import com.bootx.dao.CompanyBankDao;
import com.bootx.entity.CompanyBank;
import com.bootx.service.CompanyBankService;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service - 广告
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Service
public class CompanyBankServiceImpl extends BaseServiceImpl<CompanyBank, Long> implements CompanyBankService {

    @Autowired
    private CompanyBankDao companyBankDao;

    @Override
    public CompanyBank get() {
        return companyBankDao.get();
    }

    @Override
    public CompanyBank update(CompanyBank companyBank) {
        CompanyBank pCompanyBank = super.update(companyBank);
        if (BooleanUtils.isTrue(pCompanyBank.getIsEnabled())) {
            companyBankDao.clearDefault(pCompanyBank);
        }
        return pCompanyBank;
    }
}