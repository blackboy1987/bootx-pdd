package com.bootx.elasticsearch.service.impl;
import com.bootx.elasticsearch.service.EsBaseService;
import org.elasticsearch.client.RestHighLevelClient;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

public abstract class EsBaseServiceImpl implements EsBaseService {

    @Resource
    protected RestHighLevelClient restHighLevelClient;


    protected Long object2Long(Map<String,Object> map, String key){
        if(map.keySet().contains(key)){
            return Long.parseLong(map.get(key)+"");
        }else{
            return null;
        }
    }

    protected Integer object2Integer(Map<String,Object> map, String key){
        if(map.keySet().contains(key)){
            return Integer.parseInt(map.get(key)+"");
        }else{
            return null;
        }
    }


    protected String object2String(Map<String,Object> map, String key){
        if(map.keySet().contains(key)){
            return map.get(key)+"";
        }else{
            return null;
        }
    }


    protected Date object2Date(Map<String,Object> map, String key){
        Long time = object2Long(map,key);
        if(time==null){
            return null;
        }
        return new Date(time);
    }

    protected Boolean objet2Boolean(Map<String,Object> map, String key){
        if(map.keySet().contains(key)){
            return Boolean.getBoolean(object2String(map,key));
        }else{
            return null;
        }
    }
}