package com.bootx.pdd.service.impl;

import com.bootx.constants.PddConfig;
import com.bootx.entity.Product;
import com.bootx.entity.ProductImage;
import com.bootx.pdd.entity.PddGoodsAdd;
import com.bootx.pdd.service.GoodsService;
import com.bootx.util.ImageUtils;
import com.pdd.pop.sdk.common.util.JsonUtil;
import com.pdd.pop.sdk.http.api.pop.request.*;
import com.pdd.pop.sdk.http.api.pop.response.*;
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
        parseImage(product,accessToken);
        PddGoodsAddRequest request = PddGoodsAdd.build(product);
        PddGoodsAddResponse response = POPHTTPCLIENT.syncInvoke(request, PddConfig.accessToken);
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
    public void e(String accessToken) throws Exception {

    }

    @Override
    public void f(String accessToken) throws Exception {

    }

    @Override
    public void g(String accessToken) throws Exception {

    }

    private void parseImage(Product product, String accessToken) {
        // productImages
        product.setProductImages(product.getProductImages().stream().map(item->{
            ProductImage productImage = new ProductImage();
            try {
                PddGoodsImageUploadResponse pddGoodsImageUploadResponse = uploadImage(ImageUtils.url2Base64(productImage.getSource()), accessToken);
                String imageUrl = pddGoodsImageUploadResponse.getGoodsImageUploadResponse().getImageUrl();
                productImage.setOrder(item.getOrder());
                productImage.setLarge(imageUrl);
                productImage.setMedium(imageUrl);
                productImage.setSource(imageUrl);
                productImage.setThumbnail(imageUrl);
            } catch (Exception exception) {
                exception.printStackTrace();
            }

            return productImage;
        }).collect(Collectors.toList()));
    }
}
