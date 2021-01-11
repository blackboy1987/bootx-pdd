
package com.bootx.dao.impl;

import com.bootx.dao.StoreDao;
import com.bootx.entity.Member;
import com.bootx.entity.Store;
import org.springframework.stereotype.Repository;

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
public class StoreDaoImpl extends BaseDaoImpl<Store, Long> implements StoreDao {

    @Override
    public List<Store> findList(Member member) {
        if (member==null) {
            return Collections.emptyList();
        }
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Store> criteriaQuery = criteriaBuilder.createQuery(Store.class);
        Root<Store> root = criteriaQuery.from(Store.class);
        criteriaQuery.select(root);
        Predicate restrictions = criteriaBuilder.conjunction();
        restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("member"),member));
        criteriaQuery.where(restrictions);
        return super.findList(criteriaQuery, null, null, null, null);
    }
}