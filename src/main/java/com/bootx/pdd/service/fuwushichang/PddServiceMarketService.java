package com.bootx.pdd.service.fuwushichang;

import com.bootx.pdd.service.PddBaseService;
import com.pdd.pop.sdk.http.api.pop.response.PddServicemarketContractSearchResponse;
import com.pdd.pop.sdk.http.api.pop.response.PddServicemarketSettlementbillGetResponse;
import com.pdd.pop.sdk.http.api.pop.response.PddServicemarketTradelistGetResponse;

public interface PddServiceMarketService extends PddBaseService {

    /**
     * 用于查询指定商家在服务市场订单执行履约的排序
     * @param mallId
     *  买家店铺id 必填
     * @return
     * @throws Exception
     */
    PddServicemarketContractSearchResponse contractSearch(Long mallId) throws Exception;

    /**
     * 用于ISV查询自己名下的服务的月度结算明细
     * @param page
     *  分页页码，最大不能超过1000 必填
     * @param pageSize
     *  分页大小，最大不能超过100  必填
     * @param orderSn
     *  服务订单号  非必填
     * @param month
     *  结算月份(2020-01)    必填
     * @return
     */
    PddServicemarketSettlementbillGetResponse settlementBill(Integer page, Integer pageSize, String orderSn, String month) throws Exception;

    /**
     * 用于ISV查询自己名下的服务的交易明细单
     * @param beginTime
     *      查询起始时间，精确到秒，起止时间间隔最大31天 必填
     * @param endTime
     *      查询结束时间，精确到秒，起止时间间隔最大31天 必填
     * @param groupType
     *  	收支类型，空-全部 1-收入 2-支出 非必填
     * @param page
     *      分页页码，最大1000 必填
     * @param pageSize
     *      分页大小，最大1000 必填
     * @param serviceOrderSn
     *      服务订单号   非必填
     * @return
     * @throws Exception
     */
    PddServicemarketTradelistGetResponse tradeList(Integer beginTime, Integer endTime, Integer groupType, Integer page, Integer pageSize, String serviceOrderSn) throws Exception;
}
