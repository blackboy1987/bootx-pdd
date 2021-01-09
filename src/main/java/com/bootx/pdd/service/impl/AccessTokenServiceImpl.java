package com.bootx.pdd.service.impl;

import com.bootx.entity.Store;
import com.bootx.pdd.dao.AccessTokenDao;
import com.bootx.pdd.entity.AccessToken;
import com.bootx.pdd.service.AccessTokenService;
import com.bootx.service.StoreService;
import com.bootx.service.impl.BaseServiceImpl;
import com.pdd.pop.sdk.http.token.AccessTokenResponse;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author blackoy
 */
@Service
public class AccessTokenServiceImpl extends BaseServiceImpl<AccessToken,Long> implements AccessTokenService {

    @Resource
    private AccessTokenDao accessTokenDao;
    @Resource
    private StoreService storeService;

    @Override
    public AccessToken findByOwnerId(String ownerId) {
        return accessTokenDao.find("ownerId",ownerId);
    }

    @Override
    public AccessToken findByOwnerName(String ownerName) {
        return accessTokenDao.find("ownerName",ownerName);
    }

    @Override
    public AccessToken create(AccessTokenResponse response) {
        if(response.getErrorResponse()!=null){
            return null;
        }
        AccessToken accessToken = new AccessToken();
        accessToken.init(response);
        Store store = storeService.findByMallId(Long.valueOf(accessToken.getOwnerId()));
        if(store==null){
            try {
                storeService.create(response.getOwnerId(),response.getOwnerName());
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return super.save(accessToken);
    }
}
