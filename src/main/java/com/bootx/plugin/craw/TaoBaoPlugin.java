package com.bootx.plugin.craw;

import com.bootx.entity.*;
import com.bootx.plugin.CrawlerPlugin;
import com.bootx.plugin.craw.pojo.taobao.ProductRootBean;
import com.bootx.plugin.craw.pojo.taobao.SkuItem;
import com.bootx.util.*;
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
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
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

    /**
     * 平台分类
     * @return
     */
    @Override
    public List<ProductCategory> productCategory() {
        PluginConfig pluginConfig = getPluginConfig();
        List<ProductCategory> productCategories = new ArrayList<>();
        if (pluginConfig != null) {
            String url = pluginConfig.getAttribute("category");
            try {
                Document root = Jsoup.parse(new URL(url), 5000);
                Elements homeCategoryList = root.getElementsByClass("home-category-list");
                if (homeCategoryList != null) {
                    for (Element child : homeCategoryList) {
                        Elements moduleWrap = child.getElementsByClass("module-wrap");
                        if (moduleWrap != null) {
                            Element first = moduleWrap.first();
                            Elements a = first.getElementsByTag("a");
                            ProductCategory firstProductCategory = new ProductCategory();
                            firstProductCategory.setPluginId(pluginConfig.getPluginId());
                            firstProductCategory.setParent(null);
                            firstProductCategory.setChildren(new HashSet<>());
                            if (a != null) {
                                Element firstCategory = a.first();
                                if (firstCategory != null) {
                                    firstProductCategory.setOtherUrl(firstCategory.attr("href"));
                                    firstProductCategory.setName(firstCategory.text());
                                }

                            }
                            // 二级分类
                            Elements categoryList = first.getElementsByClass("category-list");
                            if (categoryList != null) {
                                Element categoryListFirst = categoryList.first();
                                Elements categoryListItem = categoryListFirst.getElementsByClass("category-list-item");
                                for (Element categoryListItemSecond : categoryListItem) {
                                    ProductCategory secondProductCategory = new ProductCategory();
                                    secondProductCategory.setPluginId(pluginConfig.getPluginId());
                                    secondProductCategory.setParent(firstProductCategory);
                                    secondProductCategory.setChildren(new HashSet<>());
                                    Elements secondA = categoryListItemSecond.getElementsByTag("a");
                                    if (secondA != null) {
                                        secondProductCategory.setName(secondA.first().text());
                                        secondProductCategory.setOtherUrl(secondA.first().attr("href"));

                                    // 三级分类
                                    Elements thirdCategoryItems = categoryListItemSecond.getElementsByClass("category-items");
                                    if (thirdCategoryItems != null) {
                                        Element thirdCategoryItemsFirst = thirdCategoryItems.first();
                                        Elements elementsByClass = thirdCategoryItemsFirst.getElementsByClass("category-name");
                                        for (Element thirdCategoryItemsFirstElement : elementsByClass) {
                                            if(StringUtils.isNotBlank(thirdCategoryItemsFirstElement.text())){
                                                ProductCategory thirdProductCategory = new ProductCategory();
                                                thirdProductCategory.setPluginId(pluginConfig.getPluginId());
                                                thirdProductCategory.setParent(firstProductCategory);
                                                thirdProductCategory.setChildren(new HashSet<>());
                                                thirdProductCategory.setName(thirdCategoryItemsFirstElement.text());
                                                thirdProductCategory.setOtherUrl(thirdCategoryItemsFirstElement.attr("href"));

                                                String cat = CrawlerUtils.getParams(thirdProductCategory.getOtherUrl()).get("cat");
                                                if(StringUtils.isNotBlank(cat)){
                                                    thirdProductCategory.setOtherId(pluginConfig.getPluginId()+"_"+cat);
                                                }
                                                secondProductCategory.getChildren().add(thirdProductCategory);
                                            }

                                        }
                                    }
                                    }
                                    firstProductCategory.getChildren().add(secondProductCategory);
                                }
                            }
                            productCategories.add(firstProductCategory);
                        }
                    }
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return productCategories;
    }

    /**
     * 商品抓取
     * @param member
     * @param crawlerProduct
     * @return
     */
    @Override
    public CrawlerProduct product(Member member,CrawlerProduct crawlerProduct) {
        String html = WebUtils.get(crawlerProduct.getUrl(),null);
        if(StringUtils.isBlank(html)){
            return null;
        }
       try{
           Document root = Jsoup.parse(html);
           crawlerProduct.setSn(params(crawlerProduct.getUrl()).get("id"));
           title(root,crawlerProduct);
           price(root,crawlerProduct);
           crawlerProduct.getCrawlerProductParameterValue().setParameterValues(parameterValues(root,crawlerProduct));
           crawlerProduct.setCrawlerProductSpecification(specifications(member,root,crawlerProduct));
           productInfo(member,root,crawlerProduct);
           crawlerProduct.setCrawlerProductSku(skus(member,root,crawlerProduct));
       }catch (Exception e){
           e.printStackTrace();
       }
        return crawlerProduct;
    }

    public void price(Document root, CrawlerProduct crawlerProduct) {
        Elements tbRmbNum = root.getElementsByClass("tb-rmb-num");
        if(tbRmbNum!=null){
            crawlerProduct.setPrice(tbRmbNum.first().text());
        }
    }

    /**
     * 商品的规格属性
     * @param member
     * @param crawlerProduct
     * @return
     */
    public CrawlerProductSpecification specifications(Member member,Document root,CrawlerProduct crawlerProduct) {
        Element chooseAttrs = root.getElementById("J_isku");
        List<CrawlerSpecification> crawlerSpecifications = new ArrayList<>();
        if(chooseAttrs!=null){
            Elements pChooses = chooseAttrs.getElementsByClass("J_Prop");
            for (Element pChoose:pChooses){
                Elements dt = pChoose.getElementsByTag("dt");
                Elements lis = pChoose.getElementsByTag("li");

                if(dt!=null&&dt.size()>0&&lis!=null&&lis.size()>0){
                    CrawlerSpecification crawlerSpecification = new CrawlerSpecification();
                    crawlerSpecification.setName(dt.first().text());
                    crawlerSpecification.setEntries(new ArrayList<>());
                    crawlerSpecification.setOptions(new ArrayList());
                    for (Element item:lis) {
                        Elements as = item.getElementsByTag("a");
                        if(as!=null&&as.size()>0){
                            Element a = as.first();
                            crawlerSpecification.getOptions().add(a.text());
                            String style = a.attr("style");
                            if(StringUtils.isNotBlank(style)&&style.length()>"background:url(".length()){
                                style = style.substring("background:url(".length());
                                if(style.length()>") center no-repeat;".length()){
                                    style = style.substring(0,style.length()-") center no-repeat;".length());
                                    if(StringUtils.isNotBlank(style)){
                                        String extension = FilenameUtils.getName(style);
                                        String path = member.getUsername()+"/image/"+ crawlerProduct.getSn()+"/specification/"+extension;
                                        UploadUtils.upload(style,path);
                                        String url = UploadUtils.getUrl(path);
                                        crawlerSpecification.getEntries().add(new CrawlerSpecification.Entry(a.text(),item.attr("data-value"),url));
                                    }else{
                                        crawlerSpecification.getEntries().add(new CrawlerSpecification.Entry(a.text(),item.attr("data-value"),null));
                                    }
                                }else{
                                    crawlerSpecification.getEntries().add(new CrawlerSpecification.Entry(a.text(),item.attr("data-value"),null));
                                }
                            }else{
                                crawlerSpecification.getEntries().add(new CrawlerSpecification.Entry(a.text(),item.attr("data-value"),null));
                            }
                        }
                    }
                    crawlerSpecifications.add(crawlerSpecification);
                }
                crawlerProduct.getCrawlerProductSpecification().setCrawlerSpecifications(crawlerSpecifications);
            }
        }
        return crawlerProduct.getCrawlerProductSpecification();
    }

    /**
     * 商品属性
     * @param root
     * @param crawlerProduct
     * @return
     */
    public List<ParameterValue> parameterValues(Document root, CrawlerProduct crawlerProduct) {
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


    /**
     * 解析商品的信息
     * @param member
     * @param root
     * @param crawlerProduct
     */
    private void productInfo(Member member,Document root, CrawlerProduct crawlerProduct) {
        Elements elements = root.select("script");
        for (Element script:elements) {
            String data = script.data();
            data = data.replace(" ","").replace("\n","").replace("\r","").replace("\r\n","");
            if(StringUtils.startsWith(data,"varg_config")){
                data = data.split("varg_config=")[1].split(";g_config")[0].replace("+newDate","123");
                ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
                try{
                    engine.eval("var location={protocol: 'http'};");
                    Object eval = engine.eval("JSON.stringify(" + data + ")");
                    ProductRootBean productRootBean = JsonUtils.toObject(eval.toString(),ProductRootBean.class);
                    crawlerProduct.setSn(productRootBean.getItemId());
                    if(StringUtils.isBlank(crawlerProduct.getName())){
                        crawlerProduct.setName(productRootBean.getIdata().getItem().getTitle());
                    }
                    crawlerProduct.setCrawlerProductStore(new CrawlerProductStore(productRootBean.getShopId(),productRootBean.getShopName(),productRootBean.getIdata().getShop().getUrl()));
                    productImages(member,productRootBean,crawlerProduct);
                    crawlerProduct.getCrawlerProductIntroduction().setContent(introduction(member,productRootBean,crawlerProduct));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * 解析商品的sku信息
     * @param member
     * @param crawlerProduct
     * @return
     */
    private CrawlerProductSku skus(Member member, Document root, CrawlerProduct crawlerProduct) {
        Map<String,String> map = new HashMap<>();
        crawlerProduct.getCrawlerProductSku().setSkus(new ArrayList<>());
        crawlerProduct.getCrawlerProductSpecification().getCrawlerSpecifications().stream().forEach(item->{
            item.getEntries().stream().forEach(item1->map.put(item1.getValue(),item1.getName()+"_"+item.getName()));
        });
        Elements elements = root.select("script");
        for (Element script:elements) {
            String data = script.data();
            data = data.replace(" ","").replace("\n","").replace("\r","").replace("\r\n","");
            if(StringUtils.startsWith(data,"Hub=")){
                data = data.split("skuMap:")[1].split(",propertyMemoMap")[0];
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
                                String s = map.get(item);
                                specificationValue.setValue(s.split("_")[0]);
                                specificationValue.setName(s.split("_")[1]);
                                specificationValue.setValue(s.split("_")[0]);
                                index++;
                                sku.getSpecificationValues().add(specificationValue);
                            }
                        }
                        crawlerProduct.getCrawlerProductSku().getSkus().add(sku);
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return crawlerProduct.getCrawlerProductSku();
    }

    /**
     * 解析商品的介绍
     * @param member
     * @param crawlerProduct
     * @return
     */
    private String introduction(Member member,ProductRootBean root, CrawlerProduct crawlerProduct) {
        String desc = root.getDescUrl();
        String html = url(StringUtils.startsWith(desc,"http")?desc:"http:"+desc);
        html = html.substring(10,html.length()-1);
        Elements imgs = Jsoup.parse(html).getElementsByTag("img");
        if(imgs!=null){
            crawlerProduct.getCrawlerProductIntroductionImage().setImages(imgs.stream().map(img->{
                String url = img.attr("src");
                String extension = FilenameUtils.getName(url);
                String path = member.getUsername()+"/image/"+ crawlerProduct.getSn()+"/introduction/"+extension;
                UploadUtils.upload(url,path);
               return UploadUtils.getUrl(path);
            }).collect(Collectors.toList()));
        }
        return html;
    }


    /**
     * 解析商品主图
     * @param member
     * @param crawlerProduct
     * @return
     */
    private void productImages(Member member, ProductRootBean productRootBean, CrawlerProduct crawlerProduct) {

        List<String> images = productRootBean.getIdata().getItem().getAuctionImages();
        crawlerProduct.getCrawlerProductImage().setImages(images.stream().map(image->{
            String extension = FilenameUtils.getName(image);
            String path = member.getUsername()+"/image/"+ crawlerProduct.getSn() +"/images/" +extension;
            UploadUtils.upload(image,path);
            return UploadUtils.getUrl(path);
        }).collect(Collectors.toList()));
    }

    /**
     * 商品标题
     * @param root
     * @param crawlerProduct
     * @return
     */
    public String title(Document root, CrawlerProduct crawlerProduct) {
        Elements titleElements = root.getElementsByClass("tb-main-title");
        if(titleElements!=null&&titleElements.size()>0){
            String name = titleElements.first().text();
            crawlerProduct.setName(name);
            return name;
        }
        return "";
    }

    /**
     * 平台查询
     * @param keywords
     * @return
     */
    @Override
    public List<CrawlerProduct> search(String keywords) {
        PluginConfig pluginConfig = getPluginConfig();
        List<CrawlerProduct> crawlerProducts = new ArrayList<>();
        if (pluginConfig != null) {

        }
        return crawlerProducts;
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
        headers.put("cookie","thw=cn; _fbp=fb.1.1609508506732.672226623; v=0; _tb_token_=3e77655e1f6bb; tfstk=cBEOB3AyD6fi_nMLUPQHlciYY3FAZtBtTdM9DTWKQzXfEf-AiZZu2zgl5fkAs9C..; l=eBO5vy8POd0VyR6tmOfwourza77OSIRAguPzaNbMiOCP_eC95gs5WZ8in1TpC3GVh6oJR3yUFAx0BeYBqI2wsWRKe5DDwQHmn; isg=BNzcYzJ-7xRa7Zt8VuL6nZ5XrfqOVYB_IfJsk7bd6EeqAXyL3mVQD1KzYWn5ibjX");
        headers.put("sec-fetch-mode"," navigate");
        headers.put("user-agent"," Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36 Edg/87.0.664.66");
        headers.put("host","dscnew.taobao.com");
        String result = WebUtils.get(url,null,headers);
        return result;
    }
}
