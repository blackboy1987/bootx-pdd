
package com.bootx.service.impl;

import com.bootx.common.Message;
import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.dao.StoreDao;
import com.bootx.entity.Member;
import com.bootx.entity.Store;
import com.bootx.service.StoreService;
import com.bootx.util.DateUtils;
import com.pdd.pop.sdk.http.api.pop.response.PddMallInfoGetResponse;
import com.pdd.pop.sdk.http.token.AccessTokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

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
	public Store create(AccessTokenResponse accessTokenResponse,Member member){
		Store store = findByMallId(Long.valueOf(accessTokenResponse.getOwnerId()));
		if(store==null){
			try {
				store = new Store();
				if(member!=null){
					store.setMember(member);
				}
				store.setMallName(accessTokenResponse.getOwnerName());
				store.setMallId(Long.valueOf(accessTokenResponse.getOwnerId()));
				store.setAccessToken(accessTokenResponse.getAccessToken());
				store.setExpireDate(DateUtils.getNextSecond(accessTokenResponse.getExpiresIn()));
				return super.save(store);
			}catch (Exception e){
				e.printStackTrace();
			}
		}
		if(member!=null){
			store.setMember(member);
		}
		store.setAccessToken(accessTokenResponse.getAccessToken());
		store.setExpireDate(DateUtils.getNextSecond(accessTokenResponse.getExpiresIn()));
		return super.update(store);
	}

	@Override
	public List<Store> findList(Member member) {
		return storeDao.findList(member);
	}

	@Override
	public void flushAccessToken(Store store) {

	}

    @Override
    public Page<Store> findPage(Pageable pageable, Member member) {
		return storeDao.findPage(pageable,member);
    }

	@Override
	public Message unbind(Store store) {
		return Message.success("解绑成功");
	}
}