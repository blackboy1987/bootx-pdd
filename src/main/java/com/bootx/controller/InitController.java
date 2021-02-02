package com.bootx.controller;

import com.bootx.controller.admin.BaseController;
import com.bootx.entity.Platform;
import com.bootx.entity.ProductCategory;
import com.bootx.pdd.service.PddLogisticsService;
import com.bootx.plugin.CrawlerPlugin;
import com.bootx.service.PlatformService;
import com.bootx.service.PluginService;
import com.bootx.service.ProductCategoryService;
import com.pdd.pop.sdk.http.api.pop.response.PddGoodsLogisticsTemplateGetResponse;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/init")
@CrossOrigin
public class InitController extends BaseController {

    @Resource
    private PlatformService platformService;
    @Resource
    private PluginService pluginService;
    @Resource
    private ProductCategoryService productCategoryService;
    @Resource
    private PddLogisticsService pddLogisticsService;

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
        // Product product = crawlerPlugin.product(null,url);
        return "ok";
    }
    @GetMapping("/product")
    public String product(String pluginId,String url){
        // 000000012198272087  000000012198272087
        url="https://detail.1688.com/offer/535699077418.html?spm=a260j.12536134.jr601u7p.3.18c776d2zN5FP4";
        CrawlerPlugin crawlerPlugin = pluginService.getCrawlerPlugin(pluginId);
        // Product product = crawlerPlugin.product(null,url);
        return "ok";
    }


    @GetMapping("/category2")
    public String category2() throws Exception {
        /*List<Map<String, Object>> maps = jdbcTemplate.queryForList("select id from `bootx-pdd`.productcategory where grade=1 and otherId like 'pddPlugin%';");
        for (Map<String,Object> map: maps) {
            ProductCategory parent = productCategoryService.find(Long.valueOf(map.get("id")+""));
            PddGoodsCatsGetResponse pddGoodsCatsGetResponse = pddService.category(Long.valueOf(parent.getOtherId().replaceAll("pddPlugin_","")));
            PddGoodsCatsGetResponse.GoodsCatsGetResponse goodsCatsGetResponse = pddGoodsCatsGetResponse.getGoodsCatsGetResponse();
            if(goodsCatsGetResponse==null){
                return parent.getId()+"======";
            }
            List<PddGoodsCatsGetResponse.GoodsCatsGetResponseGoodsCatsListItem> goodsCatsList =  goodsCatsGetResponse.getGoodsCatsList();
            for (PddGoodsCatsGetResponse.GoodsCatsGetResponseGoodsCatsListItem goodsCatsListItem: goodsCatsList) {
                ProductCategory productCategory = new ProductCategory();
                productCategory.setParent(productCategoryService.findByOtherId("pddPlugin_" + goodsCatsListItem.getParentCatId()));
                productCategory.setOtherId("pddPlugin_" + goodsCatsListItem.getCatId());
                productCategory.setChildren(new HashSet<>());
                productCategory.setPluginId("pddPlugin");
                productCategory.setName(goodsCatsListItem.getCatName());
                productCategoryService.save(productCategory);
            }

        }*/
        return "ok";
    }




    @GetMapping("/taobao")
    public String taobao(){
        String url="https://item.taobao.com/item.htm?spm=a230r.1.14.24.5ef972cd4cLAvC&id=632090563436&ns=1&abbucket=10#detail";
        CrawlerPlugin crawlerPlugin = pluginService.getCrawlerPlugin("taoBaoPlugin");
        // Product product = crawlerPlugin.product(memberService.findAll().get(0),//url);
        return "ok";
    }

    @GetMapping("/template")
    public PddGoodsLogisticsTemplateGetResponse template(String token) throws Exception {
        return pddLogisticsService.templateGet(token);
    }
}
