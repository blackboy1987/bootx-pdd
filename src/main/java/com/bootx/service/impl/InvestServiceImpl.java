/*
 * Copyright 2008-2018 shopxx.net. All rights reserved.
 * Support: localhost
 * License: localhost/license
 * FileId: poWGbOrrJpWm7FJ3jRPsi1GqLraWxfx4
 */
package com.bootx.service.impl;

import com.bootx.dao.InvestDao;
import com.bootx.entity.Invest;
import com.bootx.entity.Member;
import com.bootx.service.InvestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service - 广告
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Service
public class InvestServiceImpl extends BaseServiceImpl<Invest, Long> implements InvestService {

    @Autowired
    private InvestDao investDao;

    @Override
    public List<Invest> findListByCoinType(Member member, Integer coinType) {
        return investDao.findListByCoinType(member,coinType);
    }

    @Override
    public void create(Member member) {
        Invest invest = new Invest();
        invest.setUserId(member.getId());
        invest.setUserName(member.getUsername());
        invest.setPhone(member.getPhone());


        super.save(invest);
    }
}