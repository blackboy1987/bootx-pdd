
package com.bootx.service;

import com.bootx.entity.Product;

/**
 * Service - 审计日志
 * 
 * @author 好源++ Team
 * @version 6.1
 */
public interface ProductService extends BaseService<Product, Long> {

    Product findBySn(String sn);
}