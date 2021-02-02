/*
 * Copyright 2005-2017 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package com.bootx.controller.admin;

import com.bootx.entity.ProductCategory;
import com.bootx.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller - 商品分类
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
@RestController("adminProductCategoryController")
@RequestMapping("/admin/product_category")
@CrossOrigin
public class ProductCategoryController extends BaseController {

	@Autowired
	private ProductCategoryService productCategoryService;

	/**
	 * 保存
	 */
	@PostMapping("/save")
	public String save(ProductCategory productCategory, Long parentId) {
		productCategory.setParent(productCategoryService.find(parentId));
		productCategory.setTreePath(null);
		productCategory.setGrade(null);
		productCategory.setChildren(null);
		productCategoryService.save(productCategory);
		return "redirect:list";
	}
}