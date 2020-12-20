
package com.bootx.service;

import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.entity.AccountCoinLog;

/**
 * Service - 广告
 * 
 * @author 好源++ Team
 * @version 6.1
 */
public interface AccountCoinLogService extends BaseService<AccountCoinLog, Long> {

    Page<AccountCoinLog> findPage(Pageable pageable, Long userId, Integer excision, Integer assetType, String account,Integer state);
}