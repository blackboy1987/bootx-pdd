
package com.bootx.service.impl;

import com.bootx.dao.CrawlerUrlLogDao;
import com.bootx.entity.CrawlerLog;
import com.bootx.entity.CrawlerUrlLog;
import com.bootx.service.CrawlerLogService;
import com.bootx.service.CrawlerUrlLogService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

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
    @Resource
    private CrawlerLogService crawlerLogService;

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
            CrawlerLog crawlerLog = crawlerUrlLog.getCrawlerLog();
            if(status==1){
                // 成功
                crawlerLog.setSuccess(crawlerLog.getSuccess()+1);
            }else if(status==2){
                // 失败
                crawlerLog.setSuccess(crawlerLog.getFail()+1);
            }
            crawlerLogService.update(crawlerLog);
        }
    }

    @Override
    public void updateInfo(String url,String crawlerLogSn, String memo,Integer status) {
        CrawlerUrlLog crawlerUrlLog = findByUrlAndCrawlerLogSn(url,crawlerLogSn);
        if(crawlerUrlLog!=null){
            crawlerUrlLog.setStatus(status);
            crawlerUrlLog.setMemo(memo);
            super.update(crawlerUrlLog);
            CrawlerLog crawlerLog = crawlerUrlLog.getCrawlerLog();
            if(status==1){
                // 成功
                crawlerLog.setSuccess(crawlerLog.getSuccess()+1);
            }else if(status==2){
                // 失败
                crawlerLog.setSuccess(crawlerLog.getFail()+1);
            }
            crawlerLogService.update(crawlerLog);
        }
    }

    @Override
    public CrawlerUrlLog findByUrlAndCrawlerLogSn(String url, String crawlerLogSn) {
        return crawlerUrlLogDao.findByUrlAndCrawlerLogSn(url,crawlerLogSn);
    }
}