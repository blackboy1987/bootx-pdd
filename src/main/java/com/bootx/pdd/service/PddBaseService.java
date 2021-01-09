package com.bootx.pdd.service;

import com.pdd.pop.sdk.http.api.pop.response.PddGoodsCatsGetResponse;
import com.pdd.pop.sdk.http.api.pop.response.PddGoodsImageUploadResponse;
import com.pdd.pop.sdk.http.api.pop.response.PddMallInfoGetResponse;
import com.pdd.pop.sdk.http.token.AccessTokenResponse;

/**
 * @author black
 */
public interface PddBaseService {
    /**
     * 图片上传
     * @param imageBase64
     *      imageBase64
     * @param accessToken
     *      accessToken
     * @return
     *      PddGoodsImageUploadResponse
     * @throws Exception
     */
    PddGoodsImageUploadResponse uploadImage(String imageBase64, String accessToken) throws Exception;

    AccessTokenResponse token(String code) throws Exception;
    AccessTokenResponse refreshToken(String refreshToken) throws Exception;

    PddMallInfoGetResponse storeInfo(String accessToken) throws Exception;

    PddGoodsCatsGetResponse category(Long parentId) throws Exception;
}
