package com.bootx.pdd.service.impl;

import com.bootx.pdd.service.PddMallService;
import com.bootx.util.JsonUtils;
import com.pdd.pop.sdk.http.api.pop.request.PddMallCpsProtocolStatusQueryRequest;
import com.pdd.pop.sdk.http.api.pop.request.PddMallInfoGetRequest;
import com.pdd.pop.sdk.http.api.pop.response.PddMallCpsProtocolStatusQueryResponse;
import com.pdd.pop.sdk.http.api.pop.response.PddMallInfoGetResponse;
import org.springframework.stereotype.Service;

@Service
public class PddMallServiceImpl extends PddBaseServiceImpl implements PddMallService {

    @Override
    public PddMallInfoGetResponse pddMallInfoGet(String accessToken) throws Exception {

        PddMallInfoGetRequest request = new PddMallInfoGetRequest();
        PddMallInfoGetResponse response = popClient.syncInvoke(request, accessToken);
        System.out.println(JsonUtils.toJson(response));
        return response;
    }

    @Override
    public PddMallCpsProtocolStatusQueryResponse pddMallCpsProtocolStatusQuery(String accessToken) throws Exception {

        PddMallCpsProtocolStatusQueryRequest request = new PddMallCpsProtocolStatusQueryRequest();
        PddMallCpsProtocolStatusQueryResponse response = popClient.syncInvoke(request, accessToken);
        System.out.println(JsonUtils.toJson(response));
        return response;
    }


}
