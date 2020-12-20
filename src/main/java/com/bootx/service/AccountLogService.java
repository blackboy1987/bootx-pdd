
package com.bootx.service;

import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.entity.AccountLog;

/**
 * Service - 广告
 * 
 * @author 好源++ Team
 * @version 6.1
 */
public interface AccountLogService extends BaseService<AccountLog, Long> {

    Page<AccountLog> findPage(Pageable pageable, Long userId, Integer dataType);
}