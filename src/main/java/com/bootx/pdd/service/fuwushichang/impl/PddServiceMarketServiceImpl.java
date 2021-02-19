package com.bootx.pdd.service.fuwushichang.impl;

import com.bootx.pdd.service.fuwushichang.PddServiceMarketService;
import com.bootx.pdd.service.impl.PddBaseServiceImpl;
import com.pdd.pop.sdk.common.util.JsonUtil;
import com.pdd.pop.sdk.http.api.pop.request.PddServicemarketContractSearchRequest;
import com.pdd.pop.sdk.http.api.pop.request.PddServicemarketSettlementbillGetRequest;
import com.pdd.pop.sdk.http.api.pop.request.PddServicemarketTradelistGetRequest;
import com.pdd.pop.sdk.http.api.pop.response.PddServicemarketContractSearchResponse;
import com.pdd.pop.sdk.http.api.pop.response.PddServicemarketSettlementbillGetResponse;
import com.pdd.pop.sdk.http.api.pop.response.PddServicemarketTradelistGetResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class PddServiceMarketServiceImpl extends PddBaseServiceImpl implements PddServiceMarketService {

    @Override
    public PddServicemarketContractSearchResponse contractSearch(Long mallId) throws Exception {
        PddServicemarketContractSearchRequest request = new PddServicemarketContractSearchRequest();
        request.setMallId(mallId);
        PddServicemarketContractSearchResponse response = popClient.syncInvoke(request);
        System.out.println(JsonUtil.transferToJson(response));
        return response;
    }

    @Override
    public PddServicemarketSettlementbillGetResponse settlementBill(Integer page, Integer pageSize, String serviceOrderSn, String settleMonth) throws Exception {

        PddServicemarketSettlementbillGetRequest request = new PddServicemarketSettlementbillGetRequest();
        request.setPage(page);
        request.setPageSize(pageSize);
        if(StringUtils.isNotBlank(serviceOrderSn)){
            request.setServiceOrderSn(serviceOrderSn);
        }
        request.setSettleMonth(settleMonth);
        PddServicemarketSettlementbillGetResponse response = popClient.syncInvoke(request);
        System.out.println(JsonUtil.transferToJson(response));
        return response;
    }

    @Override
    public PddServicemarketTradelistGetResponse tradeList(Integer beginTime,Integer endTime,Integer groupType,Integer page, Integer pageSize,String serviceOrderSn) throws Exception {
        PddServicemarketTradelistGetRequest request = new PddServicemarketTradelistGetRequest();
        request.setBeginTime(beginTime);
        request.setEndTime(endTime);
        if(groupType==null){
            request.setGroupType(groupType);
        }
        request.setPage(page);
        request.setPageSize(pageSize);
        if(StringUtils.isNotBlank(serviceOrderSn)){
            request.setServiceOrderSn(serviceOrderSn);
        }
        PddServicemarketTradelistGetResponse response = popClient.syncInvoke(request);
        System.out.println(JsonUtil.transferToJson(response));
        return response;
    }
}
