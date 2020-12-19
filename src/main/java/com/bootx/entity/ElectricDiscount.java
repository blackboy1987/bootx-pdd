/**
  * Copyright 2020 json.cn 
  */
package com.bootx.entity;

import javax.persistence.Entity;
import java.math.BigDecimal;

/**
 * 电费折扣
 * @author black
 */
@Entity
public class ElectricDiscount extends BaseEntity<Long> {

    private Long productId;
    private BigDecimal discount;
    private Integer day;
    private BigDecimal deduction;
    private Integer tag;
    private Boolean enable;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public BigDecimal getDeduction() {
        return deduction;
    }

    public void setDeduction(BigDecimal deduction) {
        this.deduction = deduction;
    }

    public Integer getTag() {
        return tag;
    }

    public void setTag(Integer tag) {
        this.tag = tag;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }
}