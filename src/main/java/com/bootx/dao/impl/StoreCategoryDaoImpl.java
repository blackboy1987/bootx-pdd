
package com.bootx.dao.impl;

import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.dao.StoreCategoryDao;
import com.bootx.entity.Member;
import com.bootx.entity.StoreCategory;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Collections;
import java.util.List;

/**
 * Dao - 审计日志
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Repository
public class StoreCategoryDaoImpl extends BaseDaoImpl<StoreCategory, Long> implements StoreCategoryDao {

    @Override
    public List<StoreCategory> findList(Member member) {
        if (member==null) {
            return Collections.emptyList();
        }
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<StoreCategory> criteriaQuery = criteriaBuilder.createQuery(StoreCategory.class);
        Root<StoreCategory> root = criteriaQuery.from(StoreCategory.class);
        criteriaQuery.select(root);
        Predicate restrictions = criteriaBuilder.conjunction();
        restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("member"),member));
        criteriaQuery.where(restrictions);
        return super.findList(criteriaQuery, null, null, null, null);
    }

    @Override
    public Page<StoreCategory> findPage (Pageable pageable, Member member) {
        if (member==null) {
            return new Page<>(Collections.emptyList(),0L,pageable);
        }
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<StoreCategory> criteriaQuery = criteriaBuilder.createQuery(StoreCategory.class);
        Root<StoreCategory> root = criteriaQuery.from(StoreCategory.class);
        criteriaQuery.select(root);
        Predicate restrictions = criteriaBuilder.conjunction();
        restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("member"),member));
        criteriaQuery.where(restrictions);
        return super.findPage(criteriaQuery, pageable);
    }

    @Override
    public StoreCategory findDefault(Member member) {
        try {
            String jpql = "select storeCategory from StoreCategory storeCategory where storeCategory.isDefault = true and storeCategory.member = :member";
            return entityManager.createQuery(jpql, StoreCategory.class).setParameter("member",member).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}