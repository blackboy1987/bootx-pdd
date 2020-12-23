package com.bootx.entity;

import javax.persistence.Entity;

/**
 * @author black
 */
@Entity
public class CompanyBank extends BaseEntity<Long>{

    private String bankCard;

    private String theirBank;

    private String area;

    private String name;

    private String img;

    private Boolean isEnabled;


    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
    }

    public String getTheirBank() {
        return theirBank;
    }

    public void setTheirBank(String theirBank) {
        this.theirBank = theirBank;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Boolean getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }
}
