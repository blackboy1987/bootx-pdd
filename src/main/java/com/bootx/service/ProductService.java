
package com.bootx.service;

import com.bootx.entity.CrawlerLog;
import com.bootx.entity.Product;

import java.util.List;

/**
 * Service - 审计日志
 * 
 * @author 好源++ Team
 * @version 6.1
 */
public interface ProductService extends BaseService<Product, Long> {

	List<Product> crawler(CrawlerLog crawlerLog,String[] urls, Integer type);

    Product findBySn(String sn);
}