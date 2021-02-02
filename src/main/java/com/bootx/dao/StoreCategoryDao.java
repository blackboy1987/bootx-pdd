
package com.bootx.dao;

import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.entity.Member;
import com.bootx.entity.StoreCategory;

import java.util.List;

/**
 * Dao - 审计日志
 * 
 * @author 好源++ Team
 * @version 6.1
 */
public interface StoreCategoryDao extends BaseDao<StoreCategory, Long> {

    List<StoreCategory> findList(Member member);

    Page<StoreCategory> findPage(Pageable pageable, Member member);

    StoreCategory findDefault(Member member);
}