
package com.bootx.service.impl;

import com.bootx.dao.ProductDao;
import com.bootx.entity.Product;
import com.bootx.service.PluginService;
import com.bootx.service.ProductCategoryService;
import com.bootx.service.ProductService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
	private ProductCategoryService productCategoryService;

	@Override
	public Product findBySn(String sn) {
		return productDao.find("sn",sn);
	}

	private Product findByUrl(String url) {
		return productDao.find("url",url);

	}
}