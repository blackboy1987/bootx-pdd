package com.bootx.util;

import org.apache.commons.lang3.StringUtils;

public final class CrawlerUtils {

    public static String getPlugInId(String url){
        if(StringUtils.contains(url,"taobao.com")){
           return "taobaoPlugin";
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

}
