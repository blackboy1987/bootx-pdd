
package com.bootx.service.impl;

import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.dao.AccountLogDao;
import com.bootx.entity.AccountLog;
import com.bootx.service.AccountLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service - 广告
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Service
public class AccountLogServiceImpl extends BaseServiceImpl<AccountLog, Long> implements AccountLogService {

    @Autowired
    private AccountLogDao accountLogDao;

    @Override
    public Page<AccountLog> findPage(Pageable pageable, Long userId, Integer dataType) {
        return accountLogDao.findPage(pageable, userId, dataType);
    }
}