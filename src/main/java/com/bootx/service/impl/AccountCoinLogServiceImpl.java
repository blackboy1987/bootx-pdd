
package com.bootx.service.impl;

import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.dao.AccountCoinLogDao;
import com.bootx.entity.AccountCoinLog;
import com.bootx.service.AccountCoinLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service - 广告
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Service
public class AccountCoinLogServiceImpl extends BaseServiceImpl<AccountCoinLog, Long> implements AccountCoinLogService {

    @Autowired
    private AccountCoinLogDao accountCoinLogDao;

    @Override
    public Page<AccountCoinLog> findPage(Pageable pageable, Long userId, Integer excision, Integer assetType, String account,Integer state) {
        return accountCoinLogDao.findPage(pageable, userId, excision, assetType, account,state);
    }
}