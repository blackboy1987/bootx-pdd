
package com.bootx.service;

import com.bootx.entity.CrawlerUrlLog;

/**
 * Service - 审计日志
 * 
 * @author 好源++ Team
 * @version 6.1
 */
public interface CrawlerUrlLogService extends BaseService<CrawlerUrlLog, Long> {

    CrawlerUrlLog findByUrl(String url);

    void updateInfo(String url,String productSn,String crawlerLogSn,String memo,Integer status);

    void updateInfo(String url,String crawlerLogSn, String memo,Integer status);

    CrawlerUrlLog findByUrlAndCrawlerLogSn(String url, String crawlerLogSn);
}