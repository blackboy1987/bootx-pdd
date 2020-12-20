
package com.bootx.dao;

import com.bootx.entity.NoticeReadLog;

/**
 * Dao - 广告
 * 
 * @author 好源++ Team
 * @version 6.1
 */
public interface NoticeReadLogDao extends BaseDao<NoticeReadLog, Long> {

    NoticeReadLog findByUserId(Long userId);
}