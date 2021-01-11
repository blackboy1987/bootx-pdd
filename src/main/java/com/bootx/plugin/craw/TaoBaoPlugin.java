package com.bootx.plugin.craw;

import com.bootx.entity.*;
import com.bootx.plugin.CrawlerPlugin;
import com.bootx.plugin.craw.pojo.taobao.ProductRootBean;
import com.bootx.plugin.craw.pojo.taobao.SkuItem;
import com.bootx.util.DateUtils;
import com.bootx.util.JsonUtils;
import com.bootx.util.UploadUtils;
import com.bootx.util.WebUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Component("taoBaoPlugin")
public class TaoBaoPlugin extends CrawlerPlugin {

    @Override
    public String getName() {
        return "taoBaoPlugin";
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
    public Product product(Member member,String url) {
        String html = WebUtils.get(url,null);
        if(StringUtils.isBlank(html)){
            return null;
        }
        Product product = new Product();
       try{
           Document root = Jsoup.parse(html);
           title(root,product);
           product.getProductParameterValue().setParameterValues(parameterValues(root,product));
           product.setSpecifications(specifications(member,root,product));
           productInfo(member,root,product);
           product.getProductSku().setSkus(skus(member,root,product));
       }catch (Exception e){
           e.printStackTrace();
       }
        return product;
    }

    private List<Specification> specifications(Member member,Document root,Product product) {
        Element chooseAttrs = root.getElementById("J_isku");
        List<Specification> specifications = new ArrayList<>();
        if(chooseAttrs!=null){
            Elements pChooses = chooseAttrs.getElementsByClass("J_Prop");
            for (Element pChoose:pChooses){
                Elements dt = pChoose.getElementsByTag("dt");
                Elements lis = pChoose.getElementsByTag("li");
                if(dt!=null&&dt.size()>0&&lis!=null&&lis.size()>0){
                    Specification specification = new Specification();
                    specification.setName(dt.first().text());
                    specification.setEntries(new ArrayList<>());
                    specification.setOptions(new ArrayList());
                    Integer order1 = 0;
                    for (Element item:lis) {
                        Elements as = item.getElementsByTag("a");
                        if(as!=null&&as.size()>0){
                            Element a = as.first();
                            specification.getOptions().add(a.text());
                            String style = a.attr("style");
                            if(StringUtils.isNotBlank(style)&&style.length()>"background:url(".length()){
                                style = style.substring("background:url(".length());
                                if(style.length()>") center no-repeat;".length()){
                                    style = style.substring(0,style.length()-") center no-repeat;".length());
                                    if(StringUtils.isNotBlank(style)){
                                        String extension = FilenameUtils.getExtension(style);
                                        String path = member.getUsername()+"/image/"+ DateUtils.formatDateToString(new Date(),"yyyy/MM/dd")+"/"+UUID.randomUUID().toString().replace("-","")+"."+extension;
                                        UploadUtils.upload(style,path);
                                        String url = UploadUtils.getUrl(path);
                                        specification.getEntries().add(new Specification.Entry(a.text(),item.attr("data-value"),url));
                                    }else{
                                        specification.getEntries().add(new Specification.Entry(a.text(),item.attr("data-value"),null));
                                    }
                                }else{
                                    specification.getEntries().add(new Specification.Entry(a.text(),item.attr("data-value"),null));
                                }
                            }else{
                                specification.getEntries().add(new Specification.Entry(a.text(),item.attr("data-value"),null));
                            }
                        }
                    }
                    specifications.add(specification);
                }
            }
        }
        return specifications;
    }

    private List<ParameterValue> parameterValues(Document root, Product product) {
        List<ParameterValue> parameterValues = new ArrayList<>();
        Elements elements = root.getElementsByClass("attributes-list");
        if(elements!=null){
            AtomicReference<Integer> index = new AtomicReference<>(0);
            for (Element item:elements) {
                ParameterValue parameterValue = new ParameterValue();
                parameterValue.setEntries(new ArrayList<>());
                parameterValue.setGroup("参数"+(index.getAndSet(index.get() + 1)));
                Elements elements1 = item.getElementsByTag("li");
                if(elements1!=null){
                    for (Element item1:elements1) {
                        String text = item1.text();
                        if(StringUtils.isNotBlank(text)){
                            String[] texts = text.split(":");
                            if(texts.length==2){
                                parameterValue.getEntries().add(new ParameterValue.Entry(texts[0].trim(),texts[1].trim()));
                            }
                        }
                    }
                }
                if(parameterValue.getEntries().size()>0){
                    parameterValues.add(parameterValue);
                }
            }
        }
        return parameterValues;
    }


    private void productInfo(Member member,Document root, Product product) {
        Elements elements = root.select("script");
        for (Element script:elements) {
            String data = script.data();
            data = data.replace(" ","").replace("\n","").replace("\r","").replace("\r\n","");
            if(StringUtils.startsWith(data,"varg_config")){
                data = data.split("varg_config=")[1].split(";g_config")[0].replace("+newDate","123");
                System.out.println(data);
                ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
                try{
                    engine.eval("var location={protocol: 'http'};");
                    Object eval = engine.eval("JSON.stringify(" + data + ")");
                    ProductRootBean productRootBean = JsonUtils.toObject(eval.toString(),ProductRootBean.class);
                    product.setSn(productRootBean.getItemId());
                    if(StringUtils.isBlank(product.getName())){
                        product.setName(productRootBean.getIdata().getItem().getTitle());
                    }
                    product.setProductStore(new ProductStore(productRootBean.getShopId(),productRootBean.getShopName(),productRootBean.getIdata().getShop().getUrl()));
                    productImages(member,productRootBean,product);
                    product.getProductIntroduction().setContent(introduction(member,productRootBean,product));


                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

    }

    private List<Sku> skus(Member member, Document root, Product product) {
        Map<String,String> map = new HashMap<>();

        product.getSpecifications().stream().forEach(item->{
            item.getEntries().stream().forEach(item1->map.put(item1.getValue(),item.getName()));
        });


        List<Sku> skus = new ArrayList<>();
        Elements elements = root.select("script");
        for (Element script:elements) {
            String data = script.data();
            data = data.replace(" ","").replace("\n","").replace("\r","").replace("\r\n","");
            if(StringUtils.startsWith(data,"Hub=")){
                data = data.split("skuMap:")[1].split(",propertyMemoMap")[0];
                System.out.println(data);
                ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
                try{
                    Object eval = engine.eval("JSON.stringify(" + data + ")");
                    Map<String, SkuItem> skuItemMap= JsonUtils.toObject(eval.toString(), new TypeReference<Map<String, SkuItem>>() {
                    });
                    for (String key:skuItemMap.keySet()){
                        SkuItem skuItem = skuItemMap.get(key);
                        Sku sku = new Sku();
                        sku.setSpecificationValues(new ArrayList<>());
                        sku.setSn(skuItem.getSkuId());
                        sku.setPrice(skuItem.getPrice());
                        sku.setStock(skuItem.getStock());
                        String[] keys = key.split(";");
                        int index = 0;
                        for (String item:keys) {
                            SpecificationValue specificationValue = new SpecificationValue();
                            if(StringUtils.isNotBlank(item)){
                                specificationValue.setId(item);
                                specificationValue.setName(map.get(item));
                                specificationValue.setValue(item);
                                index++;
                                sku.getSpecificationValues().add(specificationValue);
                            }
                        }
                        skus.add(sku);
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return skus;
    }

    private String introduction(Member member,ProductRootBean root, Product product) {
        ProductIntroductionImage productIntroductionImage = new ProductIntroductionImage();
        productIntroductionImage.setImages(new ArrayList<>());
        String desc = root.getDescUrl();
        String html = url(StringUtils.startsWith(desc,"http")?desc:"http:"+desc);
        html = html.substring(10,html.length()-1);
        Elements imgs = Jsoup.parse(html).getElementsByTag("img");
        if(imgs!=null){
            imgs.stream().forEach(img->{
                String url = img.attr("src");
                String extension = FilenameUtils.getExtension(url);
                String path = member.getUsername()+"/image/"+ DateUtils.formatDateToString(new Date(),"yyyy/MM/dd")+"/"+UUID.randomUUID().toString().replace("-","")+"."+extension;
                UploadUtils.upload(url,path);
                url = UploadUtils.getUrl(path);
                productIntroductionImage.getImages().add(url);
            });
        }
        productIntroductionImage.setProduct(product);
        product.setProductIntroductionImage(productIntroductionImage);
        return html;

    }


    private void productImages(Member member, ProductRootBean productRootBean, Product product) {
        AtomicReference<Integer> order = new AtomicReference<>(0);
        product.setProductImages(productRootBean.getIdata().getItem().getAuctionImages().stream().map(img->{
            ProductImage productImage = new ProductImage();

            String url = getAttribute("largeImageUrlPrefix")+img;
            String extension = FilenameUtils.getExtension(url);
            String path = member.getUsername()+"/image/"+ DateUtils.formatDateToString(new Date(),"yyyy/MM/dd")+"/"+UUID.randomUUID().toString().replace("-","")+"."+extension;
            UploadUtils.upload(url,path);
            url = UploadUtils.getUrl(path);
            productImage.setLarge(url+"?x-oss-process=style/800");
            productImage.setMedium(url+"?x-oss-process=style/480");
            productImage.setSource(url);
            productImage.setThumbnail(url+"?x-oss-process=style/65");
            productImage.setOrder(order.getAndSet(order.get() + 1));
            return productImage;
        }).collect(Collectors.toList()));
    }


    private String title(Document root, Product product) {
        Elements titleElements = root.getElementsByClass("tb-main-title");
        if(titleElements!=null&&titleElements.size()>0){
            String name = titleElements.first().text();
            System.out.println(name);
            product.setName(name);
            return name;
        }
        return "";
    }

    @Override
    public List<Product> search(String keywords) {
        PluginConfig pluginConfig = getPluginConfig();
        List<Product> products = new ArrayList<>();
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
        Map<String,Object> headers = new HashMap<>();
        headers.put("cookie","thw=cn; _fbp=fb.1.1609508506732.672226623; v=0; _tb_token_=3e77655e1f6bb; tfstk=cBEOB3AyD6fi_nMLUPQHlciYY3FAZtBtTdM9DTWKQzXfEf-AiZZu2zgl5fkAs9C..; l=eBO5vy8POd0VyR6tmOfwourza77OSIRAguPzaNbMiOCP_eC95gs5WZ8in1TpC3GVh6oJR3yUFAx0BeYBqI2wsWRKe5DDwQHmn; isg=BNzcYzJ-7xRa7Zt8VuL6nZ5XrfqOVYB_IfJsk7bd6EeqAXyL3mVQD1KzYWn5ibjX");
        headers.put("sec-fetch-mode"," navigate");
        headers.put("user-agent"," Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36 Edg/87.0.664.66");
        headers.put("host","dscnew.taobao.com");
        String result = WebUtils.get(url,null,headers);
        return result;
    }
}
