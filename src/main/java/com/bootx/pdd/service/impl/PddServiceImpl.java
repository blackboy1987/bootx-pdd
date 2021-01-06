package com.bootx.pdd.service.impl;

import com.bootx.constants.PddConfig;
import com.bootx.entity.Product;
import com.bootx.pdd.entity.AccessToken;
import com.bootx.pdd.entity.PddGoodsAdd;
import com.bootx.pdd.service.AccessTokenService;
import com.bootx.pdd.service.PddService;
import com.bootx.service.StoreService;
import com.pdd.pop.sdk.common.util.JsonUtil;
import com.pdd.pop.sdk.http.PopAccessTokenClient;
import com.pdd.pop.sdk.http.PopClient;
import com.pdd.pop.sdk.http.PopHttpClient;
import com.pdd.pop.sdk.http.api.pop.request.PddGoodsAddRequest;
import com.pdd.pop.sdk.http.api.pop.request.PddGoodsImageUploadRequest;
import com.pdd.pop.sdk.http.api.pop.request.PddMallInfoGetRequest;
import com.pdd.pop.sdk.http.api.pop.response.PddGoodsAddResponse;
import com.pdd.pop.sdk.http.api.pop.response.PddGoodsImageUploadResponse;
import com.pdd.pop.sdk.http.api.pop.response.PddMallInfoGetResponse;
import com.pdd.pop.sdk.http.token.AccessTokenResponse;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author blackoy
 */
@Service
public class PddServiceImpl implements PddService {

    public static final PopClient POPHTTPCLIENT = new PopHttpClient(PddConfig.clientId,PddConfig.clientSecret);

    @Resource
    private StoreService storeService;

    @Resource
    private AccessTokenService accessTokenService;

    @Override
    public AccessToken token (String code){
        PopAccessTokenClient accessTokenClient = new PopAccessTokenClient(PddConfig.clientId,PddConfig.clientSecret);
        try {
            AccessTokenResponse response = accessTokenClient.generate(code);
            // 如果成功更新store的Token信息
            return accessTokenService.create(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public AccessToken refreshToken(String refreshToken) {
        PopAccessTokenClient accessTokenClient = new PopAccessTokenClient(PddConfig.clientId,PddConfig.clientSecret);
        try {
            AccessTokenResponse response = accessTokenClient.refresh(refreshToken);
            return accessTokenService.create(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void addProduct(Product product,String accessToken) {


        PddGoodsAddRequest request = PddGoodsAdd.build(product);

        PddGoodsAddResponse response = null;
        try {
            response = POPHTTPCLIENT.syncInvoke(request, PddConfig.accessToken);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(JsonUtil.transferToJson(response));
    }

    @Override
    public PddGoodsImageUploadResponse uploadImage(String imageBase64,String accessToken) throws Exception {

        PddGoodsImageUploadRequest request = new PddGoodsImageUploadRequest();
        request.setImage(imageBase64);
        PddGoodsImageUploadResponse response = POPHTTPCLIENT.syncInvoke(request, PddConfig.accessToken);
        return response;
    }

    @Override
    public PddMallInfoGetResponse storeInfo(String accessToken) throws Exception {
        PddMallInfoGetRequest request = new PddMallInfoGetRequest();
        PddMallInfoGetResponse response = POPHTTPCLIENT.syncInvoke(request,  PddConfig.accessToken);
        storeService.update(response);
        return response;
    }
}
