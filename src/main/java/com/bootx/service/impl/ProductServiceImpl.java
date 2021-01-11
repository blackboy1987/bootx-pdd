
package com.bootx.service.impl;

import com.bootx.dao.ProductDao;
import com.bootx.entity.CrawlerLog;
import com.bootx.entity.Product;
import com.bootx.plugin.CrawlerPlugin;
import com.bootx.service.CrawlerUrlLogService;
import com.bootx.service.PluginService;
import com.bootx.service.ProductCategoryService;
import com.bootx.service.ProductService;
import com.bootx.util.CrawlerUtils;
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
	private ProductDao productDao;

	@Resource
	private CrawlerUrlLogService crawlerUrlLogService;
	@Resource
	private ProductCategoryService productCategoryService;

	@Override
	public List<Product> crawler(CrawlerLog crawlerLog, String[] urls, Integer type) {
		List<Product> products = new ArrayList<>();
		for (String url:urls) {
			Product product1 = findByUrl(url);
			if(product1!=null){
				products.add(product1);
				crawlerUrlLogService.updateInfo(url,product1,crawlerLog.getSn(),"采集完成",1);
				continue;
			}
			String pluginId = CrawlerUtils.getPlugInId(url);
			CrawlerPlugin crawlerPlugin = pluginService.getCrawlerPlugin(pluginId);
			if(crawlerPlugin!=null){
				Product product = crawlerPlugin.product(crawlerLog.getMember(),url);
				if(product!=null){
					product.setProductCategory(productCategoryService.findByOtherId(pluginId+"_"+product.getProductCategoryIds().get(product.getProductCategoryIds().size()-1)));
					product.setCrawlerLogSn(crawlerLog.getSn());
					product.setPluginId(pluginId);
					product = save(product);
					products.add(product);
					crawlerUrlLogService.updateInfo(url,product,crawlerLog.getSn(),"采集完成",1);
				}else{
					crawlerUrlLogService.updateInfo(url,null,crawlerLog.getSn(),"采集失败",2);
				}
			}else{
				crawlerUrlLogService.updateInfo(url,crawlerLog.getSn(),"不支持该地址",2);
			}

		}
		return products;
	}

	@Override
	public Product findBySn(String sn) {
		return productDao.find("sn",sn);
	}

	private Product findByUrl(String url) {
		return productDao.find("url",url);

	}
}