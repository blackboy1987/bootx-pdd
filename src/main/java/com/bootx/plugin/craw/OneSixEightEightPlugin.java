package com.bootx.plugin.craw;

import com.bootx.entity.*;
import com.bootx.plugin.CrawlerPlugin;
import com.bootx.plugin.craw.pojo.oneSixEightEightPlugin.JsonRootBean;
import com.bootx.plugin.craw.pojo.oneSixEightEightPlugin.ModDetailAttribute;
import com.bootx.plugin.craw.pojo.oneSixEightEightPlugin.SkuProps;
import com.bootx.util.JsonUtils;
import com.bootx.util.WebUtils;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component("oneSixEightEightPlugin")
public class OneSixEightEightPlugin extends CrawlerPlugin {

    private static JsonRootBean jsonRootBean = null;



    @Override
    public String getName() {
        return "1688";
    }

    @Override
    public String getVersion() {
        return "1.0";
    }

    @Override
    public String getAuthor() {
        return "SHOP++";
    }

    @Override
    public String getSiteUrl() {
        return "http://www.bootx.net";
    }

    @Override
    public String getInstallUrl() {
        return "ftp_storage/install";
    }

    @Override
    public String getUninstallUrl() {
        return "ftp_storage/uninstall";
    }

    @Override
    public String getSettingUrl() {
        return "ftp_storage/setting";
    }

    @Override
    public List<ProductCategory> productCategory() {
        return null;
    }

    @Override
    public CrawlerProduct product(Member member, CrawlerProduct crawlerProduct) {
       try{
           Document root = Jsoup.parse(url(crawlerProduct.getUrl()));
           JsonRootBean jsonRootBean = jsonRootBean(root);
           crawlerProduct.setSn(params(crawlerProduct.getUrl()).get("object_id"));
           crawlerProduct.setName(title(root,crawlerProduct));
           crawlerProduct.setPrice(jsonRootBean.getPrice());
           productImages(member,root,crawlerProduct);
           specifications(member,jsonRootBean,crawlerProduct);
           parameterValues(member,root,crawlerProduct);
           introduction(member,root,crawlerProduct);
           skus(member,jsonRootBean,crawlerProduct);
       }catch (Exception e){
           e.printStackTrace();
       }
        return crawlerProduct;
    }

    /**
     * 解析标题
     * @param root
     * @param product
     * @return
     */
    public String title(Document root, CrawlerProduct product) {
        Element titleElement = root.getElementById("mod-detail-title");
        try {
            String name = titleElement.getElementsByTag("h1").text();
            return name;
        }catch (Exception e){
            e.printStackTrace();
            return "无标题";
        }
    }

    /**
     * 解析主图
     * @param root
     * @return
     */
    private void productImages(Member member, Document root,CrawlerProduct crawlerProduct) {
        List<String> images = new ArrayList<>();
        Element dtTab = root.getElementById("dt-tab");
        Elements tabTrigger = dtTab.getElementsByClass("tab-trigger");
        for (Element e:tabTrigger){
            String dataImgs = e.attr("data-imgs");
            if(StringUtils.isNotBlank(dataImgs)){
                Image image = JsonUtils.toObject(dataImgs,Image.class);
                images.add(image.getOriginal());
            }
        }
        crawlerProduct.getCrawlerProductImage().setImages(images);
    }
    /**
     * 解析规格
     * @return
     */
    public CrawlerProductSpecification specifications(Member member, JsonRootBean jsonRootBean,CrawlerProduct crawlerProduct) {
        CrawlerProductSpecification crawlerProductSpecification = crawlerProduct.getCrawlerProductSpecification();
        crawlerProductSpecification.setCrawlerSpecifications(new ArrayList<>());
        List<SkuProps> skuPropses = jsonRootBean.getSkuProps();
        for (SkuProps skuProps:skuPropses) {
            CrawlerSpecification crawlerSpecification = new CrawlerSpecification();
            crawlerSpecification.setName(skuProps.getProp());
            crawlerSpecification.setOptions(skuProps.getValue().stream().map(value->value.getName()).collect(Collectors.toList()));
            crawlerSpecification.setEntries(skuProps.getValue().stream().map(value-> new CrawlerSpecification.Entry(value.getName(),value.getName(),value.getImageUrl())).collect(Collectors.toList()));
            crawlerProductSpecification.getCrawlerSpecifications().add(crawlerSpecification);
        }
        return crawlerProductSpecification;
    }

    /**
     * 商品属性
     * @param root
     * @param crawlerProduct
     * @return
     */
    public CrawlerProductParameterValue parameterValues(Member member,Document root, CrawlerProduct crawlerProduct) {
        CrawlerProductParameterValue crawlerProductParameterValue = crawlerProduct.getCrawlerProductParameterValue();
        crawlerProductParameterValue.setParameterValues(new ArrayList<>());
        Element modDetailAttribute = root.getElementById("mod-detail-attributes");

        String attr = modDetailAttribute.attr("data-feature-json");
        List<ModDetailAttribute> modDetailAttributes = JsonUtils.toObject(attr, new TypeReference<List<ModDetailAttribute>>() {
        });

        ParameterValue parameterValue = new ParameterValue();
        parameterValue.setGroup("属性_"+ crawlerProduct.getId());
        parameterValue.setEntries(new ArrayList<>());
        modDetailAttributes.stream().forEach(item->{
            parameterValue.getEntries().add(new ParameterValue.Entry(item.getName(), item.getValue()));

        });
        crawlerProductParameterValue.getParameterValues().add(parameterValue);
        crawlerProduct.setCrawlerProductParameterValue(crawlerProductParameterValue);
        return crawlerProduct.getCrawlerProductParameterValue();
    }

    /**
     * 解析详情和详情图
     * @param root
     * @return
     */
    private String introduction(Member member,Document root, CrawlerProduct crawlerProduct) {
        Element deDescriptionDetail = root.getElementById("desc-lazyload-container");
        String url = deDescriptionDetail.attr("data-tfs-url");
        String result = url(url);
        String s = result.split("offer_details=")[1];
        s = s.substring(0,s.length()-1);
        Map<String, String> stringStringMap = JsonUtils.toObject(s, new TypeReference<Map<String, String>>() {
        });
        Elements imgs = Jsoup.parse(stringStringMap.get("content")).getElementsByTag("img");
        if(imgs!=null){
            crawlerProduct.getCrawlerProductIntroductionImage().setImages(imgs.stream().map(img->img.attr("src")).collect(Collectors.toList()));
        }
        return Jsoup.parse(result).text();
    }

    public CrawlerProductSku skus(Member member, JsonRootBean root, CrawlerProduct crawlerProduct) {
        CrawlerProductSku crawlerProductSku = crawlerProduct.getCrawlerProductSku();
        crawlerProductSku.setSkus(new ArrayList<>());
        Map<String, com.bootx.plugin.craw.pojo.oneSixEightEightPlugin.Sku> skuMap = root.getSkuMap();
        for (String key:skuMap.keySet()) {
            com.bootx.plugin.craw.pojo.oneSixEightEightPlugin.Sku sku1 = skuMap.get(key);
            Sku sku = new Sku();
            sku.setStock(sku1.getCanBookCount());
            sku.setPrice(new BigDecimal(sku1.getDiscountPrice()));
            sku.setSn(sku1.getSkuId());
            sku.setSpecificationValues(sku1.getSpecAttrs().stream().map(item->{
                SpecificationValue specificationValue = new SpecificationValue();
                specificationValue.setValue(item.getValue());
                specificationValue.setName(item.getName());
                specificationValue.setId(item.getValueId()+"");
                return specificationValue;
            }).collect(Collectors.toList()));

            crawlerProductSku.getSkus().add(sku);
        }
        return crawlerProductSku;
    }


    private JsonRootBean jsonRootBean(Document root) {
        Elements scripts = root.getElementsByTag("script");
        for (Element script:scripts) {
            String data = script.data();
            if(StringUtils.contains(data,"var iDetailData")){
                data = data.replace(" ","").replace("\n","").replace("\r","").replace("\r\n","");
                data = data.split("variDetailData=\\{sku:")[1].split(";iDetailData.allTagIds=")[0];
                ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
                try{
                    Object eval = engine.eval("JSON.stringify(" + data.substring(0,data.length()-9).replaceAll("&gt;",">") + ")");
                    JsonRootBean jsonRootBean = JsonUtils.toObject(eval.toString(),JsonRootBean.class);
                    return jsonRootBean;
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            }
        }

        return null;
    }


    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Image implements Serializable {
        private String preview;

        private String original;

        public String getPreview() {
            return preview;
        }

        public void setPreview(String preview) {
            this.preview = preview;
        }

        public String getOriginal() {
            return original;
        }

        public void setOriginal(String original) {
            this.original = original;
        }
    }

    @Override
    public List<CrawlerProduct> search(String keywords) {
        PluginConfig pluginConfig = getPluginConfig();
        List<CrawlerProduct> products = new ArrayList<>();
        if (pluginConfig != null) {

        }
        return products;
    }

    @Override
    public String getUrl(String path) {
        PluginConfig pluginConfig = getPluginConfig();
        if (pluginConfig != null) {
            String urlPrefix = pluginConfig.getAttribute("urlPrefix");
            return urlPrefix + path;
        }
        return null;
    }

    public static String url(String url) {
        if(!StringUtils.startsWith(url,"http:")&&!StringUtils.startsWith(url,"https:")){
            url = "http:"+url;
        }
        Map<String,Object> headers = new HashMap<>();
        headers.put("cookie","cna=lbpTGObSAmoCAdy93EFF48LR; lid=%E5%93%91%E9%97%A82010; enc=p%2Fiq7zI9tMKGZHcTQClrIxyfjw7hSQ7bu9e4ukOht3%2BvJiWPCpRz4oJpHZf6yqe%2BfhrBz7jBy0PrSGaaWH4OQw%3D%3D; sm4=330200; isg=BNjYeK6EM0SYDB_jhk_4hPAdqQZqwTxLvS7oHxLIVJPGrXqXuteu26fM4eWdvfQj; tfstk=c6RdBnq4wcmHWc_9upHgFeORpE6daVgdby_Lwquk8_GgDJmIvsDBiIpfnvsFyD0O.; l=eBSC51rIOVS5zBsYBO5alurza7796IObzsPzaNbMiInca1rFMFsk0NQ2-c6JydtjgtCUWetzL8MMmdeBkM4dNxDDBeXbbEpiqxvO.; hng=CN%7Czh-CN%7CCNY%7C156");
        headers.put("sec-fetch-mode","navigate");
        headers.put("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.141 Safari/537.36 Edg/87.0.664.75");
        String result = WebUtils.get(url,null,headers);
        return result;
    }
}
