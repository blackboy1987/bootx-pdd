package com.bootx.constants;

import com.pdd.pop.sdk.http.PopClient;
import com.pdd.pop.sdk.http.PopHttpClient;

/**
 * @author blackoy
 */
public final class PddConfig {

    public static final String clientId = "e5945f4dc54f4ee58d524979658e2b2c";
    public static final String clientSecret = "944ae8f7d5e64b1bafc80c0ae6abbbcab6d9a1fb";

    public static final PopClient popClient = new PopHttpClient(PddConfig.clientId,PddConfig.clientSecret);

    // https://fuwu.pinduoduo.com/service-market/auth?response_type=code&client_id=e5945f4dc54f4ee58d524979658e2b2c&redirect_uri=http://115.215.185.25/auth
}
