package com.bootx.pdd.service.impl;

import com.bootx.constants.PddConfig;
import com.bootx.pdd.service.PddService;
import com.pdd.pop.sdk.http.PopClient;
import com.pdd.pop.sdk.http.PopHttpClient;
import org.springframework.stereotype.Service;

/**
 * @author blackoy
 */
@Service
public class PddServiceImpl extends PddBaseServiceImpl implements PddService {

    public static final PopClient POPHTTPCLIENT = new PopHttpClient(PddConfig.clientId,PddConfig.clientSecret);




}
