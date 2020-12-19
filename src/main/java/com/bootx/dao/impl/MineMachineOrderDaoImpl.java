/*
 * Copyright 2008-2018 shopxx.net. All rights reserved.
 * Support: localhost
 * License: localhost/license
 * FileId: CxtKZH2JD6mAick4srEhJ3w6VvOXlHh3
 */
package com.bootx.dao.impl;

import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.dao.MineMachineOrderDao;
import com.bootx.entity.Member;
import com.bootx.entity.MineMachineOrder;
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
public class MineMachineOrderDaoImpl extends BaseDaoImpl<MineMachineOrder, Long> implements MineMachineOrderDao {

    @Override
    public Page<MineMachineOrder> findPage(Pageable pageable, Member member, Integer excision, String orderType, String coinType) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<MineMachineOrder> criteriaQuery = criteriaBuilder.createQuery(MineMachineOrder.class);
        Root<MineMachineOrder> root = criteriaQuery.from(MineMachineOrder.class);
        criteriaQuery.select(root);
        Predicate restrictions = criteriaBuilder.conjunction();
        if (member != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("userId"), member.getId()));
        }
        if (excision != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("excision"), excision));
        }
        if (StringUtils.isNotBlank(orderType)&&!StringUtils.equalsIgnoreCase("undefined",orderType)) {
            try{
                restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("orderType"), Integer.valueOf(orderType)));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        if (StringUtils.isNotBlank(coinType)&&!StringUtils.equalsIgnoreCase("undefined",coinType)) {
            try{
                restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("coinType"), Integer.valueOf(coinType)));
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        criteriaQuery.where(restrictions);
        return super.findPage(criteriaQuery, pageable);
    }
}