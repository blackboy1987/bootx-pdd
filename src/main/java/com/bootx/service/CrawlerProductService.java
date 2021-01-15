
package com.bootx.service;

import com.bootx.entity.CrawlerProduct;
import com.bootx.entity.Member;

import java.util.List;

/**
 * Service - 审计日志
 * 
 * @author 好源++ Team
 * @version 6.1
 */
public interface CrawlerProductService extends BaseService<CrawlerProduct, Long> {
    List<CrawlerProduct> crawler(Member member, String[] urls, Integer type);
    List<CrawlerProduct> crawler(List<CrawlerProduct> crawlerProducts,Member member);
    CrawlerProduct findBySn(String sn);

    CrawlerProduct findByUrl(String url);
}