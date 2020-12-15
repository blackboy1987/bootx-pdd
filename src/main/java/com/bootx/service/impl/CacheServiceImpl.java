/*
 * Copyright 2008-2018 shopxx.net. All rights reserved.
 * Support: localhost
 * License: localhost/license
 * FileId: 1C3OaPprBQkrIDVVYaWGPwQ/kVmKHYgr
 */
package com.bootx.service.impl;

import com.bootx.service.CacheService;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service - 缓存
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Service
public class CacheServiceImpl implements CacheService {

	@Autowired
	private CacheManager cacheManager;
	private static final Ehcache smsCodeCache = CacheManager.create().getCache("smsCode");

	@Override
	public String getDiskStorePath() {
		return cacheManager.getConfiguration().getDiskStoreConfiguration().getPath();
	}

	@Override
	public int getCacheSize() {
		int cacheSize = 0;
		String[] cacheNames = cacheManager.getCacheNames();
		if (cacheNames != null) {
			for (String cacheName : cacheNames) {
				Ehcache cache = cacheManager.getEhcache(cacheName);
				if (cache != null) {
					cacheSize += cache.getSize();
				}
			}
		}
		return cacheSize;
	}

	@Override
	public boolean smsCodeCacheValidate(String key, String code) {
		if(StringUtils.isBlank(key)||StringUtils.isBlank(code)){
			return false;
		}
		try {
			String cacheCode = smsCodeCache.get(key).getObjectValue().toString();
			if(!StringUtils.equalsIgnoreCase(cacheCode,code)){
				return false;
			}else{
				smsCodeCache.remove(key);
				return true;
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return false;
	}
}