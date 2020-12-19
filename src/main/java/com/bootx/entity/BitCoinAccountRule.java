package com.bootx.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.math.BigDecimal;

/**
 * @author black
 */
@Entity
@Table(name = "BitCoinAccountRule", uniqueConstraints = { @UniqueConstraint(columnNames = { "userId", "bitCoinAccountId",assetType })})
public class BitCoinAccountRule extends BaseEntity<Long> {

    private Long userId;

    private Long bitCoinAccountId;

    private Integer assetType;

    private Boolean canRecharge;

    private Boolean canWithdraw;

    private Boolean canTrade;

    private BigDecimal rechargeLimit;

    private BigDecimal rechargeRate;

    private BigDecimal rechargeMax;

    private BigDecimal withdrawLimit;

    private BigDecimal withdrawRate;

    private BigDecimal withdrawMax;

    private Boolean activation;

    private Long withdrawProduct;

    private Boolean withdrawAuth;

    private String productName;

    private String name;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBitCoinAccountId() {
        return bitCoinAccountId;
    }

    public void setBitCoinAccountId(Long bitCoinAccountId) {
        this.bitCoinAccountId = bitCoinAccountId;
    }

    public Integer getAssetType() {
        return assetType;
    }

    public void setAssetType(Integer assetType) {
        this.assetType = assetType;
    }

    public Boolean getCanRecharge() {
        return canRecharge;
    }

    public void setCanRecharge(Boolean canRecharge) {
        this.canRecharge = canRecharge;
    }

    public Boolean getCanWithdraw() {
        return canWithdraw;
    }

    public void setCanWithdraw(Boolean canWithdraw) {
        this.canWithdraw = canWithdraw;
    }

    public Boolean getCanTrade() {
        return canTrade;
    }

    public void setCanTrade(Boolean canTrade) {
        this.canTrade = canTrade;
    }

    public BigDecimal getRechargeLimit() {
        return rechargeLimit;
    }

    public void setRechargeLimit(BigDecimal rechargeLimit) {
        this.rechargeLimit = rechargeLimit;
    }

    public BigDecimal getRechargeRate() {
        return rechargeRate;
    }

    public void setRechargeRate(BigDecimal rechargeRate) {
        this.rechargeRate = rechargeRate;
    }

    public BigDecimal getRechargeMax() {
        return rechargeMax;
    }

    public void setRechargeMax(BigDecimal rechargeMax) {
        this.rechargeMax = rechargeMax;
    }

    public BigDecimal getWithdrawLimit() {
        return withdrawLimit;
    }

    public void setWithdrawLimit(BigDecimal withdrawLimit) {
        this.withdrawLimit = withdrawLimit;
    }

    public BigDecimal getWithdrawRate() {
        return withdrawRate;
    }

    public void setWithdrawRate(BigDecimal withdrawRate) {
        this.withdrawRate = withdrawRate;
    }

    public BigDecimal getWithdrawMax() {
        return withdrawMax;
    }

    public void setWithdrawMax(BigDecimal withdrawMax) {
        this.withdrawMax = withdrawMax;
    }

    public Boolean getActivation() {
        return activation;
    }

    public void setActivation(Boolean activation) {
        this.activation = activation;
    }

    public Long getWithdrawProduct() {
        return withdrawProduct;
    }

    public void setWithdrawProduct(Long withdrawProduct) {
        this.withdrawProduct = withdrawProduct;
    }

    public Boolean getWithdrawAuth() {
        return withdrawAuth;
    }

    public void setWithdrawAuth(Boolean withdrawAuth) {
        this.withdrawAuth = withdrawAuth;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
