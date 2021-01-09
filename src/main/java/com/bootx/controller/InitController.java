package com.bootx.controller;

import com.bootx.controller.admin.BaseController;
import com.bootx.entity.Platform;
import com.bootx.entity.Product;
import com.bootx.entity.ProductCategory;
import com.bootx.pdd.service.PddService;
import com.bootx.plugin.CrawlerPlugin;
import com.bootx.service.PlatformService;
import com.bootx.service.PluginService;
import com.bootx.service.ProductCategoryService;
import com.bootx.util.JsonUtils;
import com.pdd.pop.sdk.http.api.pop.response.PddGoodsCatsGetResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.HashSet;
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
    @Resource
    private PddService pddService;

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
        jdbcTemplate.update("update productcategory set parent_id=null where pluginId=?",pluginId);
        jdbcTemplate.update("delete from productcategory where pluginId=?",pluginId);
        CrawlerPlugin crawlerPlugin = pluginService.getCrawlerPlugin(pluginId);
        List<ProductCategory> productCategories = crawlerPlugin.productCategory();
        for (ProductCategory productCategory:productCategories) {
            productCategoryService.save(productCategory);
            for (ProductCategory second:productCategory.getChildren()) {
                second.setParent(productCategory);
                productCategoryService.save(second);
                for (ProductCategory third:second.getChildren()) {
                    third.setParent(second);
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
        Product product = crawlerPlugin.product(null,url);
        return "ok";
    }
    @GetMapping("/product")
    public String product(String pluginId,String url){
        // 000000012198272087  000000012198272087
        url="https://detail.1688.com/offer/535699077418.html?spm=a260j.12536134.jr601u7p.3.18c776d2zN5FP4";
        CrawlerPlugin crawlerPlugin = pluginService.getCrawlerPlugin(pluginId);
        Product product = crawlerPlugin.product(null,url);
        return "ok";
    }


    @GetMapping("/category1")
    public PddGoodsCatsGetResponse category1() throws Exception {
        jdbcTemplate.update("update productcategory set parent_id=null where pluginId=?","pddPlugin");
        jdbcTemplate.update("delete from productcategory where pluginId=?","pddPlugin");
        PddGoodsCatsGetResponse pddGoodsCatsGetResponse = pddService.category(0L);
        System.out.println(JsonUtils.toJson(pddGoodsCatsGetResponse));
        PddGoodsCatsGetResponse.GoodsCatsGetResponse goodsCatsGetResponse = pddGoodsCatsGetResponse.getGoodsCatsGetResponse();
        if(goodsCatsGetResponse==null){
            return null;
        }
        List<PddGoodsCatsGetResponse.GoodsCatsGetResponseGoodsCatsListItem> goodsCatsList =  goodsCatsGetResponse.getGoodsCatsList();
        for (PddGoodsCatsGetResponse.GoodsCatsGetResponseGoodsCatsListItem goodsCatsListItem: goodsCatsList) {
            ProductCategory productCategory = new ProductCategory();
            productCategory.setParent(productCategoryService.findByOtherId("pddPlugin_"+goodsCatsListItem.getParentCatId()));
            productCategory.setOtherId("pddPlugin_"+goodsCatsListItem.getCatId());
            productCategory.setChildren(new HashSet<>());
            productCategory.setPluginId("pddPlugin");
            productCategory.setName(goodsCatsListItem.getCatName());
            productCategoryService.save(productCategory);




            PddGoodsCatsGetResponse pddGoodsCatsGetResponse1 = pddService.category(goodsCatsListItem.getCatId());
            List<PddGoodsCatsGetResponse.GoodsCatsGetResponseGoodsCatsListItem> goodsCatsList1 = pddGoodsCatsGetResponse1.getGoodsCatsGetResponse().getGoodsCatsList();
            for (PddGoodsCatsGetResponse.GoodsCatsGetResponseGoodsCatsListItem goodsCatsListItem1: goodsCatsList1) {
                ProductCategory productCategory1 = new ProductCategory();
                productCategory1.setParent(productCategoryService.findByOtherId("pddPlugin_" + goodsCatsListItem1.getParentCatId()));
                productCategory1.setOtherId("pddPlugin_" + goodsCatsListItem1.getCatId());
                productCategory1.setChildren(new HashSet<>());
                productCategory1.setPluginId("pddPlugin");
                productCategory1.setName(goodsCatsListItem1.getCatName());
                productCategoryService.save(productCategory1);


                PddGoodsCatsGetResponse pddGoodsCatsGetResponse2 = pddService.category(goodsCatsListItem1.getCatId());
                List<PddGoodsCatsGetResponse.GoodsCatsGetResponseGoodsCatsListItem> goodsCatsList2 = pddGoodsCatsGetResponse2.getGoodsCatsGetResponse().getGoodsCatsList();
                for (PddGoodsCatsGetResponse.GoodsCatsGetResponseGoodsCatsListItem goodsCatsListItem2: goodsCatsList2) {
                    ProductCategory productCategory2 = new ProductCategory();
                    productCategory2.setParent(productCategoryService.findByOtherId("pddPlugin_" + goodsCatsListItem2.getParentCatId()));
                    productCategory2.setOtherId("pddPlugin_" + goodsCatsListItem2.getCatId());
                    productCategory2.setChildren(new HashSet<>());
                    productCategory2.setPluginId("pddPlugin");
                    productCategory2.setName(goodsCatsListItem2.getCatName());
                    productCategoryService.save(productCategory2);



                    PddGoodsCatsGetResponse pddGoodsCatsGetResponse3 = pddService.category(goodsCatsListItem2.getCatId());
                    List<PddGoodsCatsGetResponse.GoodsCatsGetResponseGoodsCatsListItem> goodsCatsList3 = pddGoodsCatsGetResponse3.getGoodsCatsGetResponse().getGoodsCatsList();
                    for (PddGoodsCatsGetResponse.GoodsCatsGetResponseGoodsCatsListItem goodsCatsListItem3: goodsCatsList3) {
                        ProductCategory productCategory3 = new ProductCategory();
                        productCategory3.setParent(productCategoryService.findByOtherId("pddPlugin_" + goodsCatsListItem3.getParentCatId()));
                        productCategory3.setOtherId("pddPlugin_" + goodsCatsListItem3.getCatId());
                        productCategory3.setChildren(new HashSet<>());
                        productCategory3.setPluginId("pddPlugin");
                        productCategory3.setName(goodsCatsListItem3.getCatName());
                        productCategoryService.save(productCategory3);

                    }
                }
            }
        }


        return pddGoodsCatsGetResponse;
    }
}
