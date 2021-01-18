package com.bootx.pdd.service.impl;

import com.bootx.common.Pageable;
import com.bootx.pdd.entity.PddCrawlerProduct;
import com.bootx.pdd.entity.PddGoodsAdd;
import com.bootx.pdd.service.PddGoodsService;
import com.bootx.util.ImageUtils;
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
        PddDeleteDraftCommitResponse response = POPHTTPCLIENT.syncInvoke(request, accessToken);
        System.out.println(JsonUtil.transferToJson(response));
        return response;
    }
    @Override
    public PddDeleteGoodsCommitResponse pddDeleteGoodsCommit(String accessToken) throws Exception {
        PddDeleteGoodsCommitRequest request = new PddDeleteGoodsCommitRequest();
        List<Long> goodsIds = new ArrayList<Long>();
        goodsIds.add(0L);
        request.setGoodsIds(goodsIds);
        PddDeleteGoodsCommitResponse response = POPHTTPCLIENT.syncInvoke(request, accessToken);
        System.out.println(JsonUtil.transferToJson(response));
        return response;
    }

    @Override
    public PddGoodsAddResponse pddGoodsAdd(PddCrawlerProduct pddCrawlerProduct, String accessToken) throws Exception {
        // 处理掉product中的图片
        PddGoodsAdd pddGoodsAdd = new PddGoodsAdd();
        pddGoodsAdd.setCarouselGallerys(parseImage(pddCrawlerProduct,accessToken));
        pddGoodsAdd.setDetailGallerys(parseDetailImage(pddCrawlerProduct,accessToken));
        PddGoodsAddRequest request = pddGoodsAdd.build(pddCrawlerProduct);
        PddGoodsAddResponse response = POPHTTPCLIENT.syncInvoke(request, accessToken);
        System.out.println(JsonUtil.transferToJson(response));
        return response;
    }

    @Override
    public PddGoodsEditGoodsCommitResponse pddGoodsEditGoodsCommit(PddCrawlerProduct pddCrawlerProduct, String accessToken) throws Exception {
        // 处理掉product中的图片
        PddGoodsAdd pddGoodsAdd = new PddGoodsAdd();
        pddGoodsAdd.setCarouselGallerys(parseImage(pddCrawlerProduct,accessToken));
        pddGoodsAdd.setDetailGallerys(parseDetailImage(pddCrawlerProduct,accessToken));


        PddGoodsEditGoodsCommitRequest request = pddGoodsAdd.build1(pddCrawlerProduct);
        PddGoodsEditGoodsCommitResponse response = POPHTTPCLIENT.syncInvoke(request, accessToken);
        System.out.println(JsonUtil.transferToJson(response));
        return response;
    }

    @Override
    public PddGoodsAuthorizationCatsResponse pddGoodsAuthorizationCats(String accessToken) throws Exception {

        PddGoodsAuthorizationCatsRequest request = new PddGoodsAuthorizationCatsRequest();
        request.setParentCatId(0L);
        PddGoodsAuthorizationCatsResponse response = POPHTTPCLIENT.syncInvoke(request, accessToken);
        System.out.println(JsonUtil.transferToJson(response));
        return response;
    }

    @Override
    public PddGoodsCatRuleGetResponse pddGoodsCatRuleGet(String accessToken,Long catId) throws Exception {

        PddGoodsCatRuleGetRequest request = new PddGoodsCatRuleGetRequest();
        request.setCatId(catId);
        PddGoodsCatRuleGetResponse response = POPHTTPCLIENT.syncInvoke(request, accessToken);
        System.out.println(JsonUtil.transferToJson(response));
        return response;
    }

    @Override
    public PddGoodsCatTemplateGetResponse pddGoodsCatTemplateGet(String accessToken,Long catId) throws Exception {
        PddGoodsCatTemplateGetRequest request = new PddGoodsCatTemplateGetRequest();
        request.setCatId(catId);
        PddGoodsCatTemplateGetResponse response = POPHTTPCLIENT.syncInvoke(request, accessToken);
        System.out.println(JsonUtil.transferToJson(response));
        return response;
    }

    @Override
    public PddGoodsCatsGetResponse pddGoodsCatsGet(String accessToken,Long catId) throws Exception {
        PddGoodsCatsGetRequest request = new PddGoodsCatsGetRequest();
        request.setParentCatId(0L);
        PddGoodsCatsGetResponse response = POPHTTPCLIENT.syncInvoke(request);
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
        PddGoodsCommitListGetResponse response = POPHTTPCLIENT.syncInvoke(request, accessToken);
        System.out.println(JsonUtil.transferToJson(response));
    }

    @Override
    public void f(String accessToken) throws Exception {

    }

    @Override
    public void g(String accessToken) throws Exception {

    }

    private List<String> parseImage(PddCrawlerProduct pddCrawlerProduct, String accessToken) {
        // productImages
        return pddCrawlerProduct.getCrawlerProductImage().getImages().stream().map(item->{
           if(StringUtils.isNotBlank(item)){
               try {
                   PddGoodsImageUploadResponse pddGoodsImageUploadResponse = uploadImage(ImageUtils.url2Base64(item), accessToken);
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
                try {
                    PddGoodsImageUploadResponse pddGoodsImageUploadResponse = uploadImage(ImageUtils.url2Base64(item), accessToken);
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
