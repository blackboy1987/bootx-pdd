
package com.bootx.service.impl;

import com.bootx.entity.CrawlerLog;
import com.bootx.entity.CrawlerUrlLog;
import com.bootx.entity.Member;
import com.bootx.service.CrawlerLogService;
import com.bootx.util.CrawlerUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.UUID;

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
        crawlerLog.setSn(UUID.randomUUID().toString().replace("-",""));
        crawlerLog.setMember(member);
        crawlerLog.setType(type);
        crawlerLog.setStatus(0);
        crawlerLog.setCrawlerUrlLogs(new HashSet<>());
        for (String url:urls) {
            CrawlerUrlLog crawlerUrlLog = new CrawlerUrlLog();
            crawlerUrlLog.setCrawlerLog(crawlerLog);
            crawlerUrlLog.setUrl(url);
            crawlerUrlLog.setType(type);
            crawlerUrlLog.setStatus(0);
            crawlerUrlLog.setMemo("采集中");
            crawlerUrlLog.setMember(member);
            crawlerUrlLog.setCrawlerLogSn(crawlerLog.getSn());
            crawlerLog.getCrawlerUrlLogs().add(crawlerUrlLog);
            if(StringUtils.isNotBlank(CrawlerUtils.getPlugInId(url)) &&!crawlerLog.getPluginIds().contains(CrawlerUtils.getPlugInId(url))){
                crawlerLog.getPluginIds().add(CrawlerUtils.getPlugInId(url));
            }

        }
        return super.save(crawlerLog);
    }
}