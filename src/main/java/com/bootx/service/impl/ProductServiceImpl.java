
package com.bootx.service.impl;

import com.bootx.entity.Product;
import com.bootx.plugin.CrawlerPlugin;
import com.bootx.service.CrawlerLogService;
import com.bootx.service.CrawlerUrlLogService;
import com.bootx.service.PluginService;
import com.bootx.service.ProductService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Service - 审计日志
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Service
public class ProductServiceImpl extends BaseServiceImpl<Product, Long> implements ProductService {

	@Resource
	private PluginService pluginService;
	@Resource
	private CrawlerUrlLogService crawlerUrlLogService;
	@Resource
	private CrawlerLogService crawlerLogService;

	@Override
	@Async
	public List<Product> crawler(String[] urls, Integer type) {
		crawlerLogService.save(urls, type);
		List<Product> products = new ArrayList<>();
		for (String url:urls) {
			if(StringUtils.contains(url,"taobao.com")){
				CrawlerPlugin crawlerPlugin = pluginService.getCrawlerPlugin("taobaoPlugin");
				Product product = crawlerPlugin.product(url);
				product.setPluginId("taobaoPlugin");
				products.add(product);
			}else if(StringUtils.contains(url,"1688.com")){
				CrawlerPlugin crawlerPlugin = pluginService.getCrawlerPlugin("oneSixEightEightPlugin");
				Product product = crawlerPlugin.product(url);
				product.setPluginId("oneSixEightEightPlugin");
				products.add(product);
			}else if(StringUtils.contains(url,"jd.com")){
				CrawlerPlugin crawlerPlugin = pluginService.getCrawlerPlugin("jdPlugin");
				Product product = crawlerPlugin.product(url);
				product.setPluginId("jdPlugin");
				products.add(product);
			}else if(StringUtils.contains(url,"suning.com")){
				CrawlerPlugin crawlerPlugin = pluginService.getCrawlerPlugin("suningPlugin");
				Product product = crawlerPlugin.product(url);
				product.setPluginId("suningPlugin");
				products.add(product);
			}else if(StringUtils.contains(url,"tmall.com")){
				CrawlerPlugin crawlerPlugin = pluginService.getCrawlerPlugin("tMallPlugin");
				Product product = crawlerPlugin.product(url);
				products.add(product);
				product.setPluginId("tMallPlugin");
			}
		}
		return products;
	}
}