/*
 * Copyright 2008-2018 shopxx.net. All rights reserved.
 * Support: localhost
 * License: localhost/license
 * FileId: 31lg+ScsivhzJHyJBd23PSwyd/NCn6W4
 */
package com.bootx.audit;

/**
 * Audit - 审计者Provider
 * 
 * @author 好源++ Team
 * @version 6.1
 */
public interface AuditorProvider<T> {

	/**
	 * 获取当前审计者
	 * 
	 * @return 当前审计者
	 */
	T getCurrentAuditor();

}