
package com.bootx.dao;

import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.entity.Member;
import com.bootx.entity.MineMachineOrder;

/**
 * Dao - 广告
 * 
 * @author 好源++ Team
 * @version 6.1
 */
public interface MineMachineOrderDao extends BaseDao<MineMachineOrder, Long> {

    Page<MineMachineOrder> findPage(Pageable pageable, Member member, Integer excision, String orderType, String coinType);
}