package com.bootx.controller;

import com.bootx.controller.admin.BaseController;
import com.bootx.entity.Platform;
import com.bootx.entity.Product;
import com.bootx.entity.ProductCategory;
import com.bootx.plugin.CrawlerPlugin;
import com.bootx.service.PlatformService;
import com.bootx.service.PluginService;
import com.bootx.service.ProductCategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/init")
public class InitController extends BaseController {

    @Resource
    private PlatformService platformService;
    @Resource
    private PluginService pluginService;
    @Resource
    private ProductCategoryService productCategoryService;

    @GetMapping("/index")
    public String init(){

        Platform platform = new Platform();
        platform.setName("苏宁易购");
        platform.setSiteUrl("https://www.suning.com/");

        Map<String,String> apiUrls = new HashMap<>();
        apiUrls.put("category","https://list.suning.com/");
        platform.setApiUrls(apiUrls);
        platformService.save(platform);

        return "ok";
    }
    @GetMapping("/category")
    public String category(String pluginId){
        jdbcTemplate.update("delete from productcategory where pluginId=?",pluginId);
        CrawlerPlugin crawlerPlugin = pluginService.getCrawlerPlugin(pluginId);
        List<ProductCategory> productCategories = crawlerPlugin.productCategory();
        for (ProductCategory productCategory:productCategories) {
            productCategoryService.save(productCategory);
            for (ProductCategory second:productCategory.getChildren()) {
                productCategoryService.save(second);
                for (ProductCategory third:second.getChildren()) {
                    productCategoryService.save(third);
                }
            }
        }
        return "ok";
    }
    @GetMapping("/product1")
    public String product1(String pluginId,String url){
        // 000000012198272087  000000012198272087
        url="https://product.suning.com/0000000000/12198215857.html?srcpoint=shouji_phone2018_103808426459_prod04&safp=d488778a.phone2018.103808426459.4&safc=prd.0.0&safpn=10003.00006";
        url="https://product.suning.com/0000000000/12198272087.html?srcpoint=shouji_phone2018_103808426459_prod04&safp=d488778a.phone2018.103808426459.4&safc=prd.0.0&safpn=10003.00006&ch=cu";
        url="https://product.suning.com/0000000000/12198272087.html#?srcpoint=shouji_phone2018_103808426459_prod04&safp=d488778a.phone2018.103808426459.4&safc=prd.0.0&safpn=10003.00006&ch=cu";
        // 000000012198272109 000000012198272109
        url="https://product.suning.com/0000000000/12198272109.html#?srcpoint=shouji_phone2018_103808426459_prod04&safp=d488778a.phone2018.103808426459.4&safc=prd.0.0&safpn=10003.00006&ch=cu";
        // 000000012198272121 000000012198272121
        url="https://product.suning.com/0000000000/12198272121.html#?srcpoint=shouji_phone2018_103808426459_prod04&safp=d488778a.phone2018.103808426459.4&safc=prd.0.0&safpn=10003.00006&ch=cu";
        // 000000012198272129 000000012198272129
        url="https://product.suning.com/0000000000/12198272129.html#?srcpoint=shouji_phone2018_103808426459_prod04&safp=d488778a.phone2018.103808426459.4&safc=prd.0.0&safpn=10003.00006&ch=cu";
        CrawlerPlugin crawlerPlugin = pluginService.getCrawlerPlugin(pluginId);
        Product product = crawlerPlugin.product(url);
        return "ok";
    }
    @GetMapping("/product")
    public String product(String pluginId,String url){
        // 000000012198272087  000000012198272087
        url="https://detail.1688.com/offer/535699077418.html?spm=a260j.12536134.jr601u7p.3.18c776d2zN5FP4";
        CrawlerPlugin crawlerPlugin = pluginService.getCrawlerPlugin(pluginId);
        Product product = crawlerPlugin.product(url);
        return "ok";
    }
}
