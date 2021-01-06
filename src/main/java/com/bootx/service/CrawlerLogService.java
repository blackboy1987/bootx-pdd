
package com.bootx.service;

import com.bootx.entity.CrawlerLog;

/**
 * Service - 审计日志
 * 
 * @author 好源++ Team
 * @version 6.1
 */
public interface CrawlerLogService extends BaseService<CrawlerLog, Long> {

    void save(String[] urls, Integer type);
}