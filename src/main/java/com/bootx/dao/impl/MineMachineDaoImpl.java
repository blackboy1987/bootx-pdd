
package com.bootx.dao.impl;

import com.bootx.dao.MineMachineDao;
import com.bootx.entity.MineMachine;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Collections;
import java.util.List;

/**
 * Dao - 广告
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Repository
public class MineMachineDaoImpl extends BaseDaoImpl<MineMachine, Long> implements MineMachineDao {

    @Override
    public List<MineMachine> findListByCoinType(Integer coinType,Integer count) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<MineMachine> criteriaQuery = criteriaBuilder.createQuery(MineMachine.class);
        Root<MineMachine> root = criteriaQuery.from(MineMachine.class);
        criteriaQuery.select(root);
        if(coinType==null){
            return Collections.emptyList();
        }
        criteriaQuery.where(criteriaBuilder.equal(root.get("coinType"), coinType));
        return super.findList(criteriaQuery, null, count, null, null);
    }

    @Override
    public List<MineMachine> findListByCoinTypeAndType(Integer coinType, Integer type) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<MineMachine> criteriaQuery = criteriaBuilder.createQuery(MineMachine.class);
        Root<MineMachine> root = criteriaQuery.from(MineMachine.class);
        criteriaQuery.select(root);
        if(coinType==null|| type==null){
            return Collections.emptyList();
        }
        Predicate restrictions = criteriaBuilder.conjunction();
        restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("coinType"), coinType));
        restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("type"), type));
        criteriaQuery.where(restrictions);
        return super.findList(criteriaQuery, null, null, null, null);
    }

    @Override
    public MineMachine findDefault() {
        try {
            String jpql = "select mineMachine from MineMachine mineMachine where mineMachine.type = 100";
            return entityManager.createQuery(jpql, MineMachine.class).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}