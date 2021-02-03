
package com.bootx.pdd.dao.impl;

import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.dao.impl.BaseDaoImpl;
import com.bootx.entity.Member;
import com.bootx.pdd.dao.PddCrawlerProductDao;
import com.bootx.pdd.entity.PddCrawlerProduct;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Dao - 审计日志
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Repository
public class PddCrawlerProductDaoImpl extends BaseDaoImpl<PddCrawlerProduct, Long> implements PddCrawlerProductDao {

    @Override
    public Page<PddCrawlerProduct> findPage(Pageable pageable, String name, String sn, Integer status,Integer publishStatus,Boolean isDeleted, Date beginDate, Date endDate, Member member) {
        if (member == null) {
            return Page.emptyPage(pageable);
        }
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<PddCrawlerProduct> criteriaQuery = criteriaBuilder.createQuery(PddCrawlerProduct.class);
        Root<PddCrawlerProduct> root = criteriaQuery.from(PddCrawlerProduct.class);
        criteriaQuery.select(root);
        Predicate restrictions = criteriaBuilder.conjunction();
        if (StringUtils.isNotBlank(name)) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.like(root.get("name"), "%"+name+"%"));
        }
        if (StringUtils.isNotBlank(sn)) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.like(root.get("sn"), "%"+sn+"%"));
        }
        if (status!=null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("status"), status));
        }
        if (publishStatus!=null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("publishStatus"), publishStatus));
        }
        if (isDeleted!=null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isDeleted"), isDeleted));
        }
        if (beginDate != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.greaterThanOrEqualTo(root.<Date>get("createdDate"), beginDate));
        }
        if (endDate != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.lessThanOrEqualTo(root.<Date>get("createdDate"), endDate));
        }

        criteriaQuery.where(restrictions);
        return super.findPage(criteriaQuery, pageable);
    }

    @Override
    public List<PddCrawlerProduct> findList1(String batchId) {
        if (StringUtils.isBlank(batchId)) {
            return Collections.emptyList();
        }
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<PddCrawlerProduct> criteriaQuery = criteriaBuilder.createQuery(PddCrawlerProduct.class);
        Root<PddCrawlerProduct> root = criteriaQuery.from(PddCrawlerProduct.class);
        criteriaQuery.select(root);
        Predicate restrictions = criteriaBuilder.conjunction();
        restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("batchId"), batchId));

        criteriaQuery.where(restrictions);
        return super.findList(criteriaQuery);
    }
}