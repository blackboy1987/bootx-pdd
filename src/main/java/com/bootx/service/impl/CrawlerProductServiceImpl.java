
package com.bootx.service.impl;

import com.bootx.dao.CrawlerProductDao;
import com.bootx.entity.CrawlerLog;
import com.bootx.entity.CrawlerProduct;
import com.bootx.entity.Member;
import com.bootx.plugin.CrawlerPlugin;
import com.bootx.service.CrawlerLogService;
import com.bootx.service.CrawlerProductService;
import com.bootx.service.PluginService;
import com.bootx.service.ProductCategoryService;
import com.bootx.util.CrawlerUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

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
            if(crawlerProduct.getStatus()==1){
                continue;
            }
            String pluginId = CrawlerUtils.getPlugInId(crawlerProduct.getUrl());
            CrawlerPlugin crawlerPlugin = pluginService.getCrawlerPlugin(pluginId);
            if(crawlerPlugin!=null){
                crawlerPlugin.product(member,crawlerProduct);
                if(crawlerProduct.getProductCategoryIds().size()>0){
                    crawlerProduct.setProductCategory(productCategoryService.findByOtherId(pluginId+"_"+crawlerProduct.getProductCategoryIds().get(crawlerProduct.getProductCategoryIds().size()-1)));
                }
                crawlerProduct.setStatus(1);
            }else{
                crawlerProduct.setStatus(2);
            }
            update(crawlerProduct);
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
                crawlerProduct = new CrawlerProduct();
                crawlerProduct.setUrl(url);
                crawlerProduct.setMd5(DigestUtils.md5Hex(url));
                crawlerProduct.getCrawlerLogs().add(crawlerLog);
                if(StringUtils.isNotBlank(CrawlerUtils.getPlugInId(url)) &&!crawlerLog.getPluginIds().contains(CrawlerUtils.getPlugInId(url))){
                    crawlerLog.getPluginIds().add(CrawlerUtils.getPlugInId(url));
                    crawlerProduct.setPluginId(CrawlerUtils.getPlugInId(url));
                }
                crawlerProducts.add(super.save(crawlerProduct));
            }else{
                crawlerLog.setSuccess(crawlerLog.getSuccess()+1);
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