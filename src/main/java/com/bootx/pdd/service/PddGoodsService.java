package com.bootx.pdd.service;

import com.bootx.common.Pageable;
import com.bootx.entity.Sku;
import com.bootx.entity.StoreUploadConfig;
import com.bootx.pdd.entity.PddCrawlerProduct;
import com.pdd.pop.sdk.http.api.pop.response.*;

import java.util.List;

/**
 * @author black
 */
public interface PddGoodsService {

    /**
     * 删除草稿接口
     * @param accessToken
     *      accessToken
     * @return
     *      PddDeleteDraftCommitResponse
     */
    PddDeleteDraftCommitResponse pddDeleteDraftCommit(String accessToken) throws Exception;

    /**
     * 删除商品接口,只能删除下架的商品
     * @param accessToken
     *      accessToken
     * @return
     *      PddDeleteGoodsCommitResponse
     */
    PddDeleteGoodsCommitResponse pddDeleteGoodsCommit(String accessToken) throws Exception;

    /**
     * 单个商品发布，需要配合pdd.goods.image.upload上传主图及商品详情图片，每个店铺一天可调用1200次，3次/秒。
     * @param product
     *      商品
     * @param accessToken
     *      accessToken
     */
    PddGoodsAddResponse pddGoodsAdd(PddCrawlerProduct pddCrawlerProduct, List<Sku> skus, String accessToken, StoreUploadConfig storeUploadConfig) throws Exception;


    PddGoodsEditGoodsCommitResponse pddGoodsEditGoodsCommit(PddCrawlerProduct pddCrawlerProduct, String accessToken) throws Exception;


    PddGoodsAuthorizationCatsResponse pddGoodsAuthorizationCats(String accessToken) throws Exception;

    /**
     * 通过叶子类目id获取该类目的发布规则，目前返回标品、商品服务、属性等规则。
     * @param accessToken
     * @throws Exception
     */
    PddGoodsCatRuleGetResponse pddGoodsCatRuleGet(String accessToken,Long catId) throws Exception;

    PddGoodsCatTemplateGetResponse pddGoodsCatTemplateGet(String accessToken,Long catId) throws Exception;

    PddGoodsCatsGetResponse pddGoodsCatsGet(String accessToken,Long catId) throws Exception;

    void commitListGet(String accessToken, Integer checkStatus, Long goodsId, Pageable pageable) throws Exception;

    /**
     * 生成商家自定义的规格
     * @param accessToken
     * @return
     * @throws Exception
     */
    PddGoodsSpecIdGetResponse specIdGet(Long parentSpecId, String specName, String accessToken) throws Exception;

    /**
     * 商品属性类目接口
     * @param categoryId
     * @param accessToken
     * @return
     * @throws Exception
     */
    PddGoodsSpecGetResponse specGet(Long categoryId,String accessToken) throws Exception;
}
