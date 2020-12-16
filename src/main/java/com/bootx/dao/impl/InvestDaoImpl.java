
package com.bootx.dao.impl;

import com.bootx.dao.InvestDao;
import com.bootx.entity.Invest;
import com.bootx.entity.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;

/**
 * Dao - 广告
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Repository
public class InvestDaoImpl extends BaseDaoImpl<Invest, Long> implements InvestDao {

    @Override
    public List<Invest> findListByCoinType(Member member,Integer coinType) {
        if(member==null|| coinType==null){
            return Collections.emptyList();
        }
        String jpql = "select invest from Invest invest where invest.userId =:userId and invest.coinType =:coinType order by invest.createdDate desc";
        TypedQuery<Invest> query = entityManager.createQuery(jpql, Invest.class).setParameter("userId",member.getId()).setParameter("coinType",coinType);

        return query.getResultList();
    }
}