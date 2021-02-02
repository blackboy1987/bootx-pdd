
package com.bootx.service;

import com.bootx.common.Message;
import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.entity.Member;
import com.bootx.entity.Store;
import com.bootx.entity.StoreDeliveryTemplate;
import com.bootx.entity.StoreUploadConfig;
import com.pdd.pop.sdk.http.api.pop.response.PddMallInfoGetResponse;
import com.pdd.pop.sdk.http.token.AccessTokenResponse;

import java.util.List;
import java.util.Map;

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

	Store create(AccessTokenResponse accessTokenResponse,Member member);

	List<Store> findList(Member member);

	void flushAccessToken(Store store);

    Page<Store> findPage(Pageable pageable, Member member);

	Message unbind(Store store);

    List<Map<String, Object>> tree(Member member);

	PddMallInfoGetResponse info(Long storeId) throws Exception;

    Long count1(Member member);

	Store create1(AccessTokenResponse accessTokenResponse, Member current);

	List<StoreDeliveryTemplate> getStoreDeliveryTemplate(Store store) throws Exception;


	List<StoreDeliveryTemplate> updateDeliveryTemplate(Long id) throws Exception;

	StoreUploadConfig build(StoreUploadConfig storeUploadConfig);
}