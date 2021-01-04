/*
 * Copyright 2005-2017 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package com.bootx.service;

import com.bootx.plugin.CrawlerPlugin;

import java.util.List;

/**
 * Service - 插件
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
public interface PluginService {

	/**
	 * 获取存储插件
	 * 
	 * @return 存储插件
	 */
	List<CrawlerPlugin> getCrawlerPlugins();

	/**
	 * 获取存储插件
	 * 
	 * @param isEnabled
	 *            是否启用
	 * @return 存储插件
	 */
	List<CrawlerPlugin> getCrawlerPlugins(boolean isEnabled);

	/**
	 * 获取存储插件
	 * 
	 * @param id
	 *            ID
	 * @return 存储插件
	 */
	CrawlerPlugin getCrawlerPlugin(String id);

}