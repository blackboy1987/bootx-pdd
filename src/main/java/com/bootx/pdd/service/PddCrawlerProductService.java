
package com.bootx.pdd.service;

import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.entity.CrawlerProduct;
import com.bootx.entity.Member;
import com.bootx.pdd.entity.PddCrawlerProduct;
import com.bootx.service.BaseService;

import java.util.Date;

/**
 * Service - 审计日志
 * 
 * @author 好源++ Team
 * @version 6.1
 */
public interface PddCrawlerProductService extends BaseService<PddCrawlerProduct, Long> {

    void update(CrawlerProduct crawlerProduct, PddCrawlerProduct pddCrawlerProduct);

    Page<PddCrawlerProduct> findPage(Pageable pageable, String name, String sn, Integer status,Integer publishStatus,Boolean isDeleted, Date beginDate, Date endDate, Member member);

    void publish(Long[] ids, Long[] storeIds) throws Exception;
}