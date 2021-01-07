
package com.bootx.service;

import com.bootx.entity.CrawlerLog;
import com.bootx.entity.Member;

/**
 * Service - 审计日志
 * 
 * @author 好源++ Team
 * @version 6.1
 */
public interface CrawlerLogService extends BaseService<CrawlerLog, Long> {

    CrawlerLog save(Member member, String[] urls, Integer type);
}