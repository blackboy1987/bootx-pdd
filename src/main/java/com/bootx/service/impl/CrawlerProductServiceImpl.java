
package com.bootx.service.impl;

import com.bootx.dao.CrawlerProductDao;
import com.bootx.entity.CrawlerLog;
import com.bootx.entity.CrawlerProduct;
import com.bootx.entity.Member;
import com.bootx.pdd.entity.PddCrawlerProduct;
import com.bootx.pdd.service.PddCrawlerProductService;
import com.bootx.plugin.CrawlerPlugin;
import com.bootx.service.CrawlerLogService;
import com.bootx.service.CrawlerProductService;
import com.bootx.service.PluginService;
import com.bootx.service.ProductCategoryService;
import com.bootx.util.CrawlerUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
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
    @Resource
    private PddCrawlerProductService pddCrawlerProductService;

    @Override
    public List<CrawlerProduct> crawler(Member member, String[] urls, Integer type,String batchId) {
        List<CrawlerProduct> crawlerProducts = saveCrawler(member,urls,type,batchId);
        return crawler(crawlerProducts,member);
    }

    @Override
    public List<CrawlerProduct> crawler(List<CrawlerProduct> crawlerProducts,Member member) {

        for (CrawlerProduct crawlerProduct:crawlerProducts) {
            if(crawlerProduct.getStatus()==1){
                pddCrawlerProductService.update(crawlerProduct,crawlerProduct.getPddCrawlerProduct());
                continue;
            }
            String pluginId = CrawlerUtils.getPlugInId(crawlerProduct.getUrl());
            CrawlerPlugin crawlerPlugin = pluginService.getCrawlerPlugin(pluginId);
            if(crawlerPlugin!=null){
                crawlerPlugin.product(member,crawlerProduct);
                /*if(crawlerProduct.getProductCategoryIds().size()>0){
                    crawlerProduct.setProductCategory(productCategoryService.findByOtherId(pluginId+"_"+crawlerProduct.getProductCategoryIds().get(crawlerProduct.getProductCategoryIds().size()-1)));
                    if(crawlerProduct.getProductCategory()==null){
                        // 只能推荐分类
                        tuiJianCategory(crawlerProduct);
                    }
                }*/
                crawlerProduct.setStatus(1);
            }else{
                crawlerProduct.setStatus(2);
            }
            update(crawlerProduct);
            pddCrawlerProductService.update(crawlerProduct,crawlerProduct.getPddCrawlerProduct());
        }
        return crawlerProducts;
    }


    private List<CrawlerProduct> saveCrawler(Member member, String[] urls, Integer type,String batchId) {
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
        crawlerLog.setBatchId(batchId);
        crawlerLogService.save(crawlerLog);
        Integer index = 0;
        for (String url:urls) {
            if(StringUtils.isNotBlank(url) || index<=10){
                CrawlerProduct crawlerProduct = findByUrl(url);
                if(crawlerProduct==null){
                    crawlerProduct = new CrawlerProduct();
                    crawlerProduct.setUrl(url);
                    crawlerProduct.setBatchId(batchId);
                    crawlerProduct.setMd5(DigestUtils.md5Hex(url));
                    crawlerProduct.getCrawlerLogs().add(crawlerLog);
                    if(StringUtils.isNotBlank(CrawlerUtils.getPlugInId(url)) &&!crawlerLog.getPluginIds().contains(CrawlerUtils.getPlugInId(url))){
                        crawlerLog.getPluginIds().add(CrawlerUtils.getPlugInId(url));
                        crawlerProduct.setPluginId(CrawlerUtils.getPlugInId(url));
                    }
                    crawlerProducts.add(super.save(crawlerProduct));
                }else{
                    crawlerLog.setSuccess(crawlerLog.getSuccess()+1);
                    crawlerProducts.add(crawlerProduct);
                }
                index++;
            }
        }
        crawlerLogService.update(crawlerLog);

        for (CrawlerProduct crawlerProduct:crawlerProducts) {
            PddCrawlerProduct pddCrawlerProduct = new PddCrawlerProduct();
            BeanUtils.copyProperties(crawlerProduct,pddCrawlerProduct,"id");
            pddCrawlerProduct.setMember(member);
            pddCrawlerProduct.setCrawlerProduct(crawlerProduct);
            pddCrawlerProduct.setIsDeleted(false);
            pddCrawlerProduct.setBatchId(batchId);
            pddCrawlerProductService.save(pddCrawlerProduct);
            crawlerProduct.setPddCrawlerProduct(pddCrawlerProduct);
        }

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