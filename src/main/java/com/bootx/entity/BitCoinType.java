package com.bootx.entity;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
public class BitCoinType extends BaseEntity<Long>{

    @NotEmpty
    @Column(nullable = false,updatable = false,unique = true)
    private String name;

    @Column(precision = 27, scale = 12)
    private BigDecimal price;

    @NotNull
    @Column(nullable = false)
    private Boolean isEnabled;

    private Integer assetType;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Boolean getEnabled() {
        return isEnabled;
    }

    public void setEnabled(Boolean enabled) {
        isEnabled = enabled;
    }

    public Integer getAssetType() {
        return assetType;
    }

    public void setAssetType(Integer assetType) {
        this.assetType = assetType;
    }
}
