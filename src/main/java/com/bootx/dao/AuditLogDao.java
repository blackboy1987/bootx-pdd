
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