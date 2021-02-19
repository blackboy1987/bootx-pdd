
package com.bootx.service.impl;

import com.bootx.common.Message;
import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.constants.PddConfig;
import com.bootx.dao.StoreDao;
import com.bootx.entity.*;
import com.bootx.pdd.service.PddLogisticsService;
import com.bootx.pdd.service.PddMallService;
import com.bootx.service.MemberService;
import com.bootx.service.StoreCategoryService;
import com.bootx.service.StoreService;
import com.bootx.util.DateUtils;
import com.bootx.util.pdd.FuWuShiChang;
import com.pdd.pop.sdk.http.api.pop.response.PddGoodsLogisticsTemplateGetResponse;
import com.pdd.pop.sdk.http.api.pop.response.PddMallInfoGetResponse;
import com.pdd.pop.sdk.http.api.pop.response.PddServicemarketContractSearchResponse;
import com.pdd.pop.sdk.http.token.AccessTokenResponse;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
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
	@Resource
	private MemberService memberService;
	@Resource
	private StoreCategoryService storeCategoryService;
	@Resource
	private PddLogisticsService pddLogisticsService;

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
		if(member==null){
			member = memberService.create(accessTokenResponse);
		}
		StoreCategory storeCategory = storeCategoryService.findDefault(member);
		Store store = findByMallId(Long.valueOf(accessTokenResponse.getOwnerId()));
		if(store==null){
			store = new Store();
			store.setMallName(accessTokenResponse.getOwnerName());
			store.setMallId(Long.valueOf(accessTokenResponse.getOwnerId()));
			store.setAccessToken(accessTokenResponse.getAccessToken());
			store.setExpireDate(DateUtils.getNextSecond(accessTokenResponse.getExpiresIn()));
			store.setStoreCategory(storeCategory);
			store.setMember(member);
			/**
			 * 获取店铺的订单信息
			 */
			PddServicemarketContractSearchResponse.ServicemarketContractSearchResponse servicemarketContractSearchResponse = FuWuShiChang.contractSearch(store.getMallId(), PddConfig.popClient);
			Long endAt = servicemarketContractSearchResponse.getEndAt();
			Long startAt = servicemarketContractSearchResponse.getStartAt();
			store.setEndAt(endAt);
			store.setStartAt(startAt);
			return super.save(store);
		}
		store.setAccessToken(accessTokenResponse.getAccessToken());
		store.setExpireDate(DateUtils.getNextSecond(accessTokenResponse.getExpiresIn()));
		if(store.getEndAt()==null){
			PddServicemarketContractSearchResponse.ServicemarketContractSearchResponse servicemarketContractSearchResponse = FuWuShiChang.contractSearch(store.getMallId(), PddConfig.popClient);
			Long endAt = servicemarketContractSearchResponse.getEndAt();
			Long startAt = servicemarketContractSearchResponse.getStartAt();
			store.setEndAt(endAt);
			store.setStartAt(startAt);
		}
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
		jdbcTemplate.update("delete from pddpublishlog where store_id="+store.getId());
		super.delete(store);
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

	@Override
	public Long count1(Member member) {
		return jdbcTemplate.queryForObject("select count(id) from store where member_id="+member.getId(),Long.class);
	}

	@Override
	public Store create1(AccessTokenResponse accessTokenResponse, Member member1) {
		Member member = memberService.create(accessTokenResponse);
		StoreCategory storeCategory = storeCategoryService.findDefault(member);
		Store store = findByMallId(Long.valueOf(accessTokenResponse.getOwnerId()));
		if(store==null){
			store = new Store();
			store.setMallName(accessTokenResponse.getOwnerName());
			store.setMallId(Long.valueOf(accessTokenResponse.getOwnerId()));
			store.setAccessToken(accessTokenResponse.getAccessToken());
			store.setExpireDate(DateUtils.getNextSecond(accessTokenResponse.getExpiresIn()));
			store.setMember(member);
			if(member1!=null){
				store.setSalesMan(member);
			}
			store.setStoreCategory(storeCategory);
			return super.save(store);
		}
		store.setAccessToken(accessTokenResponse.getAccessToken());
		store.setExpireDate(DateUtils.getNextSecond(accessTokenResponse.getExpiresIn()));
		return super.update(store);
	}

	@Override
	public List<StoreDeliveryTemplate> getStoreDeliveryTemplate(Store store) throws Exception {
		if(store==null){
			return Collections.emptyList();
		}
		PddGoodsLogisticsTemplateGetResponse pddGoodsLogisticsTemplateGetResponse = pddLogisticsService.templateGet(store.getAccessToken());
		if(pddGoodsLogisticsTemplateGetResponse.getErrorResponse()==null){
			PddGoodsLogisticsTemplateGetResponse.GoodsLogisticsTemplateGetResponse goodsLogisticsTemplateGetResponse = pddGoodsLogisticsTemplateGetResponse.getGoodsLogisticsTemplateGetResponse();
			return goodsLogisticsTemplateGetResponse.getLogisticsTemplateList().stream().map(item->{
				StoreDeliveryTemplate storeDeliveryTemplate = new StoreDeliveryTemplate();
				storeDeliveryTemplate.setTemplateId(item.getTemplateId());
				storeDeliveryTemplate.setTemplateName(item.getTemplateName());
				return storeDeliveryTemplate;
			}).collect(Collectors.toList());

		}
		return Collections.emptyList();
	}

	@Override
	public List<StoreDeliveryTemplate> updateDeliveryTemplate(Long id) throws Exception {
		Store store = find(id);
		if(store!=null){
			List<StoreDeliveryTemplate> storeDeliveryTemplate = getStoreDeliveryTemplate(store);
			store.setStoreDeliveryTemplates(storeDeliveryTemplate);
			super.update(store);
			return storeDeliveryTemplate;
		}
		return Collections.emptyList();
	}


	public StoreUploadConfig build(StoreUploadConfig storeUploadConfig) {
		if (storeUploadConfig==null){
			storeUploadConfig = new StoreUploadConfig();
		}
		if(storeUploadConfig.getAddAfter()==null){
			storeUploadConfig.setAddAfter(false);
		}

		if(storeUploadConfig.getAddAfterWord()==null){
			storeUploadConfig.setAddAfterWord("");
		}

		if(storeUploadConfig.getAddBefore()==null){
			storeUploadConfig.setAddBefore(false);
		}

		if(storeUploadConfig.getAddABeforeWord()==null){
			storeUploadConfig.setAddABeforeWord("");
		}

		if(storeUploadConfig.getCarouselAddTen()==null){
			storeUploadConfig.setCarouselAddTen(false);
		}

		if(storeUploadConfig.getCarouselIndex()==null){
			storeUploadConfig.setCarouselIndex(0);
		}

		if(storeUploadConfig.getCarouselRandom()==null){
			storeUploadConfig.setCarouselRandom(false);
		}

		if(storeUploadConfig.getCostTemplateId()==null){
			storeUploadConfig.setCostTemplateId(0L);
		}

		if(storeUploadConfig.getDelete()==null){
			storeUploadConfig.setDelete(false);
		}

		if(storeUploadConfig.getDeleteWord()==null){
			storeUploadConfig.setDeleteWord("");
		}

		if(storeUploadConfig.getDetailPicDelEnd()==null){
			storeUploadConfig.setDetailPicDelEnd(null);
		}

		if(storeUploadConfig.getDetailPicDelStart()==null){
			storeUploadConfig.setDetailPicDelStart(null);
		}

		if(storeUploadConfig.getDetailPicEnd()==null){
			storeUploadConfig.setDetailPicEnd(null);
		}

		if(storeUploadConfig.getDetailPicHeader()==null){
			storeUploadConfig.setDetailPicHeader(null);
		}

		if(storeUploadConfig.getFilter()==null){
			storeUploadConfig.setFilter(true);
		}

		if(storeUploadConfig.getGoodsType()==null){
			storeUploadConfig.setGoodsType(1);
		}

		if(storeUploadConfig.getGroupPriceRate()==null){
			storeUploadConfig.setGroupPriceRate(new BigDecimal(1.08));
		}

		if(storeUploadConfig.getGroupPriceType()==null){
			storeUploadConfig.setGroupPriceType(3);
		}

		if(storeUploadConfig.getIsFolt()==null){
			storeUploadConfig.setIsFolt(true);
		}

		if(storeUploadConfig.getIsPreSale()==null){
			storeUploadConfig.setIsPreSale(false);
		}

		if(storeUploadConfig.getIsRefundable()==null){
			storeUploadConfig.setIsRefundable(true);
		}

		if(storeUploadConfig.getLackStockBase1()==null){
			storeUploadConfig.setLackStockBase1(0L);
		}

		if(storeUploadConfig.getLackStockBase2()==null){
			storeUploadConfig.setLackStockBase2(100L);
		}

		if(storeUploadConfig.getMarkerPriceRate()==null){
			storeUploadConfig.setMarkerPriceRate(new BigDecimal(1.08));
		}

		if(storeUploadConfig.getMarkerPriceType()==null){
			storeUploadConfig.setMarkerPriceType(3);
		}

		if(storeUploadConfig.getNewWord()==null){
			storeUploadConfig.setNewWord("");
		}

		if(storeUploadConfig.getOldWord()==null){
			storeUploadConfig.setOldWord("");
		}

		if(storeUploadConfig.getPreSaleTime()==null){
			storeUploadConfig.setPreSaleTime(null);
		}

		if(storeUploadConfig.getRandomTitle()==null){
			storeUploadConfig.setRandomTitle(false);
		}

		if(storeUploadConfig.getReplace()==null){
			storeUploadConfig.setReplace(false);
		}

		if(storeUploadConfig.getSecondHand()==null){
			storeUploadConfig.setSecondHand(false);
		}

		if(storeUploadConfig.getShipmentLimitSecond()==null){
			storeUploadConfig.setShipmentLimitSecond(86400L);
		}

		if(storeUploadConfig.getSinglePriceRate()==null){
			storeUploadConfig.setSinglePriceRate(new BigDecimal(1.08));
		}

		if(storeUploadConfig.getSinglePriceType()==null){
			storeUploadConfig.setSinglePriceType(3);
		}

		if(storeUploadConfig.getSkuPic()==null){
			storeUploadConfig.setSkuPic(0);
		}

		if(storeUploadConfig.getSkuSnType()==null){
			storeUploadConfig.setSkuSnType(0);
		}

		if(storeUploadConfig.getSkuPrefix()==null){
			storeUploadConfig.setSkuPrefix("");
		}

		if(storeUploadConfig.getSkuSuffix()==null){
			storeUploadConfig.setSkuSuffix("");
		}

		if(storeUploadConfig.getStockBase()==null){
			storeUploadConfig.setStockBase(100L);
		}

		if(storeUploadConfig.getStockConfig()==null){
			storeUploadConfig.setStockConfig(1);
		}

		if(storeUploadConfig.getTitleDealType()==null){
			storeUploadConfig.setTitleDealType(0);
		}
		// 相当于对标题长度不进行出来

		if(storeUploadConfig.getTitleMaxLength()==null){
			storeUploadConfig.setTitleMaxLength(1000);
		}

		if(storeUploadConfig.getUploadType()==null){
			storeUploadConfig.setUploadType(1);
		}
		return storeUploadConfig;




	}
}