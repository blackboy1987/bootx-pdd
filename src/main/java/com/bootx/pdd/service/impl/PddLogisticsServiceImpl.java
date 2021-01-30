package com.bootx.pdd.service.impl;

import com.bootx.pdd.service.PddLogisticsService;
import com.pdd.pop.sdk.common.util.JsonUtil;
import com.pdd.pop.sdk.http.api.pop.request.PddGoodsLogisticsTemplateGetRequest;
import com.pdd.pop.sdk.http.api.pop.request.PddLogisticsAddressGetRequest;
import com.pdd.pop.sdk.http.api.pop.response.PddGoodsLogisticsTemplateGetResponse;
import com.pdd.pop.sdk.http.api.pop.response.PddLogisticsAddressGetResponse;
import org.springframework.stereotype.Service;

/**
 * @author black
 */
@Service
public class PddLogisticsServiceImpl extends PddBaseServiceImpl implements PddLogisticsService {


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
