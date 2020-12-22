
package com.bootx.service;

import com.bootx.entity.Member;
import com.bootx.entity.UserBind;

/**
 * Service - 广告
 * 
 * @author 好源++ Team
 * @version 6.1
 */
public interface UserBindService extends BaseService<UserBind, Long> {

    UserBind create(Member member, String uuid, String mobile);
}