package com.bootx.util.pdd;

import com.pdd.pop.sdk.common.util.JsonUtil;
import com.pdd.pop.sdk.http.PopClient;
import com.pdd.pop.sdk.http.api.pop.request.PddServicemarketContractSearchRequest;
import com.pdd.pop.sdk.http.api.pop.response.PddServicemarketContractSearchResponse;

public final class FuWuShiChang {

    /**
     * pdd.servicemarket.contract.search服务市场订单履约查询
     */

    public static PddServicemarketContractSearchResponse.ServicemarketContractSearchResponse contractSearch(Long mallId,PopClient popClient){
        PddServicemarketContractSearchRequest request = new PddServicemarketContractSearchRequest();
        request.setMallId(mallId);
        PddServicemarketContractSearchResponse.ServicemarketContractSearchResponse servicemarketContractSearchResponse = null;
        try {
            PddServicemarketContractSearchResponse response = popClient.syncInvoke(request);
            System.out.println(JsonUtil.transferToJson(response));
            if(response.getErrorResponse()!=null){
                throw PddException.toException(response.getErrorResponse());
            }
            servicemarketContractSearchResponse = response.getServicemarketContractSearchResponse();
        }catch (Exception e){
            e.printStackTrace();
        }
        return servicemarketContractSearchResponse;
    }
}
