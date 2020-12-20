
package com.bootx.service.impl;

import com.bootx.dao.NoticeReadLogDao;
import com.bootx.entity.NoticeReadLog;
import com.bootx.service.NoticeReadLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Service - 广告
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Service
public class NoticeReadLogServiceImpl extends BaseServiceImpl<NoticeReadLog, Long> implements NoticeReadLogService {

    @Autowired
    private NoticeReadLogDao noticeReadLogDao;

    @Override
    public NoticeReadLog findByUserId(Long userId) {
        return noticeReadLogDao.findByUserId(userId);
    }

    @Override
    public NoticeReadLog create(Long userId) {
        NoticeReadLog noticeReadLog = new NoticeReadLog();
        noticeReadLog.setUserId(userId);
        noticeReadLog.setNoticeIds(new ArrayList<>());
        return super.save(noticeReadLog);
    }
}