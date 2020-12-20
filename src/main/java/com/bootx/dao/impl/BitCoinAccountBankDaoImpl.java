
package com.bootx.dao.impl;

import com.bootx.dao.BitCoinAccountBankDao;
import com.bootx.entity.BitCoinAccountBank;
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
public class BitCoinAccountBankDaoImpl extends BaseDaoImpl<BitCoinAccountBank, Long> implements BitCoinAccountBankDao {

    @Override
    public BitCoinAccountBank findByBitCoinAccountIdAndUserId(Long bitCoinAccountId, Long userId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<BitCoinAccountBank> criteriaQuery = criteriaBuilder.createQuery(BitCoinAccountBank.class);
        Root<BitCoinAccountBank> root = criteriaQuery.from(BitCoinAccountBank.class);
        criteriaQuery.select(root);
        if(bitCoinAccountId==null|| userId==null){
            return new BitCoinAccountBank();
        }
        Predicate restrictions = criteriaBuilder.conjunction();
        restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("bitCoinAccountId"), bitCoinAccountId));
        restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("userId"), userId));
        criteriaQuery.where(restrictions);
        try{
            return super.findList(criteriaQuery, null, null, null, null).get(0);
        }catch (Exception e){
            return new BitCoinAccountBank();
        }
    }
}