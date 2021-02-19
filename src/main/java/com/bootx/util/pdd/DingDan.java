package com.bootx.util.pdd;

import com.pdd.pop.sdk.common.util.JsonUtil;
import com.pdd.pop.sdk.http.PopClient;
import com.pdd.pop.sdk.http.api.pop.request.*;
import com.pdd.pop.sdk.http.api.pop.response.*;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotNull;
import java.util.List;

public final class DingDan {

    /**
     * pdd.erp.order.syncerp打单信息同步
     * erp打单信息同步
     * @param orderSn
     *      必填	订单号
     * @param orderState
     *      必填	订单状态：1-已打单
     * @param waybillNo
     *      必填	运单号
     * @param logisticsId
     *      必填	物流公司编码
     * @param accessToken
     *      accessToken
     * @return
     * @throws Exception
     */
    public static PddErpOrderSyncResponse syncerp(@NotNull String orderSn,@NotNull Integer orderState,@NotNull String waybillNo,@NotNull Long logisticsId,@NotNull String accessToken) throws Exception {
        PddErpOrderSyncRequest request = new PddErpOrderSyncRequest();
        request.setOrderSn(orderSn);
        request.setOrderState(orderState);
        request.setWaybillNo(waybillNo);
        request.setLogisticsId(logisticsId);
        PddErpOrderSyncResponse response = Http.http(request,accessToken);
        return response;
    }

    /**
     * pdd.order.basic.list.get订单基础信息列表查询接口（根据成交时间）
     * 根据成团时间查询订单列表，只有订单基础信息，不包含消费者信息
     * @param endConfirmAt
     *      必填	必填，成交时间结束时间的时间戳，指格林威治时间 1970 年 01 月 01 日 00 时 00 分 00 秒(北京时间 1970 年 01 月 01 日 08 时 00 分 00 秒)起至现在的总秒数 PS：开始时间结束时间间距不超过 24 小时
     * @param orderStatus
     *      必填	发货状态，1：待发货，2：已发货待签收，3：已签收 5：全部
     * @param page
     *      必填	返回页码 默认 1，页码从 1 开始 PS：当前采用分页返回，数量和页数会一起传，如果不传，则采用 默认值
     * @param pageSize
     *      必填	返回数量，默认 100。最大 100
     * @param refundStatus
     *      必填	售后状态 1：无售后或售后关闭，2：售后处理中，3：退款中，4： 退款成功 5：全部
     * @param startConfirmAt
     *      必填	必填，成交时间开始时间的时间戳，指格林威治时间 1970 年 01 月 01 日 00 时 00 分 00 秒(北京时间 1970 年 01 月 01 日 08 时 00 分 00 秒)起至现在的总秒数
     * @param tradeType
     *      非必填	订单类型 0-普通订单 ，1- 定金订单
     * @param useHasNext
     *      非必填	是否启用has_next的分页方式，如果指定true,则返回的结果中不包含总记录数，但是会新增一个是否存在下一页的的字段，通过此种方式获取增量交易，效率在原有的基础上有80%的提升。
     * @param accessToken
     *      accessToken
     * @return
     * @throws Exception
     */
    public static PddOrderBasicListGetResponse.OrderBasicListGetResponse basicListGet(@NotNull Integer endConfirmAt, @NotNull Integer orderStatus, @NotNull Integer page, @NotNull Integer pageSize, @NotNull Integer refundStatus, @NotNull Integer startConfirmAt, Integer tradeType, Boolean useHasNext, @NotNull String accessToken) throws Exception {

        PddOrderBasicListGetRequest request = new PddOrderBasicListGetRequest();
        request.setEndConfirmAt(endConfirmAt);
        request.setOrderStatus(orderStatus);
        request.setPage(page);
        request.setPageSize(pageSize);
        request.setRefundStatus(refundStatus);
        request.setStartConfirmAt(startConfirmAt);
        if(tradeType!=null){
            request.setTradeType(tradeType);
        }

        if(useHasNext!=null){
            request.setUseHasNext(useHasNext);
        }

        PddOrderBasicListGetResponse response = Http.http(request,accessToken);
        return response.getOrderBasicListGetResponse();
    }

    /**
     * pdd.order.information.get订单详情
     * 查询单个订单详情（只能获取到成交时间三个月以内的交易信息） 注：虚拟订单充值手机号信息无法通过此接口获取，请联系虚拟类目运营人员。
     * @param orderSn
     *      必填	订单号
     * @param accessToken
     *      accessToken
     * @return
     * @throws PddException
     */
    public static PddOrderInformationGetResponse.OrderInfoGetResponseOrderInfo informationGet(@NotNull String orderSn, @NotNull String accessToken) throws PddException {
        PddOrderInformationGetRequest request = new PddOrderInformationGetRequest();
        request.setOrderSn(orderSn);
        PddOrderInformationGetResponse response = Http.http(request,accessToken);

        return response.getOrderInfoGetResponse().getOrderInfo();
    }

    /**
     * pdd.order.list.get订单列表查询接口（根据成交时间）
     * 根据成团时间查询订单列表（只能获取到成交时间三个月以内的交易信息） 注：虚拟订单充值手机号信息无法通过此接口获取，请联系虚拟类目运营人员。
     * @param orderStatus
     *      必填	发货状态，1：待发货，2：已发货待签收，3：已签收 5：全部
     * @param refundStatus
     *      必填	售后状态 1：无售后或售后关闭，2：售后处理中，3：退款中，4： 退款成功 5：全部
     * @param startConfirmAt
     *      必填	必填，成交时间开始时间的时间戳，指格林威治时间 1970 年 01 月 01 日 00 时 00 分 00 秒(北京时间 1970 年 01 月 01 日 08 时 00 分 00 秒)起至现在的总秒数
     * @param endConfirmAt
     *      必填	必填，成交时间结束时间的时间戳，指格林威治时间 1970 年 01 月 01 日 00 时 00 分 00 秒(北京时间 1970 年 01 月 01 日 08 时 00 分 00 秒)起至现在的总秒数 PS：开始时间结束时间间距不超过 24 小时
     * @param page
     *      必填	返回页码 默认 1，页码从 1 开始 PS：当前采用分页返回，数量和页数会一起传，如果不传，则采用 默认值
     * @param pageSize
     *      必填	返回数量，默认 100。最大 100
     * @param tradeType
     *      非必填	订单类型 0-普通订单 ，1- 定金订单
     * @param useHasNext
     *      非必填	是否启用has_next的分页方式，如果指定true,则返回的结果中不包含总记录数，但是会新增一个是否存在下一页的的字段，通过此种方式获取增量交易，效率在原有的基础上有80%的提升。
     * @param accessToken
     *      accessToken
     * @return
     * @throws PddException
     */
    public static List<PddOrderListGetResponse.OrderListGetResponseOrderListItem> main(@NotNull Integer orderStatus,@NotNull Integer refundStatus,@NotNull Long startConfirmAt,@NotNull Long endConfirmAt,@NotNull Integer page,@NotNull Integer pageSize,Integer tradeType,Boolean useHasNext, @NotNull String accessToken) throws PddException {
        PddOrderListGetRequest request = new PddOrderListGetRequest();
        request.setOrderStatus(orderStatus);
        request.setRefundStatus(refundStatus);
        request.setStartConfirmAt(startConfirmAt);
        request.setEndConfirmAt(endConfirmAt);
        request.setPage(page);
        request.setPageSize(pageSize);
        if(tradeType!=null){
            request.setTradeType(tradeType);
        }
        if(useHasNext){
            request.setUseHasNext(useHasNext);
        }
        PddOrderListGetResponse response = Http.http(request,accessToken);
        return response.getOrderListGetResponse().getOrderList();
    }

    /**
     * pdd.order.note.update编辑商家订单备注
     * 编辑商家订单备注信息
     * @param note
     *      必填	订单备注
     * @param tag
     *      非必填	备注标记：1-红色，2-黄色，3-绿色，4-蓝色，5-紫色
     * @param tagName
     *      非必填	标记名称；长度最大为3个字符
     * @param orderSn
     *      必填	订单号
     * @param accessToken
     *      accessToken
     * @throws Exception
     */
    public static void noteUpdate(@NotNull String note,Integer tag,String tagName,@NotNull String orderSn,@NotNull String accessToken) throws Exception {
        PddOrderNoteUpdateRequest request = new PddOrderNoteUpdateRequest();
        request.setNote(note);
        if(tag!=null){
            request.setTag(tag);
        }
        if(StringUtils.isNotBlank(tagName)){
            request.setTagName(tagName);
        }
        request.setOrderSn(orderSn);
        PddOrderNoteUpdateResponse response = Http.http(request,accessToken);
        System.out.println(JsonUtil.transferToJson(response));
    }
    /**
     * pdd.order.number.list.increment.get订单增量接口
     * 查询订单增量，注：虚拟订单充值手机号信息无法通过此接口获取，请联系虚拟类目运营人员。 拉取卖家已卖出的增量交易数据（只能获取到成交时间三个月以内的交易信息）
     * ①. 一次请求只能查询时间跨度为30分钟的增量交易记录，即end_updated_at - start_updated_at<= 30min。
     * ②. 通过从后往前翻页的方式以及结束时间不小于拼多多系统时间前3min可以避免漏单问题。
     * @param isLuckyFlag
     *      必填	订单类型（是否抽奖订单），0-全部，1-非抽奖订单，2-抽奖订单
     * @param orderStatus
     *      必填	发货状态，1-待发货，2-已发货待签收，3-已签收，5-全部
     * @param startUpdatedAt
     *      必填，最后更新时间开始时间的时间戳，指格林威治时间 1970 年01 月 01 日 00 时 00 分 00 秒(北京时间 1970 年 01 月 01 日 08 时 00 分 00 秒)起至现在的总秒数
     * @param endUpdatedAt
     *      必填，最后更新时间结束时间的时间戳，指格林威治时间 1970 年 01 月 01 日 00 时 00 分 00 秒(北京时间 1970 年 01 月 01 日 08 时 00 分 00 秒)起至现在的总秒数 PS：开始时间结束时间间距不超过 30 分钟
     * @param pageSize
     *      非必填	返回数量，默认 100。最大 100
     * @param page
     *      必填	返回页码，默认 1，页码从 1 开始 PS：当前采用分页返回，数量和页数会一起传，如果不传，则采用 默认值；注：必须采用倒序的分页方式（从最后一页往回取）才能避免漏单问题。
     * @param refundStatus
     *      必填	售后状态，1-无售后或售后关闭，2-售后处理中，3-退款中，4-退款成功 5-全部
     * @param tradeType
     *      非必填	订单类型： 0-普通订单、1-定金订单 不传为全部
     * @param useHasNext
     *      非必填	是否启用has_next的分页方式，如果指定true,则返回的结果中不包含总记录数，但是会新增一个是否存在下一页的的字段，通过此种方式获取增量交易，效率在原有的基础上有80%的提升。
     * @param accessToken
     *      accessToken
     * @throws Exception
     */
    public static void numberListIncrementGet(@NotNull Integer isLuckyFlag,@NotNull Integer orderStatus,@NotNull Long startUpdatedAt,@NotNull Long endUpdatedAt,@NotNull Integer pageSize,@NotNull Integer page,@NotNull Integer refundStatus,Integer tradeType,Boolean useHasNext, @NotNull String accessToken) throws Exception {
        PddOrderNumberListIncrementGetRequest request = new PddOrderNumberListIncrementGetRequest();
        request.setIsLuckyFlag(isLuckyFlag);
        request.setOrderStatus(orderStatus);
        request.setStartUpdatedAt(startUpdatedAt);
        request.setEndUpdatedAt(endUpdatedAt);
        if(pageSize==null){
            pageSize = 100;
        }
        request.setPageSize(pageSize);
        request.setPage(page);
        request.setRefundStatus(refundStatus);
        request.setTradeType(tradeType);
        if(useHasNext!=null){
            request.setUseHasNext(useHasNext);
        }
        PddOrderNumberListIncrementGetResponse response = Http.http(request,accessToken);
        System.out.println(JsonUtil.transferToJson(response));
    }



    /**
     * pdd.order.promise.info.get查询订单承诺信息
     * 查询订单承诺信息，用于打单等场景下的承诺信息展示
     * @return
     */
    public static PddOrderPromiseInfoGetResponse.PromiseInfoGetResponsePromiseInfo promiseInfoGet(@NotNull Long promiseId, @NotNull String accessToken, PopClient popClient) throws PddException {

        PddOrderPromiseInfoGetRequest request = new PddOrderPromiseInfoGetRequest();
        request.setPromiseId(promiseId);

        PddOrderPromiseInfoGetResponse response;
        try {
            response = popClient.syncInvoke(request, accessToken);
        }catch (Exception e){
            throw PddException.toException(e);
        }
        if(response==null){
            throw PddException.toException("接口请求失败");
        }
        if(response.getErrorResponse()!=null){
            throw PddException.toException(response.getErrorResponse());
        }

        System.out.println(JsonUtil.transferToJson(response));
        return response.getPromiseInfoGetResponse().getPromiseInfo();
    }
    /**
     * pdd.order.status.get订单状态
     * 获取订单的状态（单次最多查50个订单）
     * @param orderSns
     *      20150909-452750051,20150909-452750134 用逗号分开
     * @param accessToken
     *      accessToken
     * @param popClient
     *      popClient
     * @return
     */
    public static List<PddOrderStatusGetResponse.OrderStatusGetResponseOrderStatusListItem> statusGet(@NotNull String[] orderSns, @NotNull String accessToken, PopClient popClient) throws PddException {
        PddOrderStatusGetRequest request = new PddOrderStatusGetRequest();
        request.setOrderSns(StringUtils.join(orderSns,","));

        PddOrderStatusGetResponse response;
        try {
            response = popClient.syncInvoke(request, accessToken);
        }catch (Exception e){
            throw PddException.toException(e);
        }
        if(response==null){
            throw PddException.toException("接口请求失败");
        }
        if(response.getErrorResponse()!=null){
            throw PddException.toException(response.getErrorResponse());
        }
        System.out.println(JsonUtil.transferToJson(response));
        return response.getOrderStatusGetResponse().getOrderStatusList();
    }
}
