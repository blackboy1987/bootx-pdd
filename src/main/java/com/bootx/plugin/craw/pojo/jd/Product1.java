
package com.bootx.plugin.craw.pojo.jd;
import com.bootx.common.BaseAttributeConverter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Converter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Product1 implements Serializable {
    private ImageAndVideoJson imageAndVideoJson;
    private Long skuid;
    private String name;
    private String skuidkey;
    private String href;
    private String src;
    private List<String> imageList = new ArrayList<>();
    private List<Long> cat = new ArrayList<>();
    private Long brand;
    private Integer pType;
    private Long venderId;
    private String shopId;

    private String desc;

    public ImageAndVideoJson getImageAndVideoJson() {
        return imageAndVideoJson;
    }

    public void setImageAndVideoJson(ImageAndVideoJson imageAndVideoJson) {
        this.imageAndVideoJson = imageAndVideoJson;
    }

    public Long getSkuid() {
        return skuid;
    }

    public void setSkuid(Long skuid) {
        this.skuid = skuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSkuidkey() {
        return skuidkey;
    }

    public void setSkuidkey(String skuidkey) {
        this.skuidkey = skuidkey;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public List<String> getImageList() {
        return imageList;
    }

    public List<Long> getCat() {
        return cat;
    }

    public void setCat(List<Long> cat) {
        this.cat = cat;
    }

    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
    }

    public Long getBrand() {
        return brand;
    }

    public void setBrand(Long brand) {
        this.brand = brand;
    }

    public Integer getpType() {
        return pType;
    }

    public void setpType(Integer pType) {
        this.pType = pType;
    }

    public Long getVenderId() {
        return venderId;
    }

    public void setVenderId(Long venderId) {
        this.venderId = venderId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Converter
    public static class ColorSizeConverter extends BaseAttributeConverter<List<Map<String,String>>>{

    }
}