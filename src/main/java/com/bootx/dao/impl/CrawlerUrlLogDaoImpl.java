
package com.bootx.dao.impl;

import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.dao.CrawlerUrlLogDao;
import com.bootx.entity.CrawlerUrlLog;
import com.bootx.entity.Member;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Dao - 审计日志
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Repository
public class CrawlerUrlLogDaoImpl extends BaseDaoImpl<CrawlerUrlLog, Long> implements CrawlerUrlLogDao {

    @Override
    public CrawlerUrlLog findByUrlAndCrawlerLogSn(String url, String crawlerLogSn) {
        if (StringUtils.isBlank(url) || StringUtils.isBlank(crawlerLogSn)) {
            return null;
        }
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<CrawlerUrlLog> criteriaQuery = criteriaBuilder.createQuery(CrawlerUrlLog.class);
        Root<CrawlerUrlLog> root = criteriaQuery.from(CrawlerUrlLog.class);
        criteriaQuery.select(root);
        Predicate restrictions = criteriaBuilder.conjunction();
        restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("url"), url));
        restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("crawlerLogSn"), crawlerLogSn));
        criteriaQuery.where(restrictions);
       try {
           return super.findList(criteriaQuery).get(0);
       }catch (Exception e){
           return null;
       }
    }

    @Override
    public Page<CrawlerUrlLog> findPage(Pageable pageable, Member member, Integer status) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<CrawlerUrlLog> criteriaQuery = criteriaBuilder.createQuery(CrawlerUrlLog.class);
        Root<CrawlerUrlLog> root = criteriaQuery.from(CrawlerUrlLog.class);
        criteriaQuery.select(root);
        Predicate restrictions = criteriaBuilder.conjunction();
        if(member!=null){
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("member"), member));
        }
        if(status!=null){
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("status"), status));
        }
        criteriaQuery.where(restrictions);

        return super.findPage(criteriaQuery,pageable);
    }
}