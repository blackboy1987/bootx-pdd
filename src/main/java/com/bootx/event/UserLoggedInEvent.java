/*
 * Copyright 2008-2018 shopxx.net. All rights reserved.
 * Support: localhost
 * License: localhost/license
 * FileId: lujbFqDj3N06i7uLLX7YBNcajvi0gtY2
 */
package com.bootx.event;
import com.bootx.entity.User;

/**
 * Event - 用户登录
 * 
 * @author 好源++ Team
 * @version 6.1
 */
public class UserLoggedInEvent extends UserEvent {

	private static final long serialVersionUID = 3087635924598684802L;

	/**
	 * 构造方法
	 * 
	 * @param source
	 *            事件源
	 * @param user
	 *            用户
	 */
	public UserLoggedInEvent(Object source, User user) {
		super(source, user);
	}

}