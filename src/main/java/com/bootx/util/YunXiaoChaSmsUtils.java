package com.bootx.util;

import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author black
 */
public final class YunXiaoChaSmsUtils {

    public static void send(String[] mobiles,String content) throws UnsupportedEncodingException {
        Map<String,Object> params = new HashMap<>();
        params.put("secret","8226A146350519B335244B8F0228");
        params.put("sign","【爱购商城科技】");
        params.put("templateId","206044");
        params.put("mobile", StringUtils.join(mobiles,","));
        params.put("content",content);

        String post = WebUtils.post("https://api.1cloudsp.com/api/v2/send?accesskey=dxwblackboy1987", params);
        System.out.println(post);
    }

    public static void count() throws UnsupportedEncodingException {
        Map<String,Object> params = new HashMap<>();
        params.put("secret","8226A146350519B335244B8F0228");
        params.put("sign","【爱购商城科技】");
        params.put("templateId","64103");
        params.put("mobile","18368490194,19971579891");
        params.put("content","123456##12");

        String post = WebUtils.post("https://api.1cloudsp.com/query/account?accesskey=dxwblackboy1987", params);
        System.out.println(post);
    }



    public static void main(String[] args) throws UnsupportedEncodingException {
        send(null,null);
    }

}
