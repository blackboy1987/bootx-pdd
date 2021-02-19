
package com.bootx.service.impl;

import com.bootx.entity.User;
import com.bootx.service.MemberService;
import com.bootx.service.UserService;
import com.bootx.util.JWTUtils;
import com.bootx.util.WebUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.LockModeType;

/**
 * Service - 用户
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User, Long> implements UserService {

	@Resource
	private MemberService memberService;

	@Override
	public User getCurrentAuditor() {
		return null;
	}


	@Override
	@Transactional(readOnly = true)
	public User getCurrent() {
		return getCurrent(null, null);
	}

	@Override
	@Transactional(readOnly = true)
	public <T extends User> T getCurrent(Class<T> userClass) {
		return getCurrent(userClass, null);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public <T extends User> T getCurrent(Class<T> userClass, LockModeType lockModeType) {
		if(StringUtils.equals(userClass.getSimpleName(),"Member")){
			try{
				String token = WebUtils.getRequest().getHeader("token");
				return (T) find(Long.valueOf(JWTUtils.parseToken(token).getId()));
			}catch (Exception e){
				return null;
			}
		}
		return null;
	}

}