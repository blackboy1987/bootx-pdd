package com.bootx;

import com.bootx.plugin.craw.pojo.jd.JsonRootBean;
import com.bootx.util.JsonUtils;
import com.bootx.util.WebUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.HashMap;
import java.util.Map;

public final class Demo {

    public static void main(String[] args) {
        String str = url("https://item.jd.com/10021815017958.html");
        Document root = Jsoup.parse(str);
        Elements elements = root.select("script[charset='gbk']");
        if(elements!=null&&elements.size()>0){
            String data = elements.first().data();
            System.out.println(data);
            data = data.replace(" ","").replace("\n","").replace("\r","").replace("\r\n","");
            System.out.println(data);
            data = data.split("varpageConfig=")[1].split(";try")[0];
            System.out.println(data);
            ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
            try{
                Object eval = engine.eval("JSON.stringify(" + data + ")");
                try {
                    JsonRootBean jsonRootBean = JsonUtils.toObject(eval.toString(),JsonRootBean.class);
                    System.out.println(jsonRootBean.getProduct());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    public static String url(String url) {
        if(!StringUtils.startsWith(url,"http:")&&!StringUtils.startsWith(url,"https:")){
            url = "http:"+url;
        }
        Map<String,Object> headers = new HashMap<>();
        headers.put("cookie","__jdu=16076610028271629515156; shshshfpa=847975b7-73a2-0529-8e61-90c57103ff98-1607661005; shshshfpb=o3sXhcIu8RMjLqwU4W7D%20vQ%3D%3D; user-key=6eeed015-6181-4e48-b4be-e90b01130beb; cn=0; unpl=V2_ZzNtbREDQhMmCUNWexEMUWIKEV8SAhQRcw5FXStMWQ1lVxtYclRCFnUUR1RnGFsUZwYZWURcQxVFCEdkeB5fA2AFEFlBZxpFK0oYEDlNDEY1WnxZRldAFXEIQFx8KWwGZzMSXHJXRBRzD0FUehhfDGQFElpBX0cVcQhCU0spWzVXMxJfQ1JLHUUJdlVLWwhZYgIRXkJeDhVyCUBTfBldBGQKEVtCUEAdcQhCVH8ebARXAA%3d%3d; areaId=15; __jdv=76161171|direct|-|none|-|1611323641292; __jda=122270672.16076610028271629515156.1607661002.1610698375.1611323641.20; __jdc=122270672; shshshfp=8f1072e164f7efede3844f10c5a49ba4; ipLoc-djd=15-1158-46341-46352; 3AB9D23F7A4B3C9B=L6MCANK2GWKFO6RYAP4R74OMKOAPS42ULYCP2JNRIOTAJXA35BIY32CCJVWHNWW2PRUU4SR5OO3B7LXU7E7UOKWK6E; shshshsID=ce77f11fb82b0effff432790cd29f1a2_9_1611324032907; __jdb=122270672.12.16076610028271629515156|20.1611323641");
        headers.put("sec-fetch-mode"," navigate");
        headers.put("user-agent"," Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36 Edg/87.0.664.66");
        String result = WebUtils.get(url,null,headers);
        return result;
    }
}