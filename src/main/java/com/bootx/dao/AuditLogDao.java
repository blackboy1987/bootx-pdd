/*
 * Copyright 2008-2018 shopxx.net. All rights reserved.
 * Support: localhost
 * License: localhost/license
 * FileId: zhjV2F+cYdEVfTT9iynQTX1lM6n+8Lnx
 */
package com.bootx.dao;

import com.bootx.entity.AuditLog;

/**
 * Dao - 审计日志
 * 
 * @author 好源++ Team
 * @version 6.1
 */
public interface AuditLogDao extends BaseDao<AuditLog, Long> {

	/**
	 * 删除所有
	 */
	void removeAll();

}