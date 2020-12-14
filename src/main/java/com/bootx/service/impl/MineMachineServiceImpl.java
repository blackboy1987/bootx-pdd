/*
 * Copyright 2008-2018 shopxx.net. All rights reserved.
 * Support: localhost
 * License: localhost/license
 * FileId: poWGbOrrJpWm7FJ3jRPsi1GqLraWxfx4
 */
package com.bootx.service.impl;

import com.bootx.dao.MineMachineDao;
import com.bootx.entity.MineMachine;
import com.bootx.service.MineMachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service - 广告
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Service
public class MineMachineServiceImpl extends BaseServiceImpl<MineMachine, Long> implements MineMachineService {

	@Autowired
	private MineMachineDao mineMachineDao;

	@Override
	public List<MineMachine> findListByCoinType(Integer coinType,Integer count) {
		return mineMachineDao.findListByCoinType(coinType,count);
	}

	@Override
	public List<MineMachine> findListByCoinTypeAndType(Integer coinType, Integer type) {
		return mineMachineDao.findListByCoinTypeAndType(coinType,type);
	}

	@Override
	public MineMachine findDefault() {
		return mineMachineDao.findDefault();
	}

	@Override
	public MineMachine findByProductId(Long productId) {
		return mineMachineDao.find("productId",productId);
	}
}