
package com.bootx.dao;

import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.entity.AccountLog;

/**
 * Dao - 广告
 * 
 * @author 好源++ Team
 * @version 6.1
 */
public interface AccountLogDao extends BaseDao<AccountLog, Long> {

    Page<AccountLog> findPage(Pageable pageable, Long userId, Integer dataType);
}