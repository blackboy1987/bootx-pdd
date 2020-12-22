
package com.bootx.service.impl;

import com.bootx.entity.Member;
import com.bootx.entity.UserBind;
import com.bootx.service.UserBindService;
import org.springframework.stereotype.Service;

/**
 * Service - 广告
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Service
public class UserBindServiceImpl extends BaseServiceImpl<UserBind, Long> implements UserBindService {

    @Override
    public UserBind create(Member member, String uuid, String mobile) {
        UserBind userBind = new UserBind();
        userBind.setUserId(member.getId());
        userBind.setUuid(uuid);
        userBind.setMobile(mobile);
        return super.save(userBind);

    }
}