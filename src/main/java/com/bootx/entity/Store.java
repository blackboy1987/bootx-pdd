package com.bootx.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
    private String mallName;

    @Column(updatable = false)
    private Integer merchantType;

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
}
