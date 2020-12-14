
package com.bootx.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class MineMachine extends BaseEntity<Long> {

    private Integer type;
    private String name;
    private String tag;
    private String icon;
    private String images;
    private Integer saleType;
    private BigDecimal rmbPrice;
    private BigDecimal price;
    private Integer invest;
    private BigDecimal profit;
    private BigDecimal rmbElectricPrice;
    private BigDecimal electric;
    private BigDecimal electricDiscount;

    /**
     * 管理费
     */
    private Integer manage;
    private Integer manageDiscount;
    private Integer profitYear;

    @Column(name ="limits")
    private Integer limit;
    private Integer defaultBuyNumber;
    private Boolean isMarketable;
    private Boolean isSpecials;
    private String specialsIcon;
    private Integer monthSales;
    private Boolean profitDay;
    private String returnProfitDay;
    private Integer weekSales;
    private Integer sales;
    private BigDecimal money;
    private Long stock;
    private Boolean isStock;
    private String expirationDate;
    private String validity;
    private Boolean isRecommend;
    private Integer newsId;
    private String investTime;
    private Integer btcDiscount;
    private BigDecimal hbtDiscount;
    private Integer hbtDiscountType;
    private String maxLimit;
    private String earnest;
    private String returnRate;
    private String interest;
    private String surplus;
    private String maxQouta;
    private String isReward;
    private String hierarchy;
    private Integer coinType;
    private BigDecimal exchangeRate;
    private String productId;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public Integer getSaleType() {
        return saleType;
    }

    public void setSaleType(Integer saleType) {
        this.saleType = saleType;
    }

    public BigDecimal getRmbPrice() {
        return rmbPrice;
    }

    public void setRmbPrice(BigDecimal rmbPrice) {
        this.rmbPrice = rmbPrice;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getInvest() {
        return invest;
    }

    public void setInvest(Integer invest) {
        this.invest = invest;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    public BigDecimal getRmbElectricPrice() {
        return rmbElectricPrice;
    }

    public void setRmbElectricPrice(BigDecimal rmbElectricPrice) {
        this.rmbElectricPrice = rmbElectricPrice;
    }

    public BigDecimal getElectric() {
        return electric;
    }

    public void setElectric(BigDecimal electric) {
        this.electric = electric;
    }

    public BigDecimal getElectricDiscount() {
        return electricDiscount;
    }

    public void setElectricDiscount(BigDecimal electricDiscount) {
        this.electricDiscount = electricDiscount;
    }

    public Integer getManage() {
        return manage;
    }

    public void setManage(Integer manage) {
        this.manage = manage;
    }

    public Integer getManageDiscount() {
        return manageDiscount;
    }

    public void setManageDiscount(Integer manageDiscount) {
        this.manageDiscount = manageDiscount;
    }

    public Integer getProfitYear() {
        return profitYear;
    }

    public void setProfitYear(Integer profitYear) {
        this.profitYear = profitYear;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getDefaultBuyNumber() {
        return defaultBuyNumber;
    }

    public void setDefaultBuyNumber(Integer defaultBuyNumber) {
        this.defaultBuyNumber = defaultBuyNumber;
    }

    public Boolean getIsMarketable() {
        return isMarketable;
    }

    public void setIsMarketable(Boolean isMarketable) {
        this.isMarketable = isMarketable;
    }

    public Boolean getIsSpecials() {
        return isSpecials;
    }

    public void setIsSpecials(Boolean isSpecials) {
        this.isSpecials = isSpecials;
    }

    public String getSpecialsIcon() {
        return specialsIcon;
    }

    public void setSpecialsIcon(String specialsIcon) {
        this.specialsIcon = specialsIcon;
    }

    public Integer getMonthSales() {
        return monthSales;
    }

    public void setMonthSales(Integer monthSales) {
        this.monthSales = monthSales;
    }

    public Boolean getProfitDay() {
        return profitDay;
    }

    public void setProfitDay(Boolean profitDay) {
        this.profitDay = profitDay;
    }

    public String getReturnProfitDay() {
        return returnProfitDay;
    }

    public void setReturnProfitDay(String returnProfitDay) {
        this.returnProfitDay = returnProfitDay;
    }

    public Integer getWeekSales() {
        return weekSales;
    }

    public void setWeekSales(Integer weekSales) {
        this.weekSales = weekSales;
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Long getStock() {
        return stock;
    }


    public void setStock(Long stock) {
        this.stock = stock;
    }

    public Boolean getIsStock() {
        return isStock;
    }

    public void setIsStock(Boolean isStock) {
        this.isStock = isStock;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

    public Boolean getIsRecommend() {
        return isRecommend;
    }

    public void setIsRecommend(Boolean isRecommend) {
        this.isRecommend = isRecommend;
    }

    public Integer getNewsId() {
        return newsId;
    }

    public void setNewsId(Integer newsId) {
        this.newsId = newsId;
    }

    public String getInvestTime() {
        return investTime;
    }

    public void setInvestTime(String investTime) {
        this.investTime = investTime;
    }

    public Integer getBtcDiscount() {
        return btcDiscount;
    }

    public void setBtcDiscount(Integer btcDiscount) {
        this.btcDiscount = btcDiscount;
    }

    public BigDecimal getHbtDiscount() {
        return hbtDiscount;
    }

    public void setHbtDiscount(BigDecimal hbtDiscount) {
        this.hbtDiscount = hbtDiscount;
    }

    public Integer getHbtDiscountType() {
        return hbtDiscountType;
    }

    public void setHbtDiscountType(Integer hbtDiscountType) {
        this.hbtDiscountType = hbtDiscountType;
    }

    public String getMaxLimit() {
        return maxLimit;
    }

    public void setMaxLimit(String maxLimit) {
        this.maxLimit = maxLimit;
    }

    public String getEarnest() {
        return earnest;
    }

    public void setEarnest(String earnest) {
        this.earnest = earnest;
    }

    public String getReturnRate() {
        return returnRate;
    }

    public void setReturnRate(String returnRate) {
        this.returnRate = returnRate;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getSurplus() {
        return surplus;
    }

    public void setSurplus(String surplus) {
        this.surplus = surplus;
    }

    public String getMaxQouta() {
        return maxQouta;
    }

    public void setMaxQouta(String maxQouta) {
        this.maxQouta = maxQouta;
    }

    public String getIsReward() {
        return isReward;
    }

    public void setIsReward(String isReward) {
        this.isReward = isReward;
    }

    public String getHierarchy() {
        return hierarchy;
    }

    public void setHierarchy(String hierarchy) {
        this.hierarchy = hierarchy;
    }

    public Integer getCoinType() {
        return coinType;
    }

    public void setCoinType(Integer coinType) {
        this.coinType = coinType;
    }

    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

}