/*
 * Copyright 2008-2018 shopxx.net. All rights reserved.
 * Support: localhost
 * License: localhost/license
 * FileId: 22YIxwI+eUhJbwDWcIavVBs0O7OkiDj9
 */
package com.bootx.service;

import com.bootx.entity.Invest;
import com.bootx.entity.Member;

import java.util.List;

/**
 * Service - 广告
 * 
 * @author 好源++ Team
 * @version 6.1
 */
public interface InvestService extends BaseService<Invest, Long> {

    List<Invest> findListByCoinType(Member member, Integer coinType);

    void create(Member member);
}