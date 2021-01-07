
package com.bootx.service.impl;

import com.bootx.dao.CrawlerUrlLogDao;
import com.bootx.entity.CrawlerUrlLog;
import com.bootx.service.CrawlerUrlLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Service - 审计日志
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Service
public class CrawlerUrlLogServiceImpl extends BaseServiceImpl<CrawlerUrlLog, Long> implements CrawlerUrlLogService {

    @Resource
    private CrawlerUrlLogDao crawlerUrlLogDao;

    @Override
    public CrawlerUrlLog findByUrl(String url) {
        return crawlerUrlLogDao.find("url",url);
    }

    @Override
    public void updateInfo(String url,String productSn,String crawlerLogSn,String memo,Integer status) {
        CrawlerUrlLog crawlerUrlLog = findByUrlAndCrawlerLogSn(url,crawlerLogSn);
        if(crawlerUrlLog!=null){
            crawlerUrlLog.setStatus(status);
            crawlerUrlLog.setProductId(productSn);
            crawlerUrlLog.setMemo(memo);
            super.update(crawlerUrlLog);
            jdbcTemplate.update("update crawlerlog as crawlerLog set status=1 where crawlerLog.status=0 and (select count(crawlerUrlLog.id) from crawlerurllog as crawlerUrlLog where crawlerUrlLog.crawlerLog_id=crawlerLog.id and crawlerUrlLog.status=0)=0");
        }
    }

    @Override
    public void updateInfo(String url,String crawlerLogSn, String memo,Integer status) {
        CrawlerUrlLog crawlerUrlLog = findByUrlAndCrawlerLogSn(url,crawlerLogSn);
        if(crawlerUrlLog!=null){
            crawlerUrlLog.setStatus(status);
            crawlerUrlLog.setMemo(memo);
            super.update(crawlerUrlLog);
            jdbcTemplate.update("update crawlerlog as crawlerLog set status=1 where crawlerLog.status=0 and (select count(crawlerUrlLog.id) from crawlerurllog as crawlerUrlLog where crawlerUrlLog.crawlerLog_id=crawlerLog.id and crawlerUrlLog.status=0)=0");
        }
    }

    @Override
    public CrawlerUrlLog findByUrlAndCrawlerLogSn(String url, String crawlerLogSn) {
        return crawlerUrlLogDao.findByUrlAndCrawlerLogSn(url,crawlerLogSn);
    }
}