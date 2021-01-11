
package com.bootx.service;

import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.entity.CrawlerUrlLog;
import com.bootx.entity.Member;
import com.bootx.entity.Product;

/**
 * Service - 审计日志
 * 
 * @author 好源++ Team
 * @version 6.1
 */
public interface CrawlerUrlLogService extends BaseService<CrawlerUrlLog, Long> {

    CrawlerUrlLog findByUrl(String url);

    void updateInfo(String url, Product product, String crawlerLogSn, String memo, Integer status);

    void updateInfo(String url,String crawlerLogSn, String memo,Integer status);

    CrawlerUrlLog findByUrlAndCrawlerLogSn(String url, String crawlerLogSn);

    Page<CrawlerUrlLog> findPage(Pageable pageable, Member member, Integer status);

    void upload(Long[] ids,Long[] storeIds,Integer type) throws Exception;

    void updateProductCategory(Long id, Long[] productCategoryId, Member member);

}