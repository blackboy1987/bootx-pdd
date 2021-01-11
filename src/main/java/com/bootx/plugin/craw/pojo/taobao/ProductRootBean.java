
package com.bootx.plugin.craw.pojo.taobao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductRootBean implements Serializable {

    private int shopVer;
    private String itemId;
    private String shopId;
    private String shopName;
    private String sellerId;
    private String sellerNick;
    private String descUrl;
    private Idata idata;


    public int getShopVer() {
        return shopVer;
    }

    public void setShopVer(int shopVer) {
        this.shopVer = shopVer;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getSellerNick() {
        return sellerNick;
    }

    public void setSellerNick(String sellerNick) {
        this.sellerNick = sellerNick;
    }

    public String getDescUrl() {
        return descUrl;
    }

    public void setDescUrl(String descUrl) {
        this.descUrl = descUrl;
    }

    public Idata getIdata() {
        return idata;
    }

    public void setIdata(Idata idata) {
        this.idata = idata;
    }
}