
package com.bootx.service.impl;

import com.bootx.entity.Member;
import com.bootx.entity.MineMachine;
import com.bootx.entity.MineMachineOrder;
import com.bootx.entity.Sn;
import com.bootx.service.MineMachineOrderService;
import com.bootx.service.SnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Service - 广告
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Service
public class MineMachineOrderServiceImpl extends BaseServiceImpl<MineMachineOrder, Long> implements MineMachineOrderService {

    @Autowired
    private SnService snService;

    @Override
    public MineMachineOrder create(Member member, MineMachine mineMachine, Integer quantity, Integer day) {
        MineMachineOrder mineMachineOrder = new MineMachineOrder();
        mineMachineOrder.init();
        mineMachineOrder.setUserId(member.getId());
        mineMachineOrder.setProductType(mineMachine.getType());
        mineMachineOrder.setProductId(mineMachine.getId());
        mineMachineOrder.setPrice(mineMachine.getPrice());
        mineMachineOrder.setAmount(mineMachine.getPrice().multiply(new BigDecimal(quantity)));
        mineMachineOrder.setQuantity(quantity);
        mineMachineOrder.setMemo("系统赠送");
        mineMachineOrder.setState(1);
        mineMachineOrder.setPayType(3);
        mineMachineOrder.setPayPrice("1349.80 元");
        mineMachineOrder.setElectricType(1);
        mineMachineOrder. setDay(day);
        mineMachineOrder.setAddElectric(BigDecimal.ZERO);
        mineMachineOrder.setElectricMoney(BigDecimal.valueOf(43.99));
        mineMachineOrder.setRmbPrice(BigDecimal.valueOf(1349.81));
        mineMachineOrder.setCoinType(mineMachine.getCoinType());
        mineMachineOrder.setFromChannel("APP");
        mineMachineOrder.setExpirationDate(new Date());
        mineMachineOrder.setInvestTime("2021-01-01 00:00:00");
        mineMachineOrder.setSn(snService.generate(Sn.Type.ORDER));
        mineMachineOrder.setUserName(member.getUsername());
        mineMachineOrder.setPhone(member.getPhone());
        mineMachineOrder.setName(member.getName());
        mineMachineOrder.setProductName(mineMachine.getName());
        mineMachineOrder.setProductId(mineMachine.getId());
        mineMachineOrder.setProductIcon(mineMachine.getIcon());
        return super.save(mineMachineOrder);
    }
}