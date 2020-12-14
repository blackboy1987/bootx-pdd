/*
 * Copyright 2008-2018 shopxx.net. All rights reserved.
 * Support: localhost
 * License: localhost/license
 * FileId: aNovGLyperpmLTk+9SN66imN/92gDq+H
 */
package com.bootx.dao.impl;

import com.bootx.dao.MessageConfigDao;
import com.bootx.entity.MessageConfig;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;

/**
 * Dao - 消息配置
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Repository
public class MessageConfigDaoImpl extends BaseDaoImpl<MessageConfig, Long> implements MessageConfigDao {

	@Override
	public MessageConfig find(MessageConfig.Type type) {
		if (type == null) {
			return null;
		}
		try {
			String jpql = "select messageConfig from MessageConfig messageConfig where messageConfig.type = :type";
			return entityManager.createQuery(jpql, MessageConfig.class).setParameter("type", type).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}