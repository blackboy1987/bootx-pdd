
package com.bootx.service.impl;

import com.bootx.entity.CrawlerLog;
import com.bootx.entity.CrawlerProduct;
import com.bootx.entity.Member;
import com.bootx.service.CrawlerLogService;
import com.bootx.util.CrawlerUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Service - 审计日志
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Service
public class CrawlerLogServiceImpl extends BaseServiceImpl<CrawlerLog, Long> implements CrawlerLogService {

    @Override
    public CrawlerLog save(Member member,String[] urls, Integer type) {
        CrawlerLog crawlerLog = new CrawlerLog();
        crawlerLog.setPluginIds(new ArrayList<>());
        crawlerLog.setMember(member);
        crawlerLog.setType(type);
        crawlerLog.setStatus(0);
        crawlerLog.setCrawlerProducts(new HashSet<>());
        for (String url:urls) {
            CrawlerProduct crawlerProduct = new CrawlerProduct();
            crawlerProduct.init();
            crawlerProduct.getCrawlerLogs().add(crawlerLog);
            if(StringUtils.isNotBlank(CrawlerUtils.getPlugInId(url)) &&!crawlerLog.getPluginIds().contains(CrawlerUtils.getPlugInId(url))){
                crawlerLog.getPluginIds().add(CrawlerUtils.getPlugInId(url));
                crawlerProduct.setPluginId(CrawlerUtils.getPlugInId(url));
            }
            crawlerLog.setTotal(urls.length);
            crawlerLog.setSuccess(0);
            crawlerLog.setFail(0);
        }
        return super.save(crawlerLog);
    }
}