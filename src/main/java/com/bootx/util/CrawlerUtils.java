package com.bootx.util;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public final class CrawlerUtils {

    public static String getPlugInId(String url){
        if(StringUtils.contains(url,"taobao.com")){
           return "taoBaoPlugin";
        }else if(StringUtils.contains(url,"1688.com")){
           return "oneSixEightEightPlugin";
        }else if(StringUtils.contains(url,"jd.com")){
           return "jdPlugin";
        }else if(StringUtils.contains(url,"suning.com")){
            return "suningPlugin";
        }else if(StringUtils.contains(url,"tmall.com")){
            return "tMallPlugin";
        }
        return null;
    }


    public static Map<String,String> getParams(String url){
        Map<String,String> data = new HashMap<>();
        int i = url.indexOf("?");
        if(i>-1){
            url = url.substring(i+1);
            String[] urls = StringUtils.split(url, "&");
            for (String str:urls) {
                String[] params = str.split("=");
                if(params.length==2){
                    data.put(params[0],params[1]);
                }
            }
        }
        System.out.println(data);
        return data;
    }

    public static void main(String[] args) {
        String url = "https://s.taobao.com/list?spm=a21bo.7723600.8560.86.713a5ec99E5xlz&q=%E6%8B%BC%E6%8F%92%E7%9B%8A%E6%99%BA&style=grid&seller_type=taobao&cps=yes&s=0&cat=50005998";
        getParams(url);
    }
}
