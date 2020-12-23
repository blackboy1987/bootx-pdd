package com.bootx.util;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author black
 */
public final class YunXiaoChaSmsUtils {

    public static void send(String mobile,String content) throws UnsupportedEncodingException {
        /**
         * http://api.1cloudsp.com/api/v2/single_send?accesskey=dxwblackboy1987&secret=8226A146350519B335244B8F0228&sign=【爱购商城科技】&templateId=64103&mobile=19971579891&content=123456
         */


        Map<String,Object> params = new HashMap<>();
        params.put("secret","8226A146350519B335244B8F0228");
        params.put("sign","【爱购商城科技】");
        params.put("templateId","64103");
        params.put("mobile","18368490194,19971579891");
        params.put("content","34567");

        String post = WebUtils.post("http://api.1cloudsp.com/api/v2/send?accesskey=dxwblackboy1987", params);
        System.out.println(post);
    }




    public static void main(String[] args) throws UnsupportedEncodingException {
        send("19971579891","123456");
    }

}
