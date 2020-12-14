/*
 * Copyright 2008-2018 shopxx.net. All rights reserved.
 * Support: localhost
 * License: localhost/license
 * FileId: poWGbOrrJpWm7FJ3jRPsi1GqLraWxfx4
 */
package com.bootx.service.impl;


import com.bootx.dao.BitCoinAccountDao;
import com.bootx.entity.BitCoinAccount;
import com.bootx.entity.BitCoinType;
import com.bootx.entity.Member;
import com.bootx.service.BitCoinAccountService;
import com.bootx.service.BitCoinTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Service - 广告
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Service
public class BitCoinAccountServiceImpl extends BaseServiceImpl<BitCoinAccount, Long> implements BitCoinAccountService {

	@Autowired
	private BitCoinAccountDao bitCoinAccountDao;

	@Autowired
	private BitCoinTypeService bitCoinTypeService;

	@Override
	public List<BitCoinAccount> findByUserId(Long userId) {
		return bitCoinAccountDao.findByUserId(userId);
	}

	@Override
	public BitCoinAccount findByUserIdAndName(Long userId, String name) {
		return bitCoinAccountDao.findByUserIdAndName(userId,name);
	}

	@Override
	public void initAccount(Member member) {
		List<BitCoinType> bitCoinTypes = bitCoinTypeService.findAll();
		for (BitCoinType bitCoinType:bitCoinTypes) {
			BitCoinAccount bitCoinAccount = findByUserIdAndName(member.getId(),bitCoinType.getName());
			if(bitCoinAccount==null||bitCoinAccount.getId()==null){
				bitCoinAccount = new BitCoinAccount();
				bitCoinAccount.setAssetType(bitCoinType.getAssetType());
				bitCoinAccount.setFrozenMoney(BigDecimal.ZERO);
				bitCoinAccount.setMoney(BigDecimal.ZERO);
				bitCoinAccount.setName(bitCoinType.getName());
				bitCoinAccount.setPrice(bitCoinType.getPrice());
				bitCoinAccount.setState(true);
				bitCoinAccount.setUserId(member.getId());
				super.save(bitCoinAccount);
			}
		}
	}
}