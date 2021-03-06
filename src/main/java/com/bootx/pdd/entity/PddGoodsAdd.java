package com.bootx.pdd.entity;

import com.bootx.entity.Sku;
import com.bootx.entity.StoreUploadConfig;
import com.bootx.util.JsonUtils;
import com.pdd.pop.sdk.http.api.pop.request.PddGoodsAddRequest;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author black
 */
public final class PddGoodsAdd {

    private List<String> carouselGallerys = new ArrayList<>();

    private List<String> detailGallerys = new ArrayList<>();


    public List<String> getCarouselGallerys() {
        return carouselGallerys;
    }

    public void setCarouselGallerys(List<String> carouselGallerys) {
        this.carouselGallerys = carouselGallerys;
    }

    public List<String> getDetailGallerys() {
        return detailGallerys;
    }

    public void setDetailGallerys(List<String> detailGallerys) {
        this.detailGallerys = detailGallerys;
    }

    public PddGoodsAddRequest build(PddCrawlerProduct pddCrawlerProduct, List<Sku> skus, StoreUploadConfig storeUploadConfig){
        PddGoodsAddRequest request = new PddGoodsAddRequest();
        // 坏果包赔
        if(storeUploadConfig.getBadFruitClaim()!=null){
            request.setBadFruitClaim(storeUploadConfig.getBadFruitClaim());
        }

        // 限购次数
        if(storeUploadConfig.getBuyLimit()!=null){
            request.setBuyLimit(storeUploadConfig.getBuyLimit());
        }

        // (必填)商品轮播图，按次序上传，图片格式支持JPEG/JPG/PNG， 图片尺寸长宽比1：1且尺寸不低于480px，图片大小最高1MB
        if(storeUploadConfig.getCarouselRandom()){
            List<String> carouselGallerys = getCarouselGallerys();
            request.setCarouselGallery(random(carouselGallerys));
        }else{
            request.setCarouselGallery(getCarouselGallerys());
        }

        //	商品视频
        // List<PddGoodsAddRequest.CarouselVideoItem> carouselVideo = new ArrayList<>();
        // PddGoodsAddRequest.CarouselVideoItem item = new PddGoodsAddRequest.CarouselVideoItem();
        // 商品视频id
        // item.setFileId(0L);
        // 商品视频url
        // item.setVideoUrl("str");
        // carouselVideo.add(item);
        // request.setCarouselVideo(carouselVideo);
        // 轮播视频
        // request.setCarouselVideoUrl("str");
        // (必填)叶子类目ID
        request.setCatId(Long.parseLong(pddCrawlerProduct.getProductCategory().getOtherId().replace("pddPlugin_","")));
        // (必填)物流运费模板ID，可使用pdd.logistics.template.get获取
        if(storeUploadConfig.getDeliveryType()==1&&storeUploadConfig.getCostTemplateId()!=null){
            request.setCostTemplateId(storeUploadConfig.getCostTemplateId());
        }else{
            request.setCostTemplateId(0L);
        }

        // (必填)国家ID，country_id可以通过pdd.goods.country.get获取，仅在goods_type为2、3时（海淘商品）入参生效，其余goods_type传0
        request.setCountryId(0);
        // 团购人数
        if(storeUploadConfig.getCustomerNum()!=null){
            request.setCustomerNum(storeUploadConfig.getCustomerNum());
        }

        // 海关名称，只在goods_type=3（直供商品）时入参且is_customs=true，入参枚举值为：广州、杭州、宁波、郑州、郑州(保税物流中心)、重庆、西安、上海、郑州(综保区)、深圳、福建、天津
//  request.setCustoms("str");
        // 是否当日发货,0 否，1 是
/* if(storeUploadConfig.getShipmentLimitSecond()==0){
    request.setDeliveryOneDay(1);
}else{
    request.setDeliveryOneDay(0);
}*/


        /**
         * (必填)商品详情图：
         *  a. 尺寸要求宽度处于480~1200px之间，高度0-1500px之间
         *  b. 大小1M以内
         *  c. 数量限制在20张之间
         *  d. 图片格式仅支持JPG,PNG格式
         *  e. 点击上传时，支持批量上传详情图
         */

        request.setDetailGallery(getDetailGallerys());

        /*PddGoodsAddRequest.ElecGoodsAttributes elecGoodsAttributes = new PddGoodsAddRequest.ElecGoodsAttributes();
        // 开始时间（timeType=1时必填表示核销的开始时间）（精确到毫秒）
        elecGoodsAttributes.setBeginTime(0L);
        // 天数内有效（timeType=3必填，表示发货后几天内核销）
        elecGoodsAttributes.setDaysTime(0);
        // 截止时间（timeType=1,2时必填，表示发货后核销的截止时间）（精确到毫秒）
        elecGoodsAttributes.setEndTime(0L);
        // 卡券核销类型（1：起始时间内有效，2：发货后后至截止时间内有效，3：发货后多少天内有效）
        elecGoodsAttributes.setTimeType(0);
        request.setElecGoodsAttributes(elecGoodsAttributes);*/



        // 商品描述， 字数限制：20-500，例如，新包装，保证产品的口感和新鲜度。单颗独立小包装，双重营养，1斤家庭分享装，更实惠新疆一级骏枣夹核桃仁。
        request.setGoodsDesc(pddCrawlerProduct.getCrawlerProductIntroduction().getContent()==null?"":pddCrawlerProduct.getCrawlerProductIntroduction().getContent());
        // 商品标题，例如，新疆特产 红满疆枣夹核桃500g
        request.setGoodsName(dealName(pddCrawlerProduct.getName(),storeUploadConfig));
        /*List<PddGoodsAddRequest.GoodsPropertiesItem> goodsProperties = new ArrayList<>();
        // 商品属性列表
        PddGoodsAddRequest.GoodsPropertiesItem item1 = new PddGoodsAddRequest.GoodsPropertiesItem();
        // 组id，非销售属性不用传
        item1.setGroupId(0);
        // 图片url，非销售属性不用传
        item1.setImgUrl("str");
        // 备注，非销售属性不用传
        item1.setNote("str");
        // 父属性id，非销售属性不用传
        item1.setParentSpecId(0L);
        // 引用属性id
        item1.setRefPid(0L);
        // 属性id，非销售属性不用传
        item1.setSpecId(0L);
        // 模板属性id
        item1.setTemplatePid(0L);
        // 属性值
        item1.setValue("str");
        // 属性单位
        item1.setValueUnit("str");
        // 属性值id
        item1.setVid(0L);
        goodsProperties.add(item1);
        request.setGoodsProperties(goodsProperties);*/

        /*PddGoodsAddRequest.GoodsTradeAttr goodsTradeAttr = new PddGoodsAddRequest.GoodsTradeAttr();
        goodsTradeAttr.setAdvancesDays(0);
        PddGoodsAddRequest.GoodsTradeAttrBookingNotes bookingNotes = new PddGoodsAddRequest.GoodsTradeAttrBookingNotes();
        bookingNotes.setUrl("str");
        goodsTradeAttr.setBookingNotes(bookingNotes);
        goodsTradeAttr.setLifeSpan(0);
        request.setGoodsTradeAttr(goodsTradeAttr);*/

        // 商品出行信息
        /*PddGoodsAddRequest.GoodsTravelAttr goodsTravelAttr = new PddGoodsAddRequest.GoodsTravelAttr();
        goodsTravelAttr.setNeedTourist(false);
        goodsTravelAttr.setType(0);
        request.setGoodsTravelAttr(goodsTravelAttr);*/


        //1-国内普通商品，2-进口，3-直供（保税），4-直邮 ,5-流量 ,6-话费 ,7-优惠券 ,8-QQ充值 ,9-加油卡，15-商家卡券，19-平台卡券
        request.setGoodsType(1);
        //商品主图，请参考拼多多首页大图，如果商品参加部分活动则必填，否则无法参加活动
        // a. 尺寸750 x 352px
        // b. 大小100k以内
        // c. 图片格式仅支持JPG,PNG格式
        // d. 图片背景应以纯白为主, 商品图案居中显示
        // e. 图片不可以添加任何品牌相关文字或logo
        if(storeUploadConfig.getCarouselIndex()>pddCrawlerProduct.getCrawlerProductImage().getImages().size()){
            storeUploadConfig.setCarouselIndex(0);
        }
        request.setImageUrl(carouselGallerys.get(storeUploadConfig.getCarouselIndex()));
        // 是否支持开票（测试中）
        // request.setInvoiceStatus(false);
        // 是否需要上报海关，false-无需上报海关，true-需上报海关
//request.setIsCustoms(false);
        // 是否支持假一赔十，false-不支持，true-支持
        request.setIsFolt(storeUploadConfig.getIsFolt());
        // 是否预售,true-预售商品，false-非预售商品
        request.setIsPreSale(storeUploadConfig.getIsPreSale());
        // 是否7天无理由退换货，true-支持，false-不支持
        request.setIsRefundable(storeUploadConfig.getIsRefundable());
        // 缺重包退
        if(storeUploadConfig.getLackOfWeightClaim()!=null){
            request.setLackOfWeightClaim(storeUploadConfig.getLackOfWeightClaim());
        }
        // 买家自提模版id
        // request.setMaiJiaZiTi("str");
        // 市场价格，单位为分
        request.setMarketPrice(markerPrice(pddCrawlerProduct.getPrice(),storeUploadConfig));
        // 单次限量
        // request.setOrderLimit(999999);
        // 原产地id，是指海淘商品的生产地址，仅在goods type=3/4的时候必填，可以通过pdd.goods.country.get获取
        // request.setOriginCountryId(0);
        // 商品goods外部编码，同其他接口中的outer_goods_id 、out_goods_id、out_goods_sn、outer_goods_sn 都为商家编码（goods维度）。
        //request.setOutGoodsId("str");
        // 第三方商品Id
        //request.setOutSourceGoodsId("str");
        // 第三方商品来源
        //request.setOutSourceType(0);

        // { "consumption_tax_rate": 1, "value_added_tax_rate": 9, "hs_code": "2200", "customs_broker": "sss", "bonded_warehouse_key": "pp" }
        /*PddGoodsAddRequest.OverseaGoods overseaGoods = new PddGoodsAddRequest.OverseaGoods();
        // 保税仓唯一标识
        overseaGoods.setBondedWarehouseKey("str");
        // 消费税率
        overseaGoods.setConsumptionTaxRate(0);
        // 清关服务商
        overseaGoods.setCustomsBroker("str");
        // 	海关编号
        overseaGoods.setHsCode("str");
        // 增值税率
        overseaGoods.setValueAddedTaxRate(0);
        request.setOverseaGoods(overseaGoods);


        // oversea_type
        request.setOverseaType(0);
        //预售时间，is_pre_sale为true时必传，UNIX时间戳，只能为某一天的23:59:59
        request.setPreSaleTime(0L);*/
        // 0：不支持全国联保；1：支持全国联保
//request.setQuanGuoLianBao(0);
        // 是否二手商品，true -二手商品 ，false-全新商品
        request.setSecondHand(storeUploadConfig.getSecondHand());
        // 	上门安装模版id
        // request.setShangMenAnZhuang("str");
        // 承诺发货时间（ 秒），普通、进口商品可选48小时或24小时；直邮、直供商品只能入参120小时；is_pre_sale为true时不必传
        if(storeUploadConfig.getShipmentLimitSecond()!=null){
            request.setShipmentLimitSecond(storeUploadConfig.getShipmentLimitSecond());
        }else{
            request.setShipmentLimitSecond(86400L);
        }

        // request.setSizeSpecId(0L);

        List<PddGoodsAddRequest.SkuListItem> skuList = new ArrayList<>();
        skus.stream().filter(sku -> !sku.getIsError()).forEach(sku->{
            // sku对象列表,实例：[{ "is_onsale": 1, "limit_quantity": 999, "price": "2200", "weight": 1000, "multi_price": "1900", "thumb_url": "http://t06img.yangkeduo.com/images/2018-04-15/ced035033b5d40b589140af882621c03.jpg", "out_sku_sn": "L", "quantity": 100, "spec_id_list": "[25]", "oversea_sku": { "measurement_code": "计量单位编码", "taxation": "税费", "specifications": "规格" } }]
            PddGoodsAddRequest.SkuListItem item2 = new PddGoodsAddRequest.SkuListItem();
            // sku上架状态，0-已下架，1-上架中
            item2.setIsOnsale(1);
            // sku送装参数：长度
            // item2.setLength(0L);
            // 	sku购买限制，只入参999
            item2.setLimitQuantity(999L);
            // 	商品团购价格
            item2.setMultiPrice(multiPrice(sku.getPrice().subtract(new BigDecimal(2.5)),storeUploadConfig));
            // 	商品sku外部编码，同其他接口中的outer_id 、out_id、out_sku_sn、outer_sku_sn、out_sku_id、outer_sku_id 都为商家编码（sku维度）。
            item2.setOutSkuSn(skuSn(sku.getSn(),storeUploadConfig));
            // 第三方sku Id
            item2.setOutSourceSkuId(skuSn(sku.getSn(),storeUploadConfig));

            // 商品单买价格
            item2.setPrice(price(sku.getPrice().subtract(new BigDecimal(1)),storeUploadConfig));
            // 商品sku库存初始数量，后续库存update只使用stocks.update接口进行调用
            item2.setQuantity(stock(sku.getStock(),storeUploadConfig));
            // 商品规格列表，根据pdd.goods.spec.id.get生成的规格属性id，例如：颜色规格下商家新增白色和黑色，大小规格下商家新增L和XL，则由4种spec组合，入参一种组合即可，在skulist中需要有4个spec组合的sku，示例：[20,5]
            List<Long> collect = sku.getSpecificationValues().stream().map(specificationValue -> specificationValue.getPddId()).collect(Collectors.toList());
            item2.setSpecIdList(JsonUtils.toJson(collect));
            // 	sku 缩略图
            item2.setThumbUrl(skuImg(getCarouselGallerys(),storeUploadConfig));
            // 	重量，单位为g
            item2.setWeight(0L);
            skuList.add(item2);
        });

        request.setSkuList(skuList);
        // 库存方式（0：普通型，1：日历型）
// request.setSkuType(0);
        // 送货入户并安装模版id
       //  request.setSongHuoAnZhuang("str");
        // 送货入户模版id
        //  request.setSongHuoRuHu("str");
        // 短标题，示例：新包装，保证产品的口感和新鲜度。单颗独立小包装，双重营养，1斤家庭分享装，更实惠新疆一级骏枣夹核桃仁。
        //   request.setTinyName("str");
        // 保税仓，只在goods_type=3（直供商品）时入参，入参枚举值为：宁波保税仓、杭州保税仓、广州保税仓、深圳保税仓、重庆保税仓、郑州保税仓、福建保税仓、天津保税仓、上海保税仓、银川保税仓、成都保税仓
        //   request.setWarehouse("str");
        // 水果类目温馨提示，只在水果类目商品才生效， 字数限制：商品描述goods_desc+温馨提示总计不超过500字。
        //  request.setWarmTips("str");
        // 只换不修的天数，目前只支持0和365
        //   request.setZhiHuanBuXiu(0);
        // 发货方式。0：无物流发货；1：有物流发货。
// request.setDeliveryType(storeUploadConfig.getDeliveryType());
        return request;
    }


    /**
     * 用来打乱顺序的
     * @param list
     * @return
     */
    private List<String> random(List<String> list) {
        Collections.shuffle(list);
        Collections.shuffle(list);
        return list;
    }

    /**
     * 处理商品标题
     * @param name
     * @param storeUploadConfig
     * @return
     */
    private String dealName(String name, StoreUploadConfig storeUploadConfig) {
        if(storeUploadConfig.getRandomTitle()){
            // 打乱标题
        }
        // 标题截取
        if(storeUploadConfig.getTitleMaxLength()<name.length()){
            if(storeUploadConfig.getTitleDealType()==0){
                // 去掉最前面
                name = name.substring(name.length()-60);
            }else if(storeUploadConfig.getTitleDealType()==1){
                // 去掉最后
                name = name.substring(0,60);
            }
        }
        // 加前缀
        if(storeUploadConfig.getAddBefore()){
            name = storeUploadConfig.getAddABeforeWord()+name;
        }
        // 加前缀
        if(storeUploadConfig.getAddBefore()){
            name = name+storeUploadConfig.getAddAfterWord();
        }
        // 关键词替换
        if(storeUploadConfig.getReplace()){
            name = name.replace(storeUploadConfig.getOldWord(),storeUploadConfig.getNewWord());
        }
        // 关键词删除
        if(storeUploadConfig.getDelete()){
            name = name.replace(storeUploadConfig.getDeleteWord(),"");
        }
        return name;

    }

    /**
     * 设置市场价
     * @param price
     * @param storeUploadConfig
     * @return
     */
    private Long markerPrice(String price, StoreUploadConfig storeUploadConfig) {
        BigDecimal price1 = new BigDecimal(price);
        Integer markerPriceType = storeUploadConfig.getMarkerPriceType();
        BigDecimal markerPriceRate = storeUploadConfig.getMarkerPriceRate();
        return price(price1,markerPriceType,markerPriceRate);
    }

    /**
     * 团购加
     * @param price
     * @param storeUploadConfig
     * @return
     */
    private Long multiPrice(BigDecimal price, StoreUploadConfig storeUploadConfig) {
        Integer groupPriceType = storeUploadConfig.getGroupPriceType();
        BigDecimal groupPriceRate = storeUploadConfig.getGroupPriceRate();
        return price(price,groupPriceType,groupPriceRate);
    }

    /**
     * 设置单买价格
     * @param price
     * @param storeUploadConfig
     * @return
     */
    private Long price(BigDecimal price, StoreUploadConfig storeUploadConfig) {
        Integer singlePriceType = storeUploadConfig.getSinglePriceType();
        BigDecimal singlePriceRate = storeUploadConfig.getSinglePriceRate();
        return price(price,singlePriceType,singlePriceRate);
    }

    private Long price(BigDecimal price,Integer type,BigDecimal rate){
        // 1：加 2：减 3：乘 4：除
        if(type==1){
            return price.add(rate).multiply(new BigDecimal(100)).longValue();
        }else if(type==2){
            return price.subtract(rate).multiply(new BigDecimal(100)).longValue();
        }else if(type==3){
            return price.multiply(rate).multiply(new BigDecimal(100)).longValue();
        }else if(type==4){
            return price.divide(rate, RoundingMode.HALF_DOWN).multiply(new BigDecimal(100)).longValue();
        }
        return price.multiply(rate).multiply(new BigDecimal(100)).longValue();
    }

    /**
     * 商品编号
     * @param sn
     * @param storeUploadConfig
     * @return
     */
    private String skuSn(String sn, StoreUploadConfig storeUploadConfig) {
        if(StringUtils.isBlank(sn)){
            sn = System.currentTimeMillis()+"";
        }
        if(storeUploadConfig.getSkuSnType()==0){

        }else if(storeUploadConfig.getSkuSnType()==1){
            sn = System.currentTimeMillis()+"";
        }
        return storeUploadConfig.getSkuPrefix()+sn+storeUploadConfig.getSkuSuffix();
    }
    /**
     * 设置库存
     */
    private Long stock(Long stock, StoreUploadConfig storeUploadConfig) {
        if(stock==null){
            stock = 100L;
        }
        if(storeUploadConfig.getStockConfig()==0){

        }else{
            stock = storeUploadConfig.getStockBase();
        }

        if(stock<storeUploadConfig.getLackStockBase1()){
            stock = storeUploadConfig.getLackStockBase2();
        }
        return stock;
    }

    /**
     * sku 缩略图的处理
     * @param images
     * @param storeUploadConfig
     * @return
     */
    private String skuImg(List<String> images, StoreUploadConfig storeUploadConfig) {
        Integer skuPic = storeUploadConfig.getSkuPic();
        if(skuPic>images.size()){
            skuPic=0;
        }
        return images.get(skuPic);

    }
}
