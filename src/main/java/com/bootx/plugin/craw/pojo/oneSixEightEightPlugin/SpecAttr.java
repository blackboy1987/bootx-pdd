package com.bootx.plugin.craw.pojo.oneSixEightEightPlugin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SpecAttr implements Serializable {

    private Integer fid;

    private String imgSupport;

    private String name;

    private Integer order;

    private String value;

    private Integer valueId;

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public String getImgSupport() {
        return imgSupport;
    }

    public void setImgSupport(String imgSupport) {
        this.imgSupport = imgSupport;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getValueId() {
        return valueId;
    }

    public void setValueId(Integer valueId) {
        this.valueId = valueId;
    }
}
