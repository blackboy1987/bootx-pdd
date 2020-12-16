
package com.bootx.dao;

import com.bootx.entity.Invest;
import com.bootx.entity.Member;

import java.util.List;

/**
 * Dao - 广告
 * 
 * @author 好源++ Team
 * @version 6.1
 */
public interface InvestDao extends BaseDao<Invest, Long> {

    List<Invest> findListByCoinType(Member member, Integer coinType);
}