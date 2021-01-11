package com.bootx.pdd.service.impl;

import com.bootx.pdd.service.LogisticsService;
import com.pdd.pop.sdk.common.util.JsonUtil;
import com.pdd.pop.sdk.http.api.pop.request.PddGoodsLogisticsTemplateGetRequest;
import com.pdd.pop.sdk.http.api.pop.request.PddLogisticsAddressGetRequest;
import com.pdd.pop.sdk.http.api.pop.response.PddGoodsLogisticsTemplateGetResponse;
import com.pdd.pop.sdk.http.api.pop.response.PddLogisticsAddressGetResponse;

/**
 * @author black
 */
public class LogisticsServiceImpl extends PddBaseServiceImpl implements LogisticsService {


    @Override
    public PddLogisticsAddressGetResponse addressGet() throws Exception {
        PddLogisticsAddressGetRequest request = new PddLogisticsAddressGetRequest();
        PddLogisticsAddressGetResponse response = POPHTTPCLIENT.syncInvoke(request);
        System.out.println(JsonUtil.transferToJson(response));
        return response;
    }

    @Override
    public PddGoodsLogisticsTemplateGetResponse templateGet(String accessToken) throws Exception {
        PddGoodsLogisticsTemplateGetRequest request = new PddGoodsLogisticsTemplateGetRequest();
        PddGoodsLogisticsTemplateGetResponse response = POPHTTPCLIENT.syncInvoke(request,accessToken);
        System.out.println(JsonUtil.transferToJson(response));
        return response;
    }
}
