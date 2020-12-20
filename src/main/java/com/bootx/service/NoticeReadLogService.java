
package com.bootx.service;

import com.bootx.entity.NoticeReadLog;

/**
 * Service - 广告
 * 
 * @author 好源++ Team
 * @version 6.1
 */
public interface NoticeReadLogService extends BaseService<NoticeReadLog, Long> {

    NoticeReadLog findByUserId(Long userId);

    NoticeReadLog create(Long userId);
}