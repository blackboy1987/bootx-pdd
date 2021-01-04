
package com.bootx.plugin.craw.pojo.tMall;

import com.bootx.common.BaseAttributeConverter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Convert;
import javax.persistence.Converter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemDO implements Serializable {

    private String reservePrice;
    private String title;
    @Convert(converter = AttachImgUrlConverter.class)
    private List<String> attachImgUrl = new ArrayList<>();
    private Long quantity;

    public String getReservePrice() {
        return reservePrice;
    }

    public void setReservePrice(String reservePrice) {
        this.reservePrice = reservePrice;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getAttachImgUrl() {
        return attachImgUrl;
    }

    public void setAttachImgUrl(List<String> attachImgUrl) {
        this.attachImgUrl = attachImgUrl;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    @Converter
    public static class AttachImgUrlConverter extends BaseAttributeConverter<List<String>> {

    }
}