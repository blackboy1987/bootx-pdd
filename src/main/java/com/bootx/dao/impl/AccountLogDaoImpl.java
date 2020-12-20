
package com.bootx.dao.impl;

import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.dao.AccountLogDao;
import com.bootx.entity.AccountLog;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Dao - 广告
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Repository
public class AccountLogDaoImpl extends BaseDaoImpl<AccountLog, Long> implements AccountLogDao {

    @Override
    public Page<AccountLog> findPage(Pageable pageable, Long userId, Integer dataType) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<AccountLog> criteriaQuery = criteriaBuilder.createQuery(AccountLog.class);
        Root<AccountLog> root = criteriaQuery.from(AccountLog.class);
        criteriaQuery.select(root);
        Predicate restrictions = criteriaBuilder.conjunction();
        if (userId != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("userId"), userId));
        }
        if (dataType != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("dataType"), dataType));
        }
        criteriaQuery.where(restrictions);
        return super.findPage(criteriaQuery, pageable);
    }
}