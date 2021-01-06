package com.bootx.plugin.craw;

import com.bootx.entity.*;
import com.bootx.plugin.CrawlerPlugin;
import com.bootx.plugin.craw.pojo.tMall.JsonRootBean;
import com.bootx.plugin.craw.pojo.tMall.Sku;
import com.bootx.util.JsonUtils;
import com.bootx.util.WebUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Component("tMallPlugin")
public class TMallPlugin extends CrawlerPlugin {

    @Override
    public String getName() {
        return "天猫";
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
    public Product product(String url) {
        Product product = new Product();
       try{
           Document root = Jsoup.parse(url(url));
           title(root,product);
           product.setSpecifications(getSpecifications(root,product));
           more(root,product);
       }catch (Exception e){
           e.printStackTrace();
       }
        return product;
    }

    private List<Specification> getSpecifications(Document root, Product product) {
        List<Specification> specifications = new ArrayList<>();
        Elements tbSkus = root.getElementsByClass("tb-sku");
        if(tbSkus!=null&&tbSkus.size()>0){
            Element tbSku = tbSkus.first();
            Elements tmSaleProps = tbSku.getElementsByClass("tm-sale-prop");
            for (Element tmSaleProp:tmSaleProps) {
                Elements dts = tmSaleProp.getElementsByTag("dt");
                Elements dds = tmSaleProp.getElementsByTag("dd");
                if(dts==null||dts.size()==0||dds==null||dds.size()==0){
                    continue;
                }
                Elements lis = dds.first().getElementsByTag("li");
                if(lis==null||lis.size()==0){
                    continue;
                }
                Specification specification = new Specification();
                specification.setEntries(new ArrayList<>());
                specification.setOptions(new ArrayList<>());
                specification.setName(dts.first().text());

                lis.stream().forEach(item->{
                    String name = item.text();
                    String value = item.attr("data-value");
                    specification.getEntries().add(new Specification.Entry(name,value,null));
                    specification.getOptions().add(name);
                });
                specifications.add(specification);
            }
        }
        return specifications;
    }

    private void more(Document root, Product product) {
        Elements scripts = root.getElementsByTag("script");
        for (Element script:scripts) {
            String data = script.data();
            if(StringUtils.contains(data,"TShop.Setup")){
                data = data.replace(" ","").replace("\n","").replace("\r","").replace("\r\n","") .replaceAll("\\s*", "").replaceAll(" +","");
                System.out.println(data);
                data = data.split("TShop.Setup")[1];
                data = data.substring(1);
                data = data.substring(0,data.length()-7);
                JsonRootBean jsonRootBean = JsonUtils.toObject(data, JsonRootBean.class);
                product.setPrice(jsonRootBean.getItemDO().getReservePrice());
                product.setName(jsonRootBean.getItemDO().getTitle());
                product.setStock(jsonRootBean.getItemDO().getQuantity());
                product.setProductImages(productImages(jsonRootBean,product));
                product.setSkus(skus(jsonRootBean,product));
                break;
            }
        }
    }

    private List<ProductImage> productImages(JsonRootBean jsonRootBean, Product product) {
        List<ProductImage> productImages = new ArrayList<>();
        Map<String, List<String>> propertyPics = jsonRootBean.getPropertyPics();
        List<String> images = propertyPics.get("default");
        if(images!=null&&images.size()>0){
            AtomicReference<Integer> order = new AtomicReference<>(0);
            images.forEach(img->productImages.add(new ProductImage(img,img,img,img, order.getAndSet(order.get() + 1))));
        }
        product.setProductImages(productImages);

        return productImages;
    }

    private Set<com.bootx.entity.Sku> skus(JsonRootBean jsonRootBean, Product product) {
        List<Specification> specifications = product.getSpecifications();
        Set<com.bootx.entity.Sku> skus = new HashSet<>();
        Map<String,String> map = new HashMap<>();
        for (Specification specification:specifications) {
            specification.getEntries().forEach(item->map.put(item.getValue(),item.getName()));
        }
        if(jsonRootBean.getValItemInfo()!=null&&jsonRootBean.getValItemInfo().getSkuMap().size()>0){
            Map<String, Sku> skuMap = jsonRootBean.getValItemInfo().getSkuMap();
            for (String key:skuMap.keySet()) {
                com.bootx.entity.Sku sku = new com.bootx.entity.Sku();
                sku.setSpecificationValues(new ArrayList<>());
                sku.setProduct(product);
                sku.setSn(skuMap.get(key).getSkuId());
                sku.setStock(skuMap.get(key).getStock());
                sku.setPrice(skuMap.get(key).getPrice());
                String[] keys = StringUtils.split(key, ";");
                for (String key1:keys) {
                    sku.getSpecificationValues().add(new SpecificationValue(key1,map.get(key1)));
                }
                skus.add(sku);
            }
        }else{
            com.bootx.entity.Sku sku = new com.bootx.entity.Sku();
            sku.setSpecificationValues(new ArrayList<>());
            sku.setProduct(product);
            sku.setSn("abc");
            sku.setStock(product.getStock());
            try{
                sku.setPrice(new BigDecimal(product.getPrice()));
            }catch (Exception e){
                e.printStackTrace();
            }
            skus.add(sku);
        }

        return skus;
    }

    private void title(Document root, Product product) {
        Element titleElement = root.getElementsByClass("tb-detail-hd").first();
        String name = titleElement.getElementsByTag("h1").text();
        System.out.println(name);
        product.setName(name);
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
        headers.put("cookie"," ali_apache_id=11.186.201.24.1608081397956.375127.0; UM_distinctid=176691ede71c23-047088109db407-5a301e42-384000-176691ede72472; cna=lbpTGObSAmoCAdy93EFF48LR; hng=SG%7Czh-CN%7CSGD%7C702; xlly_s=1; taklid=80e36d68b608480cb8d8f7c65738a4f1; cookie2=1870236e12578a43b1baef46b631db8f; t=598d02d599fd9218df771f224a4ba61b; _tb_token_=793e71181be70; __cn_logon__=false; ali_ab=183.132.233.211.1609637776328.2; _csrf_token=1609676857350; h_keys=\"%u6bdb%u8863#%u5973%u88c5\"; CNZZDATA1261052687=680240079-1609672657-https%253A%252F%252Fdetail.1688.com%252F%7C1609672657; alicnweb=touch_tb_at%3D1609684991943; _m_h5_tk=ac2cb68dfc70ae8266b790216a51c519_1609694585131; _m_h5_tk_enc=198546fa5c9814c48b0034a82405956a; CNZZDATA1253659577=822592375-1609594397-https%253A%252F%252Fp4psearch.1688.com%252F%7C1609686202; ad_prefer=\"2021/01/03 23:17:44\"; x5sec=7b226c61707574613b32223a223434356231386536366565616238653833653131343335333735646334386334434c3744782f3846454a2f32315943753074657762413d3d227d; JSESSIONID=70CC3D563E99681579631776BFD250B7; tfstk=ceslBFwtea8WsxTc5ut5I0rX0fAhZW4yNMSV0ignh2whRiIViR0q7zM8nQeHn21..; l=eBOW4ugeOWwpJFvbBOfanurza77OYIRYSuPzaNbMiOCP_yB67pt1WZ8jna-BCnGVhs_yR3PWA2GQBeYBqIXfb0I428AIHADmn; isg=BDs72TOk4LPb79wvQt8zsunWyh-lkE-ScQwJrC34cTpDjFtutWLn4vnOpizCqaeK");
        headers.put("sec-fetch-mode"," navigate");
        headers.put("user-agent"," Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36 Edg/87.0.664.66");
        String result = WebUtils.get(url,null,headers);
        return result;
    }
}
