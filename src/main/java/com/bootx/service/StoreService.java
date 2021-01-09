
package com.bootx.service;

import com.bootx.entity.Store;
import com.pdd.pop.sdk.http.api.pop.response.PddMallInfoGetResponse;
import com.pdd.pop.sdk.http.token.AccessTokenResponse;

/**
 * Service - 审计日志
 * 
 * @author 好源++ Team
 * @version 6.1
 */
public interface StoreService extends BaseService<Store, Long> {

	Store findByMallId(Long mallId);
	Store findByMallName(String mallName);

	void updateAccessToken(AccessTokenResponse response);

	Store update(PddMallInfoGetResponse response);

	Store create(String ownerId,String ownerName);
}