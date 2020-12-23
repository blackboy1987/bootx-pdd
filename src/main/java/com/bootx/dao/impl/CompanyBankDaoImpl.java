
package com.bootx.dao.impl;

import com.bootx.dao.CompanyBankDao;
import com.bootx.entity.CompanyBank;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import javax.persistence.TypedQuery;

/**
 * Dao - 广告
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Repository
public class CompanyBankDaoImpl extends BaseDaoImpl<CompanyBank, Long> implements CompanyBankDao {

    @Override
    public CompanyBank get() {

        String jpql = "select companyBank from CompanyBank companyBank order by companyBank.id desc ";
        TypedQuery<CompanyBank> query = entityManager.createQuery(jpql, CompanyBank.class);
        query.setMaxResults(1);
        try {
            return query.getResultList().get(0);
        }catch (Exception e){
            return null;
        }
    }


    @Override
    public void clearDefault() {
        String jpql = "update CompanyBank companyBank set companyBank.isEnabled = false where companyBank.isEnabled = true";
        entityManager.createQuery(jpql).executeUpdate();
    }

    @Override
    public void clearDefault(CompanyBank exclude) {
        Assert.notNull(exclude, "[Assertion failed] - exclude is required; it must not be null");
        String jpql = "update CompanyBank companyBank set companyBank.isEnabled = false where companyBank.isEnabled = true and companyBank != :exclude";
        entityManager.createQuery(jpql).setParameter("exclude", exclude).executeUpdate();
    }

}