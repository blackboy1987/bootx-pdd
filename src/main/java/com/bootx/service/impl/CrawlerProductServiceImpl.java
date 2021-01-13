
package com.bootx.service.impl;

import com.bootx.dao.CrawlerProductDao;
import com.bootx.dao.ProductDao;
import com.bootx.entity.CrawlerLog;
import com.bootx.entity.CrawlerProduct;
import com.bootx.entity.Member;
import com.bootx.entity.Product;
import com.bootx.plugin.CrawlerPlugin;
import com.bootx.service.CrawlerLogService;
import com.bootx.service.CrawlerProductService;
import com.bootx.service.PluginService;
import com.bootx.service.ProductCategoryService;
import com.bootx.util.CrawlerUtils;
import javafx.scene.effect.SepiaTone;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Service - 审计日志
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Service
public class CrawlerProductServiceImpl extends BaseServiceImpl<CrawlerProduct, Long> implements CrawlerProductService {

    @Resource
    private PluginService pluginService;
    @Resource
    private CrawlerProductDao crawlerProductDao;
    @Resource
    private CrawlerLogService crawlerLogService;
    @Resource
    private ProductCategoryService productCategoryService;

    @Override
    public List<CrawlerProduct> crawler(Member member, String[] urls, Integer type) {

        List<CrawlerProduct> crawlerProducts = saveCrawler(member,urls,type);

        for (CrawlerProduct crawlerProduct:crawlerProducts) {
            if(crawlerProduct.isNew()){
                crawlerProducts.add(crawlerProduct);
                continue;
            }
            String pluginId = CrawlerUtils.getPlugInId(crawlerProduct.getUrl());
            CrawlerPlugin crawlerPlugin = pluginService.getCrawlerPlugin(pluginId);
            if(crawlerPlugin!=null){
                crawlerPlugin.product(member,crawlerProduct);
                if(crawlerProduct!=null){
                    crawlerProduct.setProductCategory(productCategoryService.findByOtherId(pluginId+"_"+crawlerProduct.getProductCategoryIds().get(crawlerProduct.getProductCategoryIds().size()-1)));
                    crawlerProduct
                    crawlerProduct.setStatus(1);
                    update(crawlerProduct);
                    crawlerProductService.updateInfo(url,product,crawlerLog.getSn(),"采集完成",1);
                }else{
                    crawlerProductService.updateInfo(url,null,crawlerLog.getSn(),"采集失败",2);
                }
            }else{
                crawlerProductService.updateInfo(url,crawlerLog.getSn(),"不支持该地址",2);
            }

        }
        return crawlerProducts;
    }

    private List<CrawlerProduct> saveCrawler(Member member, String[] urls, Integer type) {
        List<CrawlerProduct> crawlerProducts = new ArrayList<>();
        CrawlerLog crawlerLog = new CrawlerLog();
        crawlerLog.setPluginIds(new ArrayList<>());
        crawlerLog.setMember(member);
        crawlerLog.setType(type);
        crawlerLog.setStatus(0);
        crawlerLog.setCrawlerProducts(new HashSet<>());
        crawlerLog.setTotal(urls.length);
        crawlerLog.setSuccess(0);
        crawlerLog.setFail(0);
        crawlerLogService.save(crawlerLog);
        for (String url:urls) {
            CrawlerProduct crawlerProduct = findByUrl(url);
            if(crawlerProduct==null){
                crawlerProduct.init();
                crawlerProduct.setCrawlerLog(crawlerLog);
                if(StringUtils.isNotBlank(CrawlerUtils.getPlugInId(url)) &&!crawlerLog.getPluginIds().contains(CrawlerUtils.getPlugInId(url))){
                    crawlerLog.getPluginIds().add(CrawlerUtils.getPlugInId(url));
                    crawlerProduct.setPluginId(CrawlerUtils.getPlugInId(url));
                }
                crawlerProducts.add(super.save(crawlerProduct));

            }
        }
        crawlerLogService.update(crawlerLog);
        return crawlerProducts;
    }

    @Override
    public CrawlerProduct findBySn(String sn) {
        return crawlerProductDao.find("sn",sn);
    }

    @Override
    public CrawlerProduct findByUrl(String url) {
        return crawlerProductDao.find("md5", DigestUtils.md5Hex(url));

    }
}