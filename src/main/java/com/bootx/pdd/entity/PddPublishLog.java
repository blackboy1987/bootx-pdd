package com.bootx.pdd.entity;

import com.bootx.common.BaseAttributeConverter;
import com.bootx.entity.BaseEntity;
import com.bootx.entity.Store;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

@Entity
public class PddPublishLog extends BaseEntity<Long> {

    @ManyToOne
    @JoinColumn(nullable = false,updatable = false)
    private Store store;

    @NotEmpty
    @JoinColumn(nullable = false,updatable = false)
    private String storeName;

    @NotNull
    @Column(nullable = false,updatable = false)
    private Long sn;

    @ManyToOne
    @JoinColumn(nullable = false,updatable = false)
    private PddCrawlerProduct crawlerProduct;

    @JoinColumn(nullable = false,updatable = false)
    private String productName;

    @NotEmpty
    @Column(nullable = false,updatable = false)
    private String accessToken;

    private Integer code;

    private String msg;

    /**
     * 0: 已创建
     * 1： 进行中
     * 2： 已完成
     * 3： 已失败
     */
    @NotNull
    @Column(nullable = false)
    private Integer status;

    @Column(length = 2000)
    @Convert(converter = ResultConverter.class)
    public Map<String,Object> result = new HashMap<>();


    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public PddCrawlerProduct getCrawlerProduct() {
        return crawlerProduct;
    }

    public void setCrawlerProduct(PddCrawlerProduct crawlerProduct) {
        this.crawlerProduct = crawlerProduct;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getSn() {
        return sn;
    }

    public void setSn(Long sn) {
        this.sn = sn;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Map<String, Object> getResult() {
        return result;
    }

    public void setResult(Map<String, Object> result) {
        this.result = result;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Converter
    public static class ResultConverter extends BaseAttributeConverter<Map<String,Object>>{

    }
}
