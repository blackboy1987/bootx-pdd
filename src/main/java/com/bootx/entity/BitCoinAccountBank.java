package com.bootx.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


/**
 * {
 *                 "id":1763114,
 *                 "userId":218776,
 *                 "assetType":5,
 *                 "money":0,
 *                 "frozenMoney":0,
 *                 "state":true,
 *                 "name":"ETH",
 *                 "price":"0.00"
 *             }
 * @author black
 */
@Entity
@Table(name = "BitCoinAccountBank", uniqueConstraints = { @UniqueConstraint(columnNames = { "userId", "bitCoinAccountId" })})
public class BitCoinAccountBank extends BaseEntity<Long> {

    private Long userId;

    private Long bitCoinAccountId;

    private String bankType;

    private String name;

    private String phone;

    private String bankCode;

    private String bankAddr;

    private Boolean isDefault;

    private Boolean isDel;


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

    public String getBankType() {
        return bankType;
    }

    public void setBankType(String bankType) {
        this.bankType = bankType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankAddr() {
        return bankAddr;
    }

    public void setBankAddr(String bankAddr) {
        this.bankAddr = bankAddr;
    }

    public Boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    public Boolean getIsDel() {
        return isDel;
    }

    public void setIsDel(Boolean isDel) {
        this.isDel = isDel;
    }
}
