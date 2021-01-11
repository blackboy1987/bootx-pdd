package com.bootx.entity;

import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Store extends BaseEntity<Long>{

    @ManyToOne
    @JoinColumn(updatable = false)
    public Member member;

    private String logo;

    private String mallDesc;

    @NotNull
    @Column(nullable = false,updatable = false,unique = true)
    private Long mallId;

    @NotEmpty
    @Column(nullable = false)
    @JsonView({ListView.class})
    private String mallName;

    @Column(updatable = false)
    private Integer merchantType;

    private String accessToken;

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

    @Transient
    @JsonView({ListView.class})
    public boolean isExpired(){
        if(expireDate==null){
            return true;
        }
        return expireDate.compareTo(new Date())<=0;
    }
}
