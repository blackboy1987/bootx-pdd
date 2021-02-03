
package com.bootx.pdd.dao;

import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.dao.BaseDao;
import com.bootx.entity.Member;
import com.bootx.pdd.entity.PddCrawlerProduct;

import java.util.Date;
import java.util.List;

/**
 * Dao - 审计日志
 * 
 * @author 好源++ Team
 * @version 6.1
 */
public interface PddCrawlerProductDao extends BaseDao<PddCrawlerProduct, Long> {

    Page<PddCrawlerProduct> findPage(Pageable pageable, String name, String sn, Integer status,Integer publishStatus,Boolean isDeleted, Date beginDate, Date endDate, Member member);

    List<PddCrawlerProduct> findList1(String batchId);
}