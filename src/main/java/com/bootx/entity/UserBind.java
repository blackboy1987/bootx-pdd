package com.bootx.entity;

import javax.persistence.Entity;

/**
 * @author black
 */
@Entity
public class UserBind extends BaseEntity<Long>{

    private String uuid;

    private String mobile;

    private Long userId;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
