
package com.bootx.plugin.craw.pojo.taobao;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Item implements Serializable {

    private String id;
    private String title;
    private String pic;
    private String price;
    private int status;
    private String sellerNick;
    private String sellerNickGBK;
    private String skuComponentFirst;
    private String rcid;
    private String cid;
    private String sizeGroupName;
    private List<String> auctionImages = new ArrayList<>();


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSellerNick() {
        return sellerNick;
    }

    public void setSellerNick(String sellerNick) {
        this.sellerNick = sellerNick;
    }

    public String getSellerNickGBK() {
        return sellerNickGBK;
    }

    public void setSellerNickGBK(String sellerNickGBK) {
        this.sellerNickGBK = sellerNickGBK;
    }

    public String getSkuComponentFirst() {
        return skuComponentFirst;
    }

    public void setSkuComponentFirst(String skuComponentFirst) {
        this.skuComponentFirst = skuComponentFirst;
    }

    public String getRcid() {
        return rcid;
    }

    public void setRcid(String rcid) {
        this.rcid = rcid;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getSizeGroupName() {
        return sizeGroupName;
    }

    public void setSizeGroupName(String sizeGroupName) {
        this.sizeGroupName = sizeGroupName;
    }

    public List<String> getAuctionImages() {
        return auctionImages;
    }

    public void setAuctionImages(List<String> auctionImages) {
        this.auctionImages = auctionImages;
    }
}