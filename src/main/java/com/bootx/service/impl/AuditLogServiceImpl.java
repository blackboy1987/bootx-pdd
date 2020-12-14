/*
 * Copyright 2008-2018 shopxx.net. All rights reserved.
 * Support: localhost
 * License: localhost/license
 * FileId: PoFB0RaymZebKS8iE3+H5Xc8cndMfk91
 */
package com.bootx.service.impl;

import com.bootx.dao.AuditLogDao;
import com.bootx.entity.AuditLog;
import com.bootx.service.AuditLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
/**
 * Service - 审计日志
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Service
public class AuditLogServiceImpl extends BaseServiceImpl<AuditLog, Long> implements AuditLogService {

	@Autowired
	private AuditLogDao auditLogDao;

	@Override
	@Async
	public void create(AuditLog auditLog) {
		auditLogDao.persist(auditLog);
	}

	@Override
	public void clear() {
		auditLogDao.removeAll();
	}

}