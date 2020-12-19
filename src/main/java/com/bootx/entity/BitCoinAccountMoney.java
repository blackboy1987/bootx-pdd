package com.bootx.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.math.BigDecimal;


@Entity
@Table(name = "BitCoinAccountWallet", uniqueConstraints = { @UniqueConstraint(columnNames = { "userId", "bitCoinAccountId","assetType" })})
public class BitCoinAccountMoney extends BaseEntity<Long> {

    private Long userId;

    private Long bitCoinAccountId;

    private Integer assetType;

    private BigDecimal money;

    private BigDecimal frozenMoney;

    private Integer state;

    private String name;

    private BigDecimal price;

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

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public BigDecimal getFrozenMoney() {
        return frozenMoney;
    }

    public void setFrozenMoney(BigDecimal frozenMoney) {
        this.frozenMoney = frozenMoney;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

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
}
