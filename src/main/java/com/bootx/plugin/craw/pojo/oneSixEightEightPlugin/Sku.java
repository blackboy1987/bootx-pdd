
package com.bootx.plugin.craw.pojo.oneSixEightEightPlugin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Sku implements Serializable {

    private String specId;
    private Long saleCount;
    private String discountPrice;
    private long canBookCount;
    private String skuId;

    public String getSpecId() {
        return specId;
    }

    public void setSpecId(String specId) {
        this.specId = specId;
    }

    public Long getSaleCount() {
        return saleCount;
    }

    public void setSaleCount(Long saleCount) {
        this.saleCount = saleCount;
    }

    public String getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(String discountPrice) {
        this.discountPrice = discountPrice;
    }

    public long getCanBookCount() {
        return canBookCount;
    }

    public void setCanBookCount(long canBookCount) {
        this.canBookCount = canBookCount;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }
}