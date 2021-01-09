package com.bootx.pdd.entity;

import com.bootx.entity.Product;
import com.pdd.pop.sdk.http.api.pop.request.PddGoodsAddRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author black
 */
public final class PddGoodsAdd {

    public static PddGoodsAddRequest build(Product product){
        PddGoodsAddRequest request = new PddGoodsAddRequest();
        // 坏果包赔
        request.setBadFruitClaim(0);
        // 限购次数
        request.setBuyLimit(0L);
        // (必填)商品轮播图，按次序上传，图片格式支持JPEG/JPG/PNG， 图片尺寸长宽比1：1且尺寸不低于480px，图片大小最高1MB
        List<String> carouselGallery = product.getProductImages().stream().map(item->item.getSource()).collect(Collectors.toList());;
        request.setCarouselGallery(carouselGallery);
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
        request.setCatId(0L);
        // (必填)物流运费模板ID，可使用pdd.logistics.template.get获取
        request.setCostTemplateId(0L);
        // (必填)国家ID，country_id可以通过pdd.goods.country.get获取，仅在goods_type为2、3时（海淘商品）入参生效，其余goods_type传0
        request.setCountryId(0);
        // 团购人数
        request.setCustomerNum(0L);
        // 海关名称，只在goods_type=3（直供商品）时入参且is_customs=true，入参枚举值为：广州、杭州、宁波、郑州、郑州(保税物流中心)、重庆、西安、上海、郑州(综保区)、深圳、福建、天津
        request.setCustoms("str");
        // 是否当日发货,0 否，1 是
        request.setDeliveryOneDay(0);

        /**
         * (必填)商品详情图：
         *  a. 尺寸要求宽度处于480~1200px之间，高度0-1500px之间
         *  b. 大小1M以内
         *  c. 数量限制在20张之间
         *  d. 图片格式仅支持JPG,PNG格式
         *  e. 点击上传时，支持批量上传详情图
         */
        List<String> detailGallery = new ArrayList<String>();
        // 卡券类商品属性
        detailGallery.add("str");
        request.setDetailGallery(detailGallery);

        PddGoodsAddRequest.ElecGoodsAttributes elecGoodsAttributes = new PddGoodsAddRequest.ElecGoodsAttributes();
        // 开始时间（timeType=1时必填表示核销的开始时间）（精确到毫秒）
        elecGoodsAttributes.setBeginTime(0L);
        // 天数内有效（timeType=3必填，表示发货后几天内核销）
        elecGoodsAttributes.setDaysTime(0);
        // 截止时间（timeType=1,2时必填，表示发货后核销的截止时间）（精确到毫秒）
        elecGoodsAttributes.setEndTime(0L);
        // 卡券核销类型（1：起始时间内有效，2：发货后后至截止时间内有效，3：发货后多少天内有效）
        elecGoodsAttributes.setTimeType(0);
        request.setElecGoodsAttributes(elecGoodsAttributes);



        // 商品描述， 字数限制：20-500，例如，新包装，保证产品的口感和新鲜度。单颗独立小包装，双重营养，1斤家庭分享装，更实惠新疆一级骏枣夹核桃仁。
        request.setGoodsDesc("str");
        // 商品标题，例如，新疆特产 红满疆枣夹核桃500g
        request.setGoodsName("str");
        List<PddGoodsAddRequest.GoodsPropertiesItem> goodsProperties = new ArrayList<>();

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
        request.setGoodsProperties(goodsProperties);

        // 日历商品交易相关信息
        PddGoodsAddRequest.GoodsTradeAttr goodsTradeAttr = new PddGoodsAddRequest.GoodsTradeAttr();
        // 	提前预定天数，默认为0表示当天可预定
        goodsTradeAttr.setAdvancesDays(0);
        // 预订须知
        PddGoodsAddRequest.GoodsTradeAttrBookingNotes bookingNotes = new PddGoodsAddRequest.GoodsTradeAttrBookingNotes();
        // 预定须知图片地址
        bookingNotes.setUrl("str");
        goodsTradeAttr.setBookingNotes(bookingNotes);
        // 卡券有效期，日历日期后多少天可用。默认值为0表示仅限日历日当天使用
        goodsTradeAttr.setLifeSpan(0);
        request.setGoodsTradeAttr(goodsTradeAttr);

        // 商品出行信息
        PddGoodsAddRequest.GoodsTravelAttr goodsTravelAttr = new PddGoodsAddRequest.GoodsTravelAttr();
        // 出行人是否必填（默认是）
        goodsTravelAttr.setNeedTourist(false);
        // 日历商品类型1:旅行类,2:住宿类,3:票务类
        goodsTravelAttr.setType(0);
        request.setGoodsTravelAttr(goodsTravelAttr);


        //1-国内普通商品，2-进口，3-直供（保税），4-直邮 ,5-流量 ,6-话费 ,7-优惠券 ,8-QQ充值 ,9-加油卡，15-商家卡券，19-平台卡券
        request.setGoodsType(0);
        //商品主图，请参考拼多多首页大图，如果商品参加部分活动则必填，否则无法参加活动
        // a. 尺寸750 x 352px
        // b. 大小100k以内
        // c. 图片格式仅支持JPG,PNG格式
        // d. 图片背景应以纯白为主, 商品图案居中显示
        // e. 图片不可以添加任何品牌相关文字或logo
        request.setImageUrl("str");
        // 是否支持开票（测试中）
        request.setInvoiceStatus(false);
        // 是否需要上报海关，false-无需上报海关，true-需上报海关
        request.setIsCustoms(false);
        // 是否支持假一赔十，false-不支持，true-支持
        request.setIsFolt(false);
        // 是否预售,true-预售商品，false-非预售商品
        request.setIsPreSale(false);
        // 是否7天无理由退换货，true-支持，false-不支持
        request.setIsRefundable(false);
        // 缺重包退
        request.setLackOfWeightClaim(0);
        // 买家自提模版id
        request.setMaiJiaZiTi("str");
        // 市场价格，单位为分
        request.setMarketPrice(0L);
        // 单次限量
        request.setOrderLimit(0);
        // 原产地id，是指海淘商品的生产地址，仅在goods type=3/4的时候必填，可以通过pdd.goods.country.get获取
        request.setOriginCountryId(0);
        // 商品goods外部编码，同其他接口中的outer_goods_id 、out_goods_id、out_goods_sn、outer_goods_sn 都为商家编码（goods维度）。
        request.setOutGoodsId("str");
        // 第三方商品Id
        request.setOutSourceGoodsId("str");
        // 第三方商品来源
        request.setOutSourceType(0);

        // { "consumption_tax_rate": 1, "value_added_tax_rate": 9, "hs_code": "2200", "customs_broker": "sss", "bonded_warehouse_key": "pp" }
        PddGoodsAddRequest.OverseaGoods overseaGoods = new PddGoodsAddRequest.OverseaGoods();
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
        request.setPreSaleTime(0L);
        // 0：不支持全国联保；1：支持全国联保
        request.setQuanGuoLianBao(0);
        // 是否二手商品，true -二手商品 ，false-全新商品
        request.setSecondHand(false);
        // 	上门安装模版id
        request.setShangMenAnZhuang("str");
        // 承诺发货时间（ 秒），普通、进口商品可选48小时或24小时；直邮、直供商品只能入参120小时；is_pre_sale为true时不必传
        request.setShipmentLimitSecond(0L);
        request.setSizeSpecId(0L);

        List<PddGoodsAddRequest.SkuListItem> skuList = new ArrayList<>();
        product.getProductSku().getSkus().stream().forEach(sku->{
            // sku对象列表,实例：[{ "is_onsale": 1, "limit_quantity": 999, "price": "2200", "weight": 1000, "multi_price": "1900", "thumb_url": "http://t06img.yangkeduo.com/images/2018-04-15/ced035033b5d40b589140af882621c03.jpg", "out_sku_sn": "L", "quantity": 100, "spec_id_list": "[25]", "oversea_sku": { "measurement_code": "计量单位编码", "taxation": "税费", "specifications": "规格" } }]
            PddGoodsAddRequest.SkuListItem item2 = new PddGoodsAddRequest.SkuListItem();
            // sku上架状态，0-已下架，1-上架中
            item2.setIsOnsale(0);
            // sku送装参数：长度
            item2.setLength(0L);
            // 	sku购买限制，只入参999
            item2.setLimitQuantity(0L);
            // 	商品团购价格
            item2.setMultiPrice(0L);
            // 	商品sku外部编码，同其他接口中的outer_id 、out_id、out_sku_sn、outer_sku_sn、out_sku_id、outer_sku_id 都为商家编码（sku维度）。
            item2.setOutSkuSn(sku.getSn());
            // 第三方sku Id
            item2.setOutSourceSkuId(sku.getSn());

            // oversea_sku
            PddGoodsAddRequest.SkuListItemOverseaSku overseaSku = new PddGoodsAddRequest.SkuListItemOverseaSku();
            // 计量单位编码，从接口pdd.gooods.sku.measurement.list获取code
            overseaSku.setMeasurementCode("str");
            // 规格
            overseaSku.setSpecifications("str");
            // 税费
            overseaSku.setTaxation(0);
            item2.setOverseaSku(overseaSku);
            // 商品单买价格
            item2.setPrice(0L);
            // 商品sku库存初始数量，后续库存update只使用stocks.update接口进行调用
            item2.setQuantity(0L);
            // 商品规格列表，根据pdd.goods.spec.id.get生成的规格属性id，例如：颜色规格下商家新增白色和黑色，大小规格下商家新增L和XL，则由4种spec组合，入参一种组合即可，在skulist中需要有4个spec组合的sku，示例：[20,5]
            item2.setSpecIdList("str");
            // 	sku 缩略图
            item2.setThumbUrl("str");
            // 	重量，单位为g
            item2.setWeight(0L);
            skuList.add(item2);
        });

        request.setSkuList(skuList);
        // 库存方式（0：普通型，1：日历型）
        request.setSkuType(0);
        // 送货入户并安装模版id
        request.setSongHuoAnZhuang("str");
        // 送货入户模版id
        request.setSongHuoRuHu("str");
        // 短标题，示例：新包装，保证产品的口感和新鲜度。单颗独立小包装，双重营养，1斤家庭分享装，更实惠新疆一级骏枣夹核桃仁。
        request.setTinyName("str");
        // 保税仓，只在goods_type=3（直供商品）时入参，入参枚举值为：宁波保税仓、杭州保税仓、广州保税仓、深圳保税仓、重庆保税仓、郑州保税仓、福建保税仓、天津保税仓、上海保税仓、银川保税仓、成都保税仓
        request.setWarehouse("str");
        // 水果类目温馨提示，只在水果类目商品才生效， 字数限制：商品描述goods_desc+温馨提示总计不超过500字。
        request.setWarmTips("str");
        // 只换不修的天数，目前只支持0和365
        request.setZhiHuanBuXiu(0);
        // 发货方式。0：无物流发货；1：有物流发货。
        request.setDeliveryType(0);
        return request;
    }
}
