
package com.bootx.service;

import com.bootx.entity.Member;
import com.bootx.entity.Store;
import com.pdd.pop.sdk.http.api.pop.response.PddMallInfoGetResponse;
import com.pdd.pop.sdk.http.token.AccessTokenResponse;

import java.util.List;

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

	Store create(AccessTokenResponse accessTokenResponse);

	List<Store> findList(Member member);

	void flushAccessToken(Store store);
}