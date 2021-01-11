package com.bootx.pdd.service.impl;

import com.bootx.common.Pageable;
import com.bootx.constants.PddConfig;
import com.bootx.entity.Product;
import com.bootx.pdd.entity.PddGoodsAdd;
import com.bootx.pdd.service.GoodsService;
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
public class GoodsServiceImpl extends PddBaseServiceImpl implements GoodsService {

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
    public PddGoodsAddResponse pddGoodsAdd(Product product, String accessToken) throws Exception {
        // 处理掉product中的图片
        PddGoodsAdd pddGoodsAdd = new PddGoodsAdd();
        pddGoodsAdd.setCarouselGallerys(parseImage(product,accessToken));
        pddGoodsAdd.setDetailGallerys(parseDetailImage(product,accessToken));


        PddGoodsAddRequest request = pddGoodsAdd.build(product);
        PddGoodsAddResponse response = POPHTTPCLIENT.syncInvoke(request, PddConfig.accessToken);
        System.out.println(JsonUtil.transferToJson(response));
        return response;
    }

    @Override
    public PddGoodsEditGoodsCommitResponse pddGoodsEditGoodsCommit(Product product, String accessToken) throws Exception {
        // 处理掉product中的图片
        PddGoodsAdd pddGoodsAdd = new PddGoodsAdd();
        pddGoodsAdd.setCarouselGallerys(parseImage(product,accessToken));
        pddGoodsAdd.setDetailGallerys(parseDetailImage(product,accessToken));


        PddGoodsEditGoodsCommitRequest request = pddGoodsAdd.build1(product);
        PddGoodsEditGoodsCommitResponse response = POPHTTPCLIENT.syncInvoke(request, PddConfig.accessToken);
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

    private List<String> parseImage(Product product, String accessToken) {
        // productImages
        return product.getProductImages().stream().map(item->{
           if(StringUtils.isNotBlank(item.getSource())){
               try {
                   PddGoodsImageUploadResponse pddGoodsImageUploadResponse = uploadImage(ImageUtils.url2Base64(item.getSource()), accessToken);
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

    private List<String> parseDetailImage(Product product, String accessToken) {
        // productImages
        List<String> images = product.getProductIntroductionImage().getImages().stream().map(item->{
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
