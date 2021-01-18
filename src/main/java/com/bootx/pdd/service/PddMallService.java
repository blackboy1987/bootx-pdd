package com.bootx.pdd.service;

import com.pdd.pop.sdk.http.api.pop.response.PddMallCpsProtocolStatusQueryResponse;
import com.pdd.pop.sdk.http.api.pop.response.PddMallInfoGetResponse;

public interface PddMallService extends PddBaseService{

    PddMallInfoGetResponse pddMallInfoGet(String accessToken) throws Exception;

    PddMallCpsProtocolStatusQueryResponse pddMallCpsProtocolStatusQuery(String accessToken) throws Exception;
}
