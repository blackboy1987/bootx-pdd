package com.bootx.entity;

import javax.persistence.Entity;

/**
 * 收益
 */
@Entity
public class Invest extends BaseEntity<Long>{

    private Long userId;

    private Long productId;

    private Long productName;

    private Long invest;

    private Long frozenInvest;

    private Long frozenInvestTemp;

    private Long allBtc;

    private Long allHpt;

    private Long allEth;

    private Long lastEth;

    private Long lastBtc;

    private Long lastHpt;

    private Long lastTime;

    private Long frozenTime;

    private Long investTime;

    private Long returnMoney;

    private Long returnDays;

    private String userName;

    private String phone;

    private Long isExpire;

    private Long validity;

    private Long allBtcPrice;

    private Long allHptPrice;

    private Long lastBtcPrice;

    private Long lastHptPrice;

    private Long allEthPrice;

    private Long lastEthPrice;

    private Long type;

    private Long profit;

    private Long profitYear;

    private Long electric;

    private Long electricDiscount;

    private Long manage;

    private Long manageDiscount;

    private Long btcDiscount;

    private Long hbtDiscount;

    private Long expireDate;

    private Long comeDate;

    private Long expirationDate;

    private Integer coinType;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getProductName() {
        return productName;
    }

    public void setProductName(Long productName) {
        this.productName = productName;
    }

    public Long getInvest() {
        return invest;
    }

    public void setInvest(Long invest) {
        this.invest = invest;
    }

    public Long getFrozenInvest() {
        return frozenInvest;
    }

    public void setFrozenInvest(Long frozenInvest) {
        this.frozenInvest = frozenInvest;
    }

    public Long getFrozenInvestTemp() {
        return frozenInvestTemp;
    }

    public void setFrozenInvestTemp(Long frozenInvestTemp) {
        this.frozenInvestTemp = frozenInvestTemp;
    }

    public Long getAllBtc() {
        return allBtc;
    }

    public void setAllBtc(Long allBtc) {
        this.allBtc = allBtc;
    }

    public Long getAllHpt() {
        return allHpt;
    }

    public void setAllHpt(Long allHpt) {
        this.allHpt = allHpt;
    }

    public Long getAllEth() {
        return allEth;
    }

    public void setAllEth(Long allEth) {
        this.allEth = allEth;
    }

    public Long getLastEth() {
        return lastEth;
    }

    public void setLastEth(Long lastEth) {
        this.lastEth = lastEth;
    }

    public Long getLastBtc() {
        return lastBtc;
    }

    public void setLastBtc(Long lastBtc) {
        this.lastBtc = lastBtc;
    }

    public Long getLastHpt() {
        return lastHpt;
    }

    public void setLastHpt(Long lastHpt) {
        this.lastHpt = lastHpt;
    }

    public Long getLastTime() {
        return lastTime;
    }

    public void setLastTime(Long lastTime) {
        this.lastTime = lastTime;
    }

    public Long getFrozenTime() {
        return frozenTime;
    }

    public void setFrozenTime(Long frozenTime) {
        this.frozenTime = frozenTime;
    }

    public Long getInvestTime() {
        return investTime;
    }

    public void setInvestTime(Long investTime) {
        this.investTime = investTime;
    }

    public Long getReturnMoney() {
        return returnMoney;
    }

    public void setReturnMoney(Long returnMoney) {
        this.returnMoney = returnMoney;
    }

    public Long getReturnDays() {
        return returnDays;
    }

    public void setReturnDays(Long returnDays) {
        this.returnDays = returnDays;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getIsExpire() {
        return isExpire;
    }

    public void setIsExpire(Long isExpire) {
        this.isExpire = isExpire;
    }

    public Long getValidity() {
        return validity;
    }

    public void setValidity(Long validity) {
        this.validity = validity;
    }

    public Long getAllBtcPrice() {
        return allBtcPrice;
    }

    public void setAllBtcPrice(Long allBtcPrice) {
        this.allBtcPrice = allBtcPrice;
    }

    public Long getAllHptPrice() {
        return allHptPrice;
    }

    public void setAllHptPrice(Long allHptPrice) {
        this.allHptPrice = allHptPrice;
    }

    public Long getLastBtcPrice() {
        return lastBtcPrice;
    }

    public void setLastBtcPrice(Long lastBtcPrice) {
        this.lastBtcPrice = lastBtcPrice;
    }

    public Long getLastHptPrice() {
        return lastHptPrice;
    }

    public void setLastHptPrice(Long lastHptPrice) {
        this.lastHptPrice = lastHptPrice;
    }

    public Long getAllEthPrice() {
        return allEthPrice;
    }

    public void setAllEthPrice(Long allEthPrice) {
        this.allEthPrice = allEthPrice;
    }

    public Long getLastEthPrice() {
        return lastEthPrice;
    }

    public void setLastEthPrice(Long lastEthPrice) {
        this.lastEthPrice = lastEthPrice;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public Long getProfit() {
        return profit;
    }

    public void setProfit(Long profit) {
        this.profit = profit;
    }

    public Long getProfitYear() {
        return profitYear;
    }

    public void setProfitYear(Long profitYear) {
        this.profitYear = profitYear;
    }

    public Long getElectric() {
        return electric;
    }

    public void setElectric(Long electric) {
        this.electric = electric;
    }

    public Long getElectricDiscount() {
        return electricDiscount;
    }

    public void setElectricDiscount(Long electricDiscount) {
        this.electricDiscount = electricDiscount;
    }

    public Long getManage() {
        return manage;
    }

    public void setManage(Long manage) {
        this.manage = manage;
    }

    public Long getManageDiscount() {
        return manageDiscount;
    }

    public void setManageDiscount(Long manageDiscount) {
        this.manageDiscount = manageDiscount;
    }

    public Long getBtcDiscount() {
        return btcDiscount;
    }

    public void setBtcDiscount(Long btcDiscount) {
        this.btcDiscount = btcDiscount;
    }

    public Long getHbtDiscount() {
        return hbtDiscount;
    }

    public void setHbtDiscount(Long hbtDiscount) {
        this.hbtDiscount = hbtDiscount;
    }

    public Long getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Long expireDate) {
        this.expireDate = expireDate;
    }

    public Long getComeDate() {
        return comeDate;
    }

    public void setComeDate(Long comeDate) {
        this.comeDate = comeDate;
    }

    public Long getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Long expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Integer getCoinType() {
        return coinType;
    }

    public void setCoinType(Integer coinType) {
        this.coinType = coinType;
    }
}
