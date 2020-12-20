
package com.bootx.dao.impl;

import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.dao.AccountCoinLogDao;
import com.bootx.entity.AccountCoinLog;
import org.apache.commons.lang3.StringUtils;
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
public class AccountCoinLogDaoImpl extends BaseDaoImpl<AccountCoinLog, Long> implements AccountCoinLogDao {

    @Override
    public Page<AccountCoinLog> findPage(Pageable pageable, Long userId, Integer excision, Integer assetType, String account,Integer state) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<AccountCoinLog> criteriaQuery = criteriaBuilder.createQuery(AccountCoinLog.class);
        Root<AccountCoinLog> root = criteriaQuery.from(AccountCoinLog.class);
        criteriaQuery.select(root);
        Predicate restrictions = criteriaBuilder.conjunction();
        if (userId != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("userId"), userId));
        }
        if (excision != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("excision"), excision));
        }
        if (assetType != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("assetType"), assetType));
        }
        if (state != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("state"), state));
        }
        if (StringUtils.isNotBlank(account)) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("account"), account));
        }
        criteriaQuery.where(restrictions);
        return super.findPage(criteriaQuery, pageable);
    }
}