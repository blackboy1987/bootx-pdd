
package com.bootx.service;

import com.bootx.entity.MineMachine;

import java.util.List;

/**
 * Service - 广告
 * 
 * @author 好源++ Team
 * @version 6.1
 */
public interface MineMachineService extends BaseService<MineMachine, Long> {

    List<MineMachine> findListByCoinType(Integer coinType,Integer count);

    List<MineMachine> findListByCoinTypeAndType(Integer coinType,Integer type);

    MineMachine findDefault();

    MineMachine findByProductId(Long productId);
}