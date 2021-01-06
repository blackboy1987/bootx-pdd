package com.bootx.pdd.service;

import com.bootx.pdd.entity.AccessToken;
import com.bootx.service.BaseService;
import com.pdd.pop.sdk.http.token.AccessTokenResponse;

/**
 * @author blackoy
 */
public interface AccessTokenService extends BaseService<AccessToken,Long>{

    AccessToken findByOwnerId(String ownerId);

    AccessToken findByOwnerName(String ownerName);

    AccessToken create(AccessTokenResponse response);
}
