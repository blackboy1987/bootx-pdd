
package com.bootx.pdd.dao.impl;

import com.bootx.dao.impl.BaseDaoImpl;
import com.bootx.pdd.dao.AccessTokenDao;
import com.bootx.pdd.entity.AccessToken;
import org.springframework.stereotype.Repository;

/**
 * Dao - 审计日志
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Repository
public class AccessTokenDaoImpl extends BaseDaoImpl<AccessToken, Long> implements AccessTokenDao {

}