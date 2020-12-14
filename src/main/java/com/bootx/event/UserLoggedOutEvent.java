/*
 * Copyright 2008-2018 shopxx.net. All rights reserved.
 * Support: localhost
 * License: localhost/license
 * FileId: h3vpeqyE91B6tEOmcOE9UYAfxNOzqWDp
 */
package com.bootx.event;
import com.bootx.entity.User;
/**
 * Event - 用户注销
 * 
 * @author 好源++ Team
 * @version 6.1
 */
public class UserLoggedOutEvent extends UserEvent {

	private static final long serialVersionUID = 8560275705072178478L;

	/**
	 * 构造方法
	 * 
	 * @param source
	 *            事件源
	 * @param user
	 *            用户
	 */
	public UserLoggedOutEvent(Object source, User user) {
		super(source, user);
	}

}