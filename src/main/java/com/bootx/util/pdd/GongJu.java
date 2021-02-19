package com.bootx.util.pdd;

import com.pdd.pop.sdk.common.util.JsonUtil;
import com.pdd.pop.sdk.http.PopClient;
import com.pdd.pop.sdk.http.api.pop.request.PddPopAuthTokenCreateRequest;
import com.pdd.pop.sdk.http.api.pop.request.PddPopAuthTokenRefreshRequest;
import com.pdd.pop.sdk.http.api.pop.request.PddTimeGetRequest;
import com.pdd.pop.sdk.http.api.pop.response.PddPopAuthTokenCreateResponse;
import com.pdd.pop.sdk.http.api.pop.response.PddPopAuthTokenRefreshResponse;
import com.pdd.pop.sdk.http.api.pop.response.PddTimeGetResponse;

/**
 * 工具API
 */
public final class GongJu {

    /**
     * pdd.pop.auth.token.create获取Access Token
     */
    public static PddPopAuthTokenCreateResponse.PopAuthTokenCreateResponse popAuthTokenCreate(String code,PopClient popClient) throws PddException {
        PddPopAuthTokenCreateRequest request = new PddPopAuthTokenCreateRequest();
        request.setCode(code);

        PddPopAuthTokenCreateResponse response;
        try {
            response = popClient.syncInvoke(request);
        }catch (Exception e){
            throw PddException.toException(e);
        }
        if(response==null){
            throw PddException.toException("接口请求失败");
        }
        if(response.getErrorResponse()!=null){
            throw PddException.toException(response.getErrorResponse());
        }

        System.out.println(JsonUtil.transferToJson(response));
        return response.getPopAuthTokenCreateResponse();
    }

    /**
     * pdd.pop.auth.token.refresh刷新Access Token
     */
    public static PddPopAuthTokenRefreshResponse.PopAuthTokenRefreshResponse popAuthTokenRefresh(String refreshToken,PopClient popClient) throws PddException {
        PddPopAuthTokenRefreshRequest request = new PddPopAuthTokenRefreshRequest();
        request.setRefreshToken(refreshToken);
        PddPopAuthTokenRefreshResponse response;
        try{
            response = popClient.syncInvoke(request);
            System.out.println(JsonUtil.transferToJson(response));
        }catch (Exception e){
            throw PddException.toException(e);
        }
        if(response==null){
            throw PddException.toException("接口请求失败");
        }
        if(response.getErrorResponse()!=null){
            throw PddException.toException(response.getErrorResponse());
        }
        return response.getPopAuthTokenRefreshResponse();
    }

    /**
     * pdd.time.get获取拼多多系统时间
     */
    public static String timeGet() throws PddException {
        PddTimeGetRequest request = new PddTimeGetRequest();
        PddTimeGetResponse response = Http.http(request,null);

        return response.getTimeGetResponse().getTime();
    }
}
