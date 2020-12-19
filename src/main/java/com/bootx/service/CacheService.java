
package com.bootx.service;

/**
 * Service - 缓存
 * 
 * @author 好源++ Team
 * @version 6.1
 */
public interface CacheService {

	/**
	 * 获取缓存存储路径
	 * 
	 * @return 缓存存储路径
	 */
	String getDiskStorePath();

	/**
	 * 获取缓存数
	 * 
	 * @return 缓存数
	 */
	int getCacheSize();

	boolean smsCodeCacheValidate(String key,String code);

}