
package com.bootx.dao.impl;

import com.bootx.dao.NoticeReadLogDao;
import com.bootx.entity.NoticeReadLog;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;

/**
 * Dao - 广告
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Repository
public class NoticeReadLogDaoImpl extends BaseDaoImpl<NoticeReadLog, Long> implements NoticeReadLogDao {

    @Override
    public NoticeReadLog findByUserId(Long userId) {
        String jpql = "select noticeReadLog from NoticeReadLog noticeReadLog where noticeReadLog.userId =:userId";
        TypedQuery<NoticeReadLog> query = entityManager.createQuery(jpql, NoticeReadLog.class).setParameter("userId",userId);
        try{
            return query.getResultList().get(0);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}