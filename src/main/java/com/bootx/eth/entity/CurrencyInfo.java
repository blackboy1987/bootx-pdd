package com.bootx.eth.entity;

import com.bootx.entity.BaseEntity;

import javax.persistence.Entity;

@Entity
public class CurrencyInfo extends BaseEntity<Long> {

    private String name;

    private String logo;

    private String logoPng;

    private String priceCny;

    private String symbol;

    private String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getLogoPng() {
        return logoPng;
    }

    public void setLogoPng(String logoPng) {
        this.logoPng = logoPng;
    }

    public String getPriceCny() {
        return priceCny;
    }

    public void setPriceCny(String priceCny) {
        this.priceCny = priceCny;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}