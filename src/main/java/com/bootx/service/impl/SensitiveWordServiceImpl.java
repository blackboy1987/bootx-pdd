
package com.bootx.service.impl;

import com.bootx.dao.AuditLogDao;
import com.bootx.dao.SensitiveWordDao;
import com.bootx.entity.AuditLog;
import com.bootx.entity.SensitiveWord;
import com.bootx.service.AuditLogService;
import com.bootx.service.SensitiveWordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Service - 审计日志
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Service
public class SensitiveWordServiceImpl extends BaseServiceImpl<SensitiveWord, Long> implements SensitiveWordService {

    @Resource
    private SensitiveWordDao sensitiveWordDao;

    @Override
    public boolean wordExist(String word) {
        return sensitiveWordDao.exists("word", word);
    }
}