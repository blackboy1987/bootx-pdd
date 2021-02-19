package com.bootx.util.pdd;

import com.pdd.pop.sdk.common.util.JsonUtil;
import com.pdd.pop.sdk.http.PopClient;
import com.pdd.pop.sdk.http.api.pop.request.*;
import com.pdd.pop.sdk.http.api.pop.response.*;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

/**
 * 商品API
 */
public final class ShangPin {

    /**
     * pdd.delete.draft.commit删除草稿接口
     * 删除草稿接口
     * @param goodsCommitId
     *      必填	草稿id
     * @param accessToken
     * @throws Exception
     */
    public static void main(@NotNull Long goodsCommitId,@NotNull String accessToken) throws PddException {
        PddDeleteDraftCommitRequest request = new PddDeleteDraftCommitRequest();
        request.setGoodsCommitId(goodsCommitId);
        PddDeleteDraftCommitResponse response = Http.http(request,accessToken);
        System.out.println(JsonUtil.transferToJson(response));
    }

    /**
     * pdd.delete.goods.commit删除商品接口
     * 删除商品接口,只能删除下架的商品
     * @param goodsIds
     *      LONG[]	必填	商品id 列表(List<Long>) json string，例：[1,2]，一次操作数量请小于50
     * @param accessToken
     *      accessToken
     * @throws PddException
     */
    public static Boolean deleteGoodsCommit(@NotNull Long[] goodsIds,@NotNull String accessToken) throws PddException {
        PddDeleteGoodsCommitRequest request = new PddDeleteGoodsCommitRequest();
        request.setGoodsIds(Arrays.asList(goodsIds));
        PddDeleteGoodsCommitResponse response = Http.http(request,accessToken);
        return response.getOpenApiResponse();
    }

    /**
     * pdd.goods.add商品新增接口
     * 单个商品发布，需要配合pdd.goods.image.upload上传主图及商品详情图片，每个店铺一天可调用1200次，3次/秒
     * @param request
     *      request
     * @param accessToken
     *      accessToken
     * @return
     * @throws Exception
     */
    public static PddGoodsAddResponse.GoodsAddResponse goodsAdd(@NotNull PddGoodsAddRequest request, @NotEmpty String accessToken) throws Exception {
        PddGoodsAddResponse response =Http.http(request,accessToken);
        return response.getGoodsAddResponse();
    }

    /**
     *  pdd.goods.authorization.cats获取当前授权商家可发布的商品类目信息
     *  获取当前授权商家可发布的商品类目信息
     * @param parentCatId
     *      LONG	非必填	默认值=0，值=0时为顶点cat_id,通过树顶级节点获取一级类目
     * @param accessToken
     *      accessToken
     * @throws Exception
     */
    public static void goodsAuthorizationCats(Long parentCatId,@NotNull String accessToken) throws PddException {
        PddGoodsAuthorizationCatsRequest request = new PddGoodsAuthorizationCatsRequest();
        request.setParentCatId(parentCatId);
        PddGoodsAuthorizationCatsResponse response = Http.http(request,accessToken);
        System.out.println(JsonUtil.transferToJson(response));
    }

    /**
     * pdd.goods.cat.rule.get类目商品发布规则查询接口
     * 通过叶子类目id获取该类目的发布规则，目前返回标品、商品服务、属性等规则。
     * @param catId
     *      LONG	必填	类目id
     * @param accessToken
     *      accessToken
     * @throws Exception
     */
    public static void goodsCatRuleGet(@NotNull Long catId,@NotNull String accessToken) throws PddException {
        PddGoodsCatRuleGetRequest request = new PddGoodsCatRuleGetRequest();
        request.setCatId(catId);
        PddGoodsCatRuleGetResponse response = Http.http(request,accessToken);
        System.out.println(JsonUtil.transferToJson(response));
    }

    /**
     * pdd.goods.cat.template.get获取商品类目属性
     * 商品发布前，需要查询该类目的商品发布需要的属性，获取商品发布需要的模板-属性-属性值
     * @param catId
     *      LONG	必填	类目id
     * @param accessToken
     *      accessToken
     * @throws Exception
     */
    public static void goodsCatTemplateGet(@NotNull Long catId,@NotNull String accessToken) throws PddException {
        PddGoodsCatTemplateGetRequest request = new PddGoodsCatTemplateGetRequest();
        request.setCatId(catId);
        PddGoodsCatTemplateGetResponse response = Http.http(request,accessToken);
        System.out.println(JsonUtil.transferToJson(response));
    }

    /**
     * pdd.goods.cats.get商品标准类目接口
     * 获取拼多多标准商品类目信息（请使用pdd.goods.authorization.cats接口获取商家可发布类目）
     * @param parentCatId
     *      LONG	必填	值=0时为顶点cat_id,通过树顶级节点获取cat树
     * @throws Exception
     */
    public static void goodsCatsGet(@NotNull Long parentCatId) throws PddException {
        PddGoodsCatsGetRequest request = new PddGoodsCatsGetRequest();
        request.setParentCatId(parentCatId);
        PddGoodsCatsGetResponse response = Http.http(request,null);
        System.out.println(JsonUtil.transferToJson(response));
    }

    /**
     * pdd.goods.list.get商品列表接口
     * @param accessToken
     *      accessToken
     * @param outerId
     *      商品外部编码（sku），同其他接口中的outer_id 、out_id、out_sku_sn、outer_sku_sn、out_sku_id、outer_sku_id 都为商家编码（sku维度）。outer_id,is_onsale,goods_name三选一，优先级is_onsale>outer_id>goods_name
     * @param isOnsale
     *      上下架状态，0-下架，1-上架,outer_id,is_onsale,goods_name三选一，优先级is_onsale>outer_id>goods_name
     * @param goodsName
     *      商品名称模糊查询,outer_id,is_onsale,goods_name三选一，优先级is_onsale>outer_id>goods_name
     * @param page
     *      	返回页码 默认 1，页码从 1 开始PS：当前采用分页返回，数量和页数会一起传，如果不传，则采用 默认值
     * @param pageSize
     *      返回数量，默认 100，最大100。
     * @param outerGoodsId
     *      商家外部商品编码，支持多个，用逗号隔开，最多10个
     * @param costTemplateId
     *      模版id
     * @param popClient
     *      popClient
     * @return
     * @throws PddException
     */
    public static PddGoodsListGetResponse.GoodsListGetResponse listGet(String accessToken, String outerId, Integer isOnsale, String  goodsName, Integer page, Integer pageSize, String  outerGoodsId, Long costTemplateId, PopClient popClient) throws PddException {

        PddGoodsListGetRequest request = new PddGoodsListGetRequest();
        if(StringUtils.isNotBlank(outerId)){
            request.setOuterId(outerId);
        }
        if(isOnsale!=null){
            request.setIsOnsale(isOnsale);
        }
        if(StringUtils.isNotBlank(goodsName)){
            request.setGoodsName(goodsName);
        }
        if(pageSize==null||pageSize<=0||pageSize>100){
            pageSize = 100;
        }
        request.setPageSize(pageSize);

        if(page==null||page<=0){
            page = 1;
        }
        request.setPage(page);

        if(StringUtils.isNotBlank(outerGoodsId)){
            request.setOuterGoodsId(outerGoodsId);
        }

        if(costTemplateId!=null){
            request.setCostTemplateId(costTemplateId);
        }

        PddGoodsListGetResponse response;
        try {
            response = popClient.syncInvoke(request, accessToken);;
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

        return response.getGoodsListGetResponse();
    }

    /**
     * pdd.goods.image.upload商品图片上传接口
     * @param imageUrl
     *      图片网络地址
     * @param accessToken
     *      accessToken
     * @param popClient
     *      popClient
     * @return
     * @throws PddException
     */
    public static String imageUpload(String imageUrl,String accessToken,PopClient popClient) throws PddException {
        if(StringUtils.isBlank(imageUrl)){
            throw PddException.toException("imageUrl必传");
        }

        PddGoodsImageUploadRequest request = new PddGoodsImageUploadRequest();
        request.setImage(url2Base64(imageUrl));

        PddGoodsImageUploadResponse response;
        try {
            response = popClient.syncInvoke(request, accessToken);;
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
        return response.getGoodsImageUploadResponse().getImageUrl();
    }

    /**
     * pdd.goods.authorization.cats获取当前授权商家可发布的商品类目信息
     * @param parentCatId
     *      默认值=0，值=0时为顶点cat_id,通过树顶级节点获取一级类目
     * @param accessToken
     *      accessToken
     * @param popClient
     *      popClient
     * @return
     * @throws PddException
     */
    public static List<PddGoodsAuthorizationCatsResponse.GoodsAuthCatsGetResponseGoodsCatsListItem> authorizationCats(Long parentCatId, String accessToken, PopClient popClient) throws PddException {
        PddGoodsAuthorizationCatsRequest request = new PddGoodsAuthorizationCatsRequest();
        if(parentCatId==null){
            parentCatId = 0L;
        }
        request.setParentCatId(parentCatId);

        PddGoodsAuthorizationCatsResponse response;
        try {
            response = popClient.syncInvoke(request, accessToken);;
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
        return response.getGoodsAuthCatsGetResponse().getGoodsCatsList();
    }

    /**
     * pdd.goods.spec.get商品属性类目接口
     *
     * @param catId
     *      叶子类目ID，必须入参level=3时的cat_id,否则无法返回正确的参数
     * @param accessToken
     *      accessToken
     * @param popClient
     *      popClient
     * @throws Exception
     * @return
     */
    public static List<PddGoodsSpecGetResponse.GoodsSpecGetResponseGoodsSpecListItem> specGet(Long catId, String accessToken, PopClient popClient) throws PddException {
        PddGoodsSpecGetRequest request = new PddGoodsSpecGetRequest();
        request.setCatId(catId);
        PddGoodsSpecGetResponse response;
        try {
            response = popClient.syncInvoke(request, accessToken);;
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
        return response.getGoodsSpecGetResponse().getGoodsSpecList();
    }





























    private static String url2Base64(String imgUrl) {
        try{
            ByteArrayOutputStream data = new ByteArrayOutputStream();
            InputStream is = null;
            try {
                if(!StringUtils.startsWith(imgUrl,"http")){
                    imgUrl = "http:"+imgUrl;
                }
                URL url = new URL(imgUrl);
                byte[] by = new byte[1024];
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(5000);
                is = conn.getInputStream();
                int len = -1;
                while ((len = is.read(by)) != -1) {
                    data.write(by, 0, len);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    if(is!=null){
                        is.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return Base64.encodeBase64String(data.toByteArray());
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
}
