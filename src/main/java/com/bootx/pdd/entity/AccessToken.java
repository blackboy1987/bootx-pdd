package com.bootx.pdd.entity;

import com.bootx.common.BaseAttributeConverter;
import com.bootx.entity.BaseEntity;
import com.pdd.pop.sdk.http.token.AccessTokenResponse;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author black
 */
@Entity
@Table(name = "pdd_accessToken")
public class AccessToken extends BaseEntity<Long> {

    @NotEmpty
    @Column(nullable = false,updatable = false,unique = true)
    private String accessToken;

    @NotNull
    @Column(nullable = false,updatable = false)
    private Integer expiresIn;

    @NotNull
    @Column(nullable = false,updatable = false,unique = true)
    private String refreshToken;


    @NotEmpty
    @Lob
    @Convert(converter = ScopeConverter.class)
    private List<String> scope = new ArrayList<>();

    /**
     * 店铺id
     */
    @NotEmpty
    @Column(nullable = false,updatable = false,unique = true)
    private String ownerId;

    /**
     * 店铺名称
     */
    @NotEmpty
    @Column(nullable = false,updatable = false,unique = true)
    private String ownerName;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public List<String> getScope() {
        return scope;
    }

    public void setScope(List<String> scope) {
        this.scope = scope;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public void init(AccessTokenResponse accessTokenResponse){
        setAccessToken(accessTokenResponse.getAccessToken());
        setExpiresIn(accessTokenResponse.getExpiresIn());
        setRefreshToken(accessTokenResponse.getRefreshToken());
        setOwnerId(accessTokenResponse.getOwnerId());
        setOwnerName(accessTokenResponse.getOwnerName());
        setScope(accessTokenResponse.getScope());
    }

    @Converter
    public static class ScopeConverter extends BaseAttributeConverter<List<String>> {

    }
}
