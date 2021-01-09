
package com.bootx.dao;

import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.entity.CrawlerUrlLog;
import com.bootx.entity.Member;

/**
 * Dao - 审计日志
 * 
 * @author 好源++ Team
 * @version 6.1
 */
public interface CrawlerUrlLogDao extends BaseDao<CrawlerUrlLog, Long> {

    CrawlerUrlLog findByUrlAndCrawlerLogSn(String url, String crawlerLogSn);

    Page<CrawlerUrlLog> findPage(Pageable pageable, Member member, Integer status);
}