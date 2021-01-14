
package com.bootx.pdd.service;

import com.bootx.entity.CrawlerProduct;
import com.bootx.pdd.entity.PddCrawlerProduct;
import com.bootx.service.BaseService;

/**
 * Service - 审计日志
 * 
 * @author 好源++ Team
 * @version 6.1
 */
public interface PddCrawlerProductService extends BaseService<PddCrawlerProduct, Long> {

    void update(CrawlerProduct crawlerProduct, PddCrawlerProduct pddCrawlerProduct);
}