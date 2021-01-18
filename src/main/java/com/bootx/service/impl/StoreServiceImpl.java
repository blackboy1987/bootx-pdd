
package com.bootx.service.impl;

import com.bootx.common.Message;
import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.dao.StoreDao;
import com.bootx.entity.Member;
import com.bootx.entity.Store;
import com.bootx.pdd.service.PddMallService;
import com.bootx.service.StoreService;
import com.bootx.util.DateUtils;
import com.pdd.pop.sdk.http.api.pop.response.PddMallInfoGetResponse;
import com.pdd.pop.sdk.http.token.AccessTokenResponse;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service - 审计日志
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Service
public class StoreServiceImpl extends BaseServiceImpl<Store, Long> implements StoreService {

	@Resource
	private StoreDao storeDao;
	@Resource
	private PddMallService pddMallService;

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

	@Override
	public List<Map<String, Object>> tree(Member member) {
		if(member==null){
			return Collections.emptyList();
		}
		List<Map<String, Object>> maps = jdbcTemplate.queryForList("select storecategory.id `key`,storecategory.name title,(select count(store.id) from store as store where store.member_id=" + member.getId() + " and store.storeCategory_id=storecategory.id) childrenCount from storecategory as storecategory where storecategory.member_id=" + member.getId() + ";");
		for (Map<String,Object> map:maps) {
			map.put("disableCheckbox",true);
			map.put("checkable",false);
			map.put("selectable",false);
			if(Integer.parseInt(map.get("childrenCount")+"")>0){
				map.put("children",jdbcTemplate.queryForList("select id `key`,mallName title from store where member_id="+member.getId()+" and storeCategory_id="+map.get("key")+";"));
			}
		}
		return maps.stream().filter(item->Integer.parseInt(item.get("childrenCount")+"")>0).collect(Collectors.toList());
	}

	@Override
	public PddMallInfoGetResponse info(Long id) throws Exception {
		Store store = find(id);
		PddMallInfoGetResponse pddMallInfoGetResponse = pddMallService.pddMallInfoGet(store.getAccessToken());

		return pddMallInfoGetResponse;

	}
}