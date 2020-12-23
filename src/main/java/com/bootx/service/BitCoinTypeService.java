
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

    BitCoinType findByAssetType(Integer assetType);
}