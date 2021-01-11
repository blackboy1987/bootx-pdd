package com.bootx.pdd.service;

import com.pdd.pop.sdk.http.api.pop.response.PddGoodsLogisticsTemplateGetResponse;
import com.pdd.pop.sdk.http.api.pop.response.PddLogisticsAddressGetResponse;

/**
 * @author black
 */
public interface LogisticsService extends PddService{

    PddLogisticsAddressGetResponse addressGet() throws Exception;

    PddGoodsLogisticsTemplateGetResponse templateGet(String accessToken) throws Exception;

}
