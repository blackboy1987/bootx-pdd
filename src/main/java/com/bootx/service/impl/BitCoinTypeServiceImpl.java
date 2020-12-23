
package com.bootx.service.impl;

import com.bootx.dao.BitCoinTypeDao;
import com.bootx.entity.BitCoinType;
import com.bootx.service.BitCoinTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service - 广告
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Service
public class BitCoinTypeServiceImpl extends BaseServiceImpl<BitCoinType, Long> implements BitCoinTypeService {

	@Autowired
	private BitCoinTypeDao bitCoinTypeDao;

	@Override
	public BitCoinType findByName(String name) {
		return bitCoinTypeDao.find("name",name);
	}

	@Override
	public BitCoinType findByAssetType(Integer assetType) {
		return bitCoinTypeDao.find("assetType",assetType);
	}
}