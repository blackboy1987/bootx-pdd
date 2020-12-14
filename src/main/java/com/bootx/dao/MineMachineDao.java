
package com.bootx.dao;


import com.bootx.entity.MineMachine;

import java.util.List;

/**
 * Dao - 广告
 * 
 * @author 好源++ Team
 * @version 6.1
 */
public interface MineMachineDao extends BaseDao<MineMachine, Long> {

    List<MineMachine> findListByCoinType(Integer coinType,Integer count);

    List<MineMachine> findListByCoinTypeAndType(Integer coinType, Integer type);

    MineMachine findDefault();
}