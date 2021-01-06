
package com.bootx.service.impl;

import com.bootx.dao.StoreDao;
import com.bootx.entity.Store;
import com.bootx.pdd.entity.AccessToken;
import com.bootx.pdd.service.PddService;
import com.bootx.service.StoreService;
import com.pdd.pop.sdk.http.api.pop.response.PddMallInfoGetResponse;
import com.pdd.pop.sdk.http.token.AccessTokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Service - 审计日志
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Service
public class StoreServiceImpl extends BaseServiceImpl<Store, Long> implements StoreService {

	@Autowired
	private StoreDao storeDao;
	@Resource
	private PddService pddService;

	@Override
	public Store findByMallId(Long mallId) {
		return storeDao.find("mallId",mallId);
	}

	@Override
	public Store findByMallName(String mallName) {
		return storeDao.find("mallName",mallName);
	}

	@Override
	@Async
	public void updateAccessToken(AccessTokenResponse response) {
		if(response.getErrorResponse()==null){
			Store store = findByMallId(Long.valueOf(response.getOwnerId()));
			if(store!=null){

			}
		}

	}

	@Override
	@Async
	public Store update(PddMallInfoGetResponse response) {
		if(response.getErrorResponse()==null){
			PddMallInfoGetResponse.MallInfoGetResponse mallInfoGetResponse = response.getMallInfoGetResponse();
			Store store = findByMallId(mallInfoGetResponse.getMallId());
			if(store!=null){
				store.setLogo(mallInfoGetResponse.getLogo());
				store.setMallDesc(mallInfoGetResponse.getMallDesc());
				store.setMallName(mallInfoGetResponse.getMallName());
				store.setMallId(mallInfoGetResponse.getMallId());
				return update(store);
			}else{
				store = new Store();
				store.setLogo(mallInfoGetResponse.getLogo());
				store.setMallDesc(mallInfoGetResponse.getMallDesc());
				store.setMallName(mallInfoGetResponse.getMallName());
				store.setMallId(mallInfoGetResponse.getMallId());
				return save(store);
			}
		}
		return null;
	}

	@Override
	public Store create(AccessToken accessToken){
		Store store = findByMallId(Long.valueOf(accessToken.getOwnerId()));
		if(store==null){
			try {
				store = new Store();
				store.setMallName(accessToken.getOwnerName());
				store.setMallId(Long.valueOf(accessToken.getOwnerId()));
				return super.save(store);
			}catch (Exception e){
				e.printStackTrace();
			}
		}
		return store;
	}
}