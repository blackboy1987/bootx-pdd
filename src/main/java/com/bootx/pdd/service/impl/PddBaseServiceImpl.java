package com.bootx.pdd.service.impl;

import com.bootx.constants.PddConfig;
import com.bootx.pdd.service.PddBaseService;
import com.pdd.pop.sdk.http.PopAccessTokenClient;
import com.pdd.pop.sdk.http.PopClient;
import com.pdd.pop.sdk.http.PopHttpClient;
import com.pdd.pop.sdk.http.api.pop.request.PddGoodsCatsGetRequest;
import com.pdd.pop.sdk.http.api.pop.request.PddGoodsImageUploadRequest;
import com.pdd.pop.sdk.http.api.pop.request.PddMallInfoGetRequest;
import com.pdd.pop.sdk.http.api.pop.response.PddGoodsCatsGetResponse;
import com.pdd.pop.sdk.http.api.pop.response.PddGoodsImageUploadResponse;
import com.pdd.pop.sdk.http.api.pop.response.PddMallInfoGetResponse;
import com.pdd.pop.sdk.http.token.AccessTokenResponse;

/**
 * @author black
 */
public abstract class PddBaseServiceImpl implements PddBaseService {

    protected static final PopClient POPHTTPCLIENT = new PopHttpClient(PddConfig.clientId,PddConfig.clientSecret);

    @Override
    public PddGoodsImageUploadResponse uploadImage(String imageBase64, String accessToken) throws Exception {

        PddGoodsImageUploadRequest request = new PddGoodsImageUploadRequest();
        request.setImage(imageBase64);
        PddGoodsImageUploadResponse response = POPHTTPCLIENT.syncInvoke(request, PddConfig.accessToken);
        return response;
    }

    @Override
    public AccessTokenResponse token (String code) throws Exception {
        PopAccessTokenClient accessTokenClient = new PopAccessTokenClient(PddConfig.clientId,PddConfig.clientSecret);
        return accessTokenClient.generate(code);
    }

    @Override
    public AccessTokenResponse refreshToken(String refreshToken) throws Exception {
        PopAccessTokenClient accessTokenClient = new PopAccessTokenClient(PddConfig.clientId,PddConfig.clientSecret);
        return accessTokenClient.refresh(refreshToken);
    }


    @Override
    public PddMallInfoGetResponse storeInfo(String accessToken) throws Exception {
        PddMallInfoGetRequest request = new PddMallInfoGetRequest();
        return POPHTTPCLIENT.syncInvoke(request,  PddConfig.accessToken);
    }

    @Override
    public PddGoodsCatsGetResponse category(Long parentId) throws Exception {
        PddGoodsCatsGetRequest request = new PddGoodsCatsGetRequest();
        request.setParentCatId(parentId);
        PddGoodsCatsGetResponse response = POPHTTPCLIENT.syncInvoke(request);
        return response;
    }
}
