package com.bootx.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StoreUploadConfig implements Serializable {

    /**
     * INTEGER	非必填	坏果包赔
     */
    private Integer badFruitClaim;

    /**
     *是否加后缀
     */
    private Boolean addAfter;

    /**
     *加后缀
     */
    private String addAfterWord;

    /**
     * 是否加前缀
     */
    private Boolean addBefore;

    /**
     *addBeforeWord
     */
    private String addABeforeWord;

    /**
     *限购次数
     */
    private Long buyLimit;

    /**
     *轮播图补足10张
     */
    private Boolean carouselAddTen;

    /**
     *轮播图第几张作为主图
     */
    private Integer carouselIndex;

    /**
     *轮播图随机位置
     */
    private Boolean carouselRandom;

    /**
     *物流模板
     */
    private Long costTemplateId;

    /**
     *团购人数
     */
    private Long customerNum;

    /**
     *是否删除关键词
     */
    private Boolean delete;

    /**
     *删除关键词
     */
    private String deleteWord;

    /**
     *发货方式：0：无物流，1：有物流
     */
    private Integer deliveryType;

    /**
     *删除后第几张详情图
     */
    private Integer detailPicDelEnd;

    /**
     *删除前第几张详情
     */
    private Integer detailPicDelStart;

    /**
     *保留商品详情图，第几张开始
     */
    private Integer detailPicEnd;

    /**
     *保留商品详情图，第几张结束
     */
    private String detailPicHeader;

    /**
     *过滤已发布的商品
     */
    private Boolean filter;

    /**
     *商品类型 1：普通商品 2：进口商品
     */
    private Integer goodsType;

    /**
     *团购价 溢价率
     */
    private BigDecimal groupPriceRate;

    /**
     * 团购价 溢价方式。1：加 2：减 3：乘 4：除
     */
    private Integer groupPriceType;

    /**
     *假一罚十
     */
    private Boolean isFolt;

    /**
     *开启预售
     */
    private Boolean isPreSale;

    /**
     *七天无理由退货
     */
    private Boolean isRefundable;

    /**
     *当库存数少于lackStockBase1
     */
    private Long lackStockBase1;

    /**
     *当库存数少于lackStockBase1时，设置为lackStockBase2
     */
    private Long lackStockBase2;

    /**
     *市场价 溢价率
     */
    private BigDecimal markerPriceRate;

    /**
     *市场价 溢价方式。1：加 2：减 3：乘 4：除
     */
    private Integer markerPriceType;

    /**
     *键词替换为newWord
     */
    private String newWord;

    /**
     *替换关键词oldWord，替换为newWord
     */
    private String oldWord;

    /**
     *预售时间
     */
    private String preSaleTime;

    /**
     *打乱标题
     */
    private Boolean randomTitle;

    /**
     *是否替换关键词
     */
    private Boolean replace;

    /**
     *是否是二手货
     */
    private Boolean secondHand;

    /**
     *发货时间
     */
    private Long shipmentLimitSecond;

    /**
     *单买价 溢价率
     */
    private BigDecimal singlePriceRate;

    /**
     *单买价  溢价方式。1：加 2：减 3：乘 4：除
     */
    private Integer singlePriceType;

    /**
     *SKU图缺失处理，第skuPic张主图设置为SKU图
     */
    private Integer skuPic;

    /**
     * sku编码 0:原始编码 1：自动生成
     */
    private Integer skuSnType;


    /**
     *sku编码前缀
     */
    private String skuPrefix;

    /**
     *sku编码后缀
     */
    private String skuSuffix;

    /**
     *统一库存数
     */
    private Long stockBase;

    /**
     *库存配置 0：原始库存 1：统一库存
     */
    private Integer stockConfig;

    /**
     *当原标题长度超过titleMaxLength个字符限制时，删除
     * 0：最前面
     * 1：最后面
     */
    private Integer titleDealType;

    /**
     *当原标题长度超过titleMaxLength个字符限制时，删除
     */
    private Integer titleMaxLength;

    /**
     *复制后上架方式
     * 1：立即上架
     * 2：放入草稿箱
     */
    private Integer uploadType;

    /**
     * INTEGER	非必填	缺重包退
     */
    private Integer lackOfWeightClaim;

    public Integer getBadFruitClaim() {
        return badFruitClaim;
    }

    public void setBadFruitClaim(Integer badFruitClaim) {
        this.badFruitClaim = badFruitClaim;
    }

    public Boolean getAddAfter() {
        return addAfter;
    }

    public void setAddAfter(Boolean addAfter) {
        this.addAfter = addAfter;
    }

    public String getAddAfterWord() {
        return addAfterWord;
    }

    public void setAddAfterWord(String addAfterWord) {
        this.addAfterWord = addAfterWord;
    }

    public Boolean getAddBefore() {
        return addBefore;
    }

    public void setAddBefore(Boolean addBefore) {
        this.addBefore = addBefore;
    }

    public String getAddABeforeWord() {
        return addABeforeWord;
    }

    public void setAddABeforeWord(String addABeforeWord) {
        this.addABeforeWord = addABeforeWord;
    }

    public Long getBuyLimit() {
        return buyLimit;
    }

    public void setBuyLimit(Long buyLimit) {
        this.buyLimit = buyLimit;
    }

    public Boolean getCarouselAddTen() {
        return carouselAddTen;
    }

    public void setCarouselAddTen(Boolean carouselAddTen) {
        this.carouselAddTen = carouselAddTen;
    }

    public Integer getCarouselIndex() {
        return carouselIndex;
    }

    public void setCarouselIndex(Integer carouselIndex) {
        this.carouselIndex = carouselIndex;
    }

    public Boolean getCarouselRandom() {
        return carouselRandom;
    }

    public void setCarouselRandom(Boolean carouselRandom) {
        this.carouselRandom = carouselRandom;
    }

    public Long getCostTemplateId() {
        return costTemplateId;
    }

    public void setCostTemplateId(Long costTemplateId) {
        this.costTemplateId = costTemplateId;
    }

    public Long getCustomerNum() {
        return customerNum;
    }

    public void setCustomerNum(Long customerNum) {
        this.customerNum = customerNum;
    }

    public Boolean getDelete() {
        return delete;
    }

    public void setDelete(Boolean delete) {
        this.delete = delete;
    }

    public String getDeleteWord() {
        return deleteWord;
    }

    public void setDeleteWord(String deleteWord) {
        this.deleteWord = deleteWord;
    }

    public Integer getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(Integer deliveryType) {
        this.deliveryType = deliveryType;
    }

    public Integer getDetailPicDelEnd() {
        return detailPicDelEnd;
    }

    public void setDetailPicDelEnd(Integer detailPicDelEnd) {
        this.detailPicDelEnd = detailPicDelEnd;
    }

    public Integer getDetailPicDelStart() {
        return detailPicDelStart;
    }

    public void setDetailPicDelStart(Integer detailPicDelStart) {
        this.detailPicDelStart = detailPicDelStart;
    }

    public Integer getDetailPicEnd() {
        return detailPicEnd;
    }

    public void setDetailPicEnd(Integer detailPicEnd) {
        this.detailPicEnd = detailPicEnd;
    }

    public String getDetailPicHeader() {
        return detailPicHeader;
    }

    public void setDetailPicHeader(String detailPicHeader) {
        this.detailPicHeader = detailPicHeader;
    }

    public Boolean getFilter() {
        return filter;
    }

    public void setFilter(Boolean filter) {
        this.filter = filter;
    }

    public Integer getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(Integer goodsType) {
        this.goodsType = goodsType;
    }

    public BigDecimal getGroupPriceRate() {
        return groupPriceRate;
    }

    public void setGroupPriceRate(BigDecimal groupPriceRate) {
        this.groupPriceRate = groupPriceRate;
    }

    public Integer getGroupPriceType() {
        return groupPriceType;
    }

    public void setGroupPriceType(Integer groupPriceType) {
        this.groupPriceType = groupPriceType;
    }

    public Boolean getIsFolt() {
        return isFolt;
    }

    public void setIsFolt(Boolean isFolt) {
        this.isFolt = isFolt;
    }

    public Boolean getIsPreSale() {
        return isPreSale;
    }

    public void setIsPreSale(Boolean isPreSale) {
        this.isPreSale = isPreSale;
    }

    public Boolean getIsRefundable() {
        return isRefundable;
    }

    public void setIsRefundable(Boolean isRefundable) {
        this.isRefundable = isRefundable;
    }

    public Long getLackStockBase1() {
        return lackStockBase1;
    }

    public void setLackStockBase1(Long lackStockBase1) {
        this.lackStockBase1 = lackStockBase1;
    }

    public Long getLackStockBase2() {
        return lackStockBase2;
    }

    public void setLackStockBase2(Long lackStockBase2) {
        this.lackStockBase2 = lackStockBase2;
    }

    public BigDecimal getMarkerPriceRate() {
        return markerPriceRate;
    }

    public void setMarkerPriceRate(BigDecimal markerPriceRate) {
        this.markerPriceRate = markerPriceRate;
    }

    public Integer getMarkerPriceType() {
        return markerPriceType;
    }

    public void setMarkerPriceType(Integer markerPriceType) {
        this.markerPriceType = markerPriceType;
    }

    public String getNewWord() {
        return newWord;
    }

    public void setNewWord(String newWord) {
        this.newWord = newWord;
    }

    public String getOldWord() {
        return oldWord;
    }

    public void setOldWord(String oldWord) {
        this.oldWord = oldWord;
    }

    public String getPreSaleTime() {
        return preSaleTime;
    }

    public void setPreSaleTime(String preSaleTime) {
        this.preSaleTime = preSaleTime;
    }

    public Boolean getRandomTitle() {
        return randomTitle;
    }

    public void setRandomTitle(Boolean randomTitle) {
        this.randomTitle = randomTitle;
    }

    public Boolean getReplace() {
        return replace;
    }

    public void setReplace(Boolean replace) {
        this.replace = replace;
    }

    public Boolean getSecondHand() {
        return secondHand;
    }

    public void setSecondHand(Boolean secondHand) {
        this.secondHand = secondHand;
    }

    public Long getShipmentLimitSecond() {
        return shipmentLimitSecond;
    }

    public void setShipmentLimitSecond(Long shipmentLimitSecond) {
        this.shipmentLimitSecond = shipmentLimitSecond;
    }

    public BigDecimal getSinglePriceRate() {
        return singlePriceRate;
    }

    public void setSinglePriceRate(BigDecimal singlePriceRate) {
        this.singlePriceRate = singlePriceRate;
    }

    public Integer getSinglePriceType() {
        return singlePriceType;
    }

    public void setSinglePriceType(Integer singlePriceType) {
        this.singlePriceType = singlePriceType;
    }

    public Integer getSkuPic() {
        return skuPic;
    }

    public void setSkuPic(Integer skuPic) {
        this.skuPic = skuPic;
    }

    public Integer getSkuSnType() {
        return skuSnType;
    }

    public void setSkuSnType(Integer skuSnType) {
        this.skuSnType = skuSnType;
    }

    public String getSkuPrefix() {
        return skuPrefix;
    }

    public void setSkuPrefix(String skuPrefix) {
        this.skuPrefix = skuPrefix;
    }

    public String getSkuSuffix() {
        return skuSuffix;
    }

    public void setSkuSuffix(String skuSuffix) {
        this.skuSuffix = skuSuffix;
    }

    public Long getStockBase() {
        return stockBase;
    }

    public void setStockBase(Long stockBase) {
        this.stockBase = stockBase;
    }

    public Integer getStockConfig() {
        return stockConfig;
    }

    public void setStockConfig(Integer stockConfig) {
        this.stockConfig = stockConfig;
    }

    public Integer getTitleDealType() {
        return titleDealType;
    }

    public void setTitleDealType(Integer titleDealType) {
        this.titleDealType = titleDealType;
    }

    public Integer getTitleMaxLength() {
        return titleMaxLength;
    }

    public void setTitleMaxLength(Integer titleMaxLength) {
        this.titleMaxLength = titleMaxLength;
    }

    public Integer getUploadType() {
        return uploadType;
    }

    public void setUploadType(Integer uploadType) {
        this.uploadType = uploadType;
    }

    public Integer getLackOfWeightClaim() {
        return lackOfWeightClaim;
    }

    public void setLackOfWeightClaim(Integer lackOfWeightClaim) {
        this.lackOfWeightClaim = lackOfWeightClaim;
    }
}
