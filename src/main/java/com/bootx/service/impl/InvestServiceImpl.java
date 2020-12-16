
package com.bootx.service.impl;
import com.bootx.dao.InvestDao;
import com.bootx.entity.Invest;
import com.bootx.entity.Member;
import com.bootx.entity.MineMachine;
import com.bootx.service.InvestService;
import com.bootx.service.MineMachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
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
    @Autowired
    private MineMachineService mineMachineService;


    @Override
    public List<Invest> findListByCoinType(Member member, Integer coinType) {
        List<MineMachine> mineMachines = mineMachineService.findAll();
        for (MineMachine mineMachine:mineMachines) {
            create(member,mineMachine,coinType);
        }
        return investDao.findListByCoinType(member,coinType);
    }

    @Override
    public void create(Member member, MineMachine mineMachine,Integer coinType) {
        Invest invest = new Invest();
        if(mineMachine!=null){
            invest.setProductId(mineMachine.getId());
            invest.setProductName(mineMachine.getName());
        }else{
            invest.setProductId(0L);
            invest.setProductName("系统");
        }

        invest.setInvest(new BigDecimal("0.001"));
        invest.setFrozenInvest(new BigDecimal("0"));
        invest.setFrozenInvestTemp(new BigDecimal("0"));
        invest.setAllBtc(new BigDecimal("3.2"));
        invest.setAllHpt(new BigDecimal("4.5"));
        invest.setAllEth(new BigDecimal("2.3"));
        invest.setLastEth(new BigDecimal("1.5"));
        invest.setLastBtc(new BigDecimal("2.3"));
        invest.setLastHpt(new BigDecimal("5.5"));
        invest.setLastTime(new Date());
        invest.setFrozenTime(new Date());
        invest.setInvestTime(new Date());
        invest.setReturnMoney(new BigDecimal("0"));
        invest.setReturnDays(0);
        invest.setIsExpire(false);
        invest.setValidity(0L);
        invest.setAllBtcPrice(new BigDecimal("0.3"));
        invest.setAllHptPrice(new BigDecimal("0.4"));
        invest.setLastBtcPrice(new BigDecimal("0.05"));
        invest.setLastHptPrice(new BigDecimal("0.005"));
        invest.setAllEthPrice(new BigDecimal("0.3"));
        invest.setLastEthPrice(new BigDecimal("0.002"));
        invest.setType(0);
        invest.setProfit(new BigDecimal("0"));
        invest.setProfitYear(0L);
        invest.setElectric(new BigDecimal("1.5"));
        invest.setElectricDiscount(new BigDecimal("2.3"));
        invest.setManage(new BigDecimal("2.2"));
        invest.setManageDiscount(new BigDecimal("1.5"));
        invest.setBtcDiscount(new BigDecimal("3.8"));
        invest.setHbtDiscount(new BigDecimal("4.5"));
        invest.setExpireDate(new Date());
        invest.setComeDate(new Date());
        invest.setExpirationDate(new Date());
        invest.setCoinType(coinType);

        invest.setUserId(member.getId());
        invest.setUserName(member.getUsername());
        invest.setPhone(member.getPhone());



        super.save(invest);
    }
}