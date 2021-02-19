package com.bootx.util.pdd;

import com.bootx.constants.PddConfig;
import com.pdd.pop.sdk.common.util.JsonUtil;
import com.pdd.pop.sdk.http.PopBaseHttpRequest;
import com.pdd.pop.sdk.http.PopBaseHttpResponse;
import com.pdd.pop.sdk.http.PopClient;

public final class Http {

    private static final PopClient popClient = PddConfig.popClient;

    public static  <T extends PopBaseHttpResponse> T http(PopBaseHttpRequest<T> v,String accessToken) throws PddException {
        T result;
        try {
            if(accessToken==null){
                result = popClient.syncInvoke(v);
            }else{
                result = popClient.syncInvoke(v, accessToken);
            }
        }catch (Exception e){
            throw PddException.toException(e);
        }
        if(result==null){
            throw PddException.toException("接口请求失败");
        }
        if(result.getErrorResponse()!=null){
            throw PddException.toException(result.getErrorResponse());
        }
        System.out.println(JsonUtil.transferToJson(result));
        return result;
    }

}
