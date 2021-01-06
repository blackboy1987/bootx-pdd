package com.bootx.pdd.service;

import com.bootx.entity.Product;
import com.bootx.pdd.entity.AccessToken;
import com.pdd.pop.sdk.http.api.pop.response.PddGoodsImageUploadResponse;
import com.pdd.pop.sdk.http.api.pop.response.PddMallInfoGetResponse;

/**
 * @author blackoy
 */
public interface PddService {

    AccessToken token(String code);
    AccessToken refreshToken(String refreshToken);

    void addProduct(Product product,String accessToken);

    PddGoodsImageUploadResponse uploadImage(String imageBase64, String accessToken) throws Exception;

    PddMallInfoGetResponse storeInfo(String accessToken) throws Exception;
}
