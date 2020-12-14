/*
 * Copyright 2008-2018 shopxx.net. All rights reserved.
 * Support: localhost
 * License: localhost/license
 * FileId: 22YIxwI+eUhJbwDWcIavVBs0O7OkiDj9
 */
package com.bootx.service;

import com.bootx.entity.BitCoinType;

/**
 * Service - 广告
 * 
 * @author 好源++ Team
 * @version 6.1
 */
public interface BitCoinTypeService extends BaseService<BitCoinType, Long> {

    BitCoinType findByName(String name);

}