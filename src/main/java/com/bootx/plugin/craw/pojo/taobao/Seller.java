
package com.bootx.plugin.craw.pojo.taobao;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Seller {

    private String id;
    private int mode;
    private String shopAge;
    private int status;
    private boolean goldSeller;
    private int goldPeriods;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public String getShopAge() {
        return shopAge;
    }

    public void setShopAge(String shopAge) {
        this.shopAge = shopAge;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isGoldSeller() {
        return goldSeller;
    }

    public void setGoldSeller(boolean goldSeller) {
        this.goldSeller = goldSeller;
    }

    public int getGoldPeriods() {
        return goldPeriods;
    }

    public void setGoldPeriods(int goldPeriods) {
        this.goldPeriods = goldPeriods;
    }
}