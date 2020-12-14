/*
 * Copyright 2008-2018 shopxx.net. All rights reserved.
 * Support: localhost
 * License: localhost/license
 * FileId: NBcohlMuQa0ncQlHrrCNxV/Q1DkO0t4V
 */
package com.bootx.controller;

import com.bootx.controller.admin.BaseController;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller - 首页
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@RestController
@RequestMapping("/")
public class IndexController extends BaseController {

	/**
	 * 首页
	 */
	@GetMapping
	public String index(ModelMap model) {
		return "shop/index";
	}

}