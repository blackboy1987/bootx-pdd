
package com.bootx.plugin.craw.pojo.tMall;

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
public class ValItemInfo implements Serializable {

    @Convert(converter = SkuList.class)
    private List<SkuList> skuList = new ArrayList<>();
    @Convert(converter = SkuMapConverter.class)
    private Map<String,Sku> skuMap = new HashMap<>();
    private SalesProp salesProp;

    public List<SkuList> getSkuList() {
        return skuList;
    }

    public void setSkuList(List<SkuList> skuList) {
        this.skuList = skuList;
    }

    public Map<String, Sku> getSkuMap() {
        return skuMap;
    }

    public void setSkuMap(Map<String, Sku> skuMap) {
        this.skuMap = skuMap;
    }

    public SalesProp getSalesProp() {
        return salesProp;
    }

    public void setSalesProp(SalesProp salesProp) {
        this.salesProp = salesProp;
    }

    @Converter
    public static class SkuListConverter extends BaseAttributeConverter<List<SkuList>> {

    }
    @Converter
    public static class SkuMapConverter extends BaseAttributeConverter<Map<String,Sku>> {

    }
}