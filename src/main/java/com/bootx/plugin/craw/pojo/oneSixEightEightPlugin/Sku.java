
package com.bootx.plugin.craw.pojo.oneSixEightEightPlugin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Sku implements Serializable {

    private String specId;
    private Long saleCount;
    private String discountPrice;
    private long canBookCount;
    private String skuId;

    private List<SpecAttr> specAttrs = new ArrayList<>();


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

    public List<SpecAttr> getSpecAttrs() {
        return specAttrs;
    }

    public void setSpecAttrs(List<SpecAttr> specAttrs) {
        this.specAttrs = specAttrs;
    }
}