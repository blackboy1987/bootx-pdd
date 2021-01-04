
package com.bootx.plugin.craw.pojo.oneSixEightEightPlugin;

import com.bootx.common.BaseAttributeConverter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Convert;
import javax.persistence.Converter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonRootBean implements Serializable {

    private String discount;
    private String discountPrice;
    private String price;
    private String retailPrice;
    private String canBookCount;
    private String saleCount;
    @Convert(converter = PriceRangeConverter.class)
    private List<List<Double>> priceRange = new ArrayList<>();
    @Convert(converter = PriceRangeConverter.class)
    private List<List<Double>> priceRangeOriginal = new ArrayList<>();
    private List<SkuProps> skuProps;
    private Map<String,Sku> skuMap = new HashMap<>();

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(String discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(String retailPrice) {
        this.retailPrice = retailPrice;
    }

    public String getCanBookCount() {
        return canBookCount;
    }

    public void setCanBookCount(String canBookCount) {
        this.canBookCount = canBookCount;
    }

    public String getSaleCount() {
        return saleCount;
    }

    public void setSaleCount(String saleCount) {
        this.saleCount = saleCount;
    }

    public List<List<Double>> getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(List<List<Double>> priceRange) {
        this.priceRange = priceRange;
    }

    public List<List<Double>> getPriceRangeOriginal() {
        return priceRangeOriginal;
    }

    public void setPriceRangeOriginal(List<List<Double>> priceRangeOriginal) {
        this.priceRangeOriginal = priceRangeOriginal;
    }

    public List<SkuProps> getSkuProps() {
        return skuProps;
    }

    public void setSkuProps(List<SkuProps> skuProps) {
        this.skuProps = skuProps;
    }

    public Map<String, Sku> getSkuMap() {
        return skuMap;
    }

    public void setSkuMap(Map<String, Sku> skuMap) {
        this.skuMap = skuMap;
    }

    @Converter
    public static class PriceRangeConverter extends BaseAttributeConverter<List<List<Double>>> {

    }
}