package com.bootx.pdd.service.impl;

import com.bootx.common.Pageable;
import com.bootx.entity.Sku;
import com.bootx.entity.StoreUploadConfig;
import com.bootx.pdd.entity.PddCrawlerProduct;
import com.bootx.pdd.entity.PddGoodsAdd;
import com.bootx.pdd.service.PddGoodsService;
import com.bootx.util.ImageUtils;
import com.bootx.util.JsonUtils;
import com.pdd.pop.sdk.common.util.JsonUtil;
import com.pdd.pop.sdk.http.api.pop.request.*;
import com.pdd.pop.sdk.http.api.pop.response.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author blackoy
 */
@Service
public class PddGoodsServiceImpl extends PddBaseServiceImpl implements PddGoodsService {

    @Override
    public PddDeleteDraftCommitResponse pddDeleteDraftCommit(String accessToken) throws Exception {
        PddDeleteDraftCommitRequest request = new PddDeleteDraftCommitRequest();
        request.setGoodsCommitId(0L);
        PddDeleteDraftCommitResponse response = popClient.syncInvoke(request, accessToken);
        System.out.println(JsonUtil.transferToJson(response));
        return response;
    }
    @Override
    public PddDeleteGoodsCommitResponse pddDeleteGoodsCommit(String accessToken) throws Exception {
        PddDeleteGoodsCommitRequest request = new PddDeleteGoodsCommitRequest();
        List<Long> goodsIds = new ArrayList<Long>();
        goodsIds.add(0L);
        request.setGoodsIds(goodsIds);
        PddDeleteGoodsCommitResponse response = popClient.syncInvoke(request, accessToken);
        System.out.println(JsonUtil.transferToJson(response));
        return response;
    }

    @Override
    public PddGoodsAddResponse pddGoodsAdd(PddCrawlerProduct pddCrawlerProduct, List<Sku> skus, String accessToken, StoreUploadConfig storeUploadConfig) throws Exception {
        // 处理掉product中的图片
        PddGoodsAdd pddGoodsAdd = new PddGoodsAdd();
        pddGoodsAdd.setCarouselGallerys(parseImage(pddCrawlerProduct,accessToken));
        pddGoodsAdd.setDetailGallerys(parseDetailImage(pddCrawlerProduct,accessToken));
        PddGoodsAddRequest request = pddGoodsAdd.build(pddCrawlerProduct,skus,storeUploadConfig);

        System.out.println(JsonUtils.toJson(request));
        PddGoodsAddResponse response = popClient.syncInvoke(request, accessToken);
        System.out.println(JsonUtil.transferToJson(response));
        return response;
    }

    @Override
    public PddGoodsEditGoodsCommitResponse pddGoodsEditGoodsCommit(PddCrawlerProduct pddCrawlerProduct, String accessToken) throws Exception {
        // 处理掉product中的图片
        /*PddGoodsAdd pddGoodsAdd = new PddGoodsAdd();
        pddGoodsAdd.setCarouselGallerys(parseImage(pddCrawlerProduct,accessToken));
        pddGoodsAdd.setDetailGallerys(parseDetailImage(pddCrawlerProduct,accessToken));
        PddGoodsEditGoodsCommitRequest request = pddGoodsAdd.build(pddCrawlerProduct);
        PddGoodsEditGoodsCommitResponse response = popClient.syncInvoke(request, accessToken);
        System.out.println(JsonUtil.transferToJson(response));*/
        return null;
    }

    @Override
    public PddGoodsAuthorizationCatsResponse pddGoodsAuthorizationCats(String accessToken) throws Exception {

        PddGoodsAuthorizationCatsRequest request = new PddGoodsAuthorizationCatsRequest();
        request.setParentCatId(0L);
        PddGoodsAuthorizationCatsResponse response = popClient.syncInvoke(request, accessToken);
        System.out.println(JsonUtil.transferToJson(response));
        return response;
    }

    @Override
    public PddGoodsCatRuleGetResponse pddGoodsCatRuleGet(String accessToken,Long catId) throws Exception {

        PddGoodsCatRuleGetRequest request = new PddGoodsCatRuleGetRequest();
        request.setCatId(catId);
        PddGoodsCatRuleGetResponse response = popClient.syncInvoke(request, accessToken);
        System.out.println(JsonUtil.transferToJson(response));
        return response;
    }

    @Override
    public PddGoodsCatTemplateGetResponse pddGoodsCatTemplateGet(String accessToken,Long catId) throws Exception {
        PddGoodsCatTemplateGetRequest request = new PddGoodsCatTemplateGetRequest();
        request.setCatId(catId);
        PddGoodsCatTemplateGetResponse response = popClient.syncInvoke(request, accessToken);
        System.out.println(JsonUtil.transferToJson(response));
        return response;
    }

    @Override
    public PddGoodsCatsGetResponse pddGoodsCatsGet(String accessToken,Long catId) throws Exception {
        PddGoodsCatsGetRequest request = new PddGoodsCatsGetRequest();
        request.setParentCatId(0L);
        PddGoodsCatsGetResponse response = popClient.syncInvoke(request);
        System.out.println(JsonUtil.transferToJson(response));
        return response;
    }

    @Override
    public void commitListGet(String accessToken, Integer checkStatus, Long goodsId, Pageable pageable) throws Exception {
        PddGoodsCommitListGetRequest request = new PddGoodsCommitListGetRequest();
        if(checkStatus==null){
            request.setCheckStatus(0);
        }else{
            request.setCheckStatus(checkStatus);
        }

        if(goodsId!=null){
            request.setGoodsId(goodsId);
        }
        request.setPage(pageable.getPageNumber());
        request.setPageSize(pageable.getPageSize());
        PddGoodsCommitListGetResponse response = popClient.syncInvoke(request, accessToken);
        System.out.println(JsonUtil.transferToJson(response));
    }

    @Override
    public PddGoodsSpecIdGetResponse specIdGet(Long parentSpecId, String specName, String accessToken) throws Exception {
        PddGoodsSpecIdGetRequest request = new PddGoodsSpecIdGetRequest();
        request.setParentSpecId(parentSpecId);
        request.setSpecName(specName);
        PddGoodsSpecIdGetResponse response = popClient.syncInvoke(request, accessToken);
        System.out.println(JsonUtil.transferToJson(response));
        return response;
    }

    @Override
    public PddGoodsSpecGetResponse specGet(Long categoryId,String accessToken) throws Exception {
        PddGoodsSpecGetRequest request = new PddGoodsSpecGetRequest();
        request.setCatId(categoryId);
        PddGoodsSpecGetResponse response = popClient.syncInvoke(request, accessToken);
        System.out.println(JsonUtil.transferToJson(response));
        return response;
    }

    private List<String> parseImage(PddCrawlerProduct pddCrawlerProduct, String accessToken) {
        // productImages
        return pddCrawlerProduct.getCrawlerProductImage().getImages().stream().map(item->{
           if(StringUtils.isNotBlank(item)){
               String s = ImageUtils.url2Base64(item);
               if(StringUtils.isBlank(s)){
                   return null;
               }
               try {
                   PddGoodsImageUploadResponse pddGoodsImageUploadResponse = uploadImage(s, accessToken);
                   if(pddGoodsImageUploadResponse.getErrorResponse()==null){
                       return pddGoodsImageUploadResponse.getGoodsImageUploadResponse().getImageUrl();
                   }
                   return null;
               } catch (Exception exception) {
                   exception.printStackTrace();
               }
           }
            return null;
        }).filter(StringUtils::isNotBlank).collect(Collectors.toList());
    }

    private List<String> parseDetailImage(PddCrawlerProduct pddCrawlerProduct, String accessToken) {
        // productImages
        List<String> images = pddCrawlerProduct.getCrawlerProductIntroductionImage().getImages().stream().map(item->{
            if(StringUtils.isNotBlank(item)){
                String s = ImageUtils.url2Base64(item);
                if(StringUtils.isBlank(s)){
                    return null;
                }
                try {
                    PddGoodsImageUploadResponse pddGoodsImageUploadResponse = uploadImage(s, accessToken);
                    if(pddGoodsImageUploadResponse.getErrorResponse()==null){
                        return pddGoodsImageUploadResponse.getGoodsImageUploadResponse().getImageUrl();
                    }
                    return null;
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
            return null;
        }).filter(StringUtils::isNotBlank).collect(Collectors.toList());

        return images;
    }
}
