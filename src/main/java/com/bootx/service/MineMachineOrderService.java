/*
 * Copyright 2008-2018 shopxx.net. All rights reserved.
 * Support: localhost
 * License: localhost/license
 * FileId: 22YIxwI+eUhJbwDWcIavVBs0O7OkiDj9
 */
package com.bootx.service;

import com.bootx.entity.Member;
import com.bootx.entity.MineMachine;
import com.bootx.entity.MineMachineOrder;

/**
 * Service - 广告
 * 
 * @author 好源++ Team
 * @version 6.1
 */
public interface MineMachineOrderService extends BaseService<MineMachineOrder, Long> {

    MineMachineOrder create(Member member, MineMachine mineMachine, Integer quantity, Integer day);
}