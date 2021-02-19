package com.bootx.entity;

import com.bootx.common.BaseAttributeConverter;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Store extends BaseEntity<Long>{

    @ManyToOne
    @JoinColumn(updatable = false)
    public Member member;

    private String logo;

    private String mallDesc;

    @NotNull
    @Column(nullable = false,updatable = false,unique = true)
    @JsonView({ListView.class})
    private Long mallId;

    @NotEmpty
    @Column(nullable = false)
    @JsonView({ListView.class})
    private String mallName;

    @ManyToOne
    @JoinColumn(updatable = false)
    public Member salesMan;


    /**
     * 店铺类型,
     * 1:个人
     * 2:企业
     * 3:旗舰店
     * 4:专卖店
     * 5:专营店
     * 6:普通店
     */
    @Column(updatable = false)
    @JsonView({ListView.class})
    private Integer merchantType;

    private String accessToken;

    @ManyToOne(fetch = FetchType.LAZY)
    private StoreCategory storeCategory;

    @Lob
    @Convert(converter = StoreUploadConfigConverter.class)
    private StoreUploadConfig storeUploadConfig;

    @Convert(converter = StoreDeliveryTemplateConverter.class)
    private List<StoreDeliveryTemplate> storeDeliveryTemplates = new ArrayList<>();

    /**
     * 授权开始时间
     */
    private Long startAt;

    /**
     * 授权结束时间
     */
    private Long endAt;


    /**
     * accessToken 的过期时间
     */
    private Date expireDate;

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getMallDesc() {
        return mallDesc;
    }

    public void setMallDesc(String mallDesc) {
        this.mallDesc = mallDesc;
    }

    public Long getMallId() {
        return mallId;
    }

    public void setMallId(Long mallId) {
        this.mallId = mallId;
    }

    public String getMallName() {
        return mallName;
    }

    public void setMallName(String mallName) {
        this.mallName = mallName;
    }

    public Integer getMerchantType() {
        return merchantType;
    }

    public void setMerchantType(Integer merchantType) {
        this.merchantType = merchantType;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public StoreCategory getStoreCategory() {
        return storeCategory;
    }

    public void setStoreCategory(StoreCategory storeCategory) {
        this.storeCategory = storeCategory;
    }

    public Member getSalesMan() {
        return salesMan;
    }

    public void setSalesMan(Member salesMan) {
        this.salesMan = salesMan;
    }

    public StoreUploadConfig getStoreUploadConfig() {
        return storeUploadConfig;
    }

    public void setStoreUploadConfig(StoreUploadConfig storeUploadConfig) {
        this.storeUploadConfig = storeUploadConfig;
    }

    public List<StoreDeliveryTemplate> getStoreDeliveryTemplates() {
        return storeDeliveryTemplates;
    }

    public void setStoreDeliveryTemplates(List<StoreDeliveryTemplate> storeDeliveryTemplates) {
        this.storeDeliveryTemplates = storeDeliveryTemplates;
    }

    public Long getStartAt() {
        return startAt;
    }

    public void setStartAt(Long startAt) {
        this.startAt = startAt;
    }

    public Long getEndAt() {
        return endAt;
    }

    public void setEndAt(Long endAt) {
        this.endAt = endAt;
    }

    @Transient
    public boolean isExpired(){
        if(expireDate==null){
            return true;
        }
        return expireDate.compareTo(new Date())<=0;
    }

    @Transient
    @JsonView({ListView.class})
    public String getStoreCategoryName(){
        if(getStoreCategory()==null){
            return null;
        }
        return getStoreCategory().getName();
    }

    @Convert
    public static class StoreUploadConfigConverter extends BaseAttributeConverter<StoreUploadConfig>{}


    @Convert
    public static class StoreDeliveryTemplateConverter extends BaseAttributeConverter<List<StoreDeliveryTemplate>>{

    }
}
