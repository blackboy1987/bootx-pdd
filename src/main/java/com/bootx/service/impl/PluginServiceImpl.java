/*
 * Copyright 2005-2017 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package com.bootx.service.impl;

import com.bootx.plugin.CrawlerPlugin;
import com.bootx.service.PluginService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Service - 插件
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
@Service
public class PluginServiceImpl implements PluginService {

	@Resource
	private List<CrawlerPlugin> crawlerPlugins = new ArrayList<>();
	@Resource
	private Map<String, CrawlerPlugin> crawlerPluginMap = new HashMap<>();

	@Override
	public List<CrawlerPlugin> getCrawlerPlugins() {
		Collections.sort(crawlerPlugins);
		return crawlerPlugins;
	}

	@Override
	public List<CrawlerPlugin> getCrawlerPlugins(final boolean isEnabled) {
		List<CrawlerPlugin> result = new ArrayList<>();
		CollectionUtils.select(crawlerPlugins, new Predicate() {
			@Override
			public boolean evaluate(Object object) {
				CrawlerPlugin storagePlugin = (CrawlerPlugin) object;
				return storagePlugin.getIsEnabled() == isEnabled;
			}
		}, result);
		Collections.sort(result);
		return result;
	}

	@Override
	public CrawlerPlugin getCrawlerPlugin(String id) {
		return crawlerPluginMap.get(id);
	}

}