/*
 * Copyright 2008-2018 shopxx.net. All rights reserved.
 * Support: localhost
 * License: localhost/license
 * FileId: Nk49Z43lIMkAxGvEp2iSCEW22Wz60oBd
 */
package com.bootx.dao;

import com.bootx.entity.MessageConfig;

/**
 * Dao - 消息配置
 * 
 * @author 好源++ Team
 * @version 6.1
 */
public interface MessageConfigDao extends BaseDao<MessageConfig, Long> {

	/**
	 * 查找消息配置
	 * 
	 * @param type
	 *            类型
	 * @return 消息配置
	 */
	MessageConfig find(MessageConfig.Type type);

}