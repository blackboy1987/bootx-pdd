/*
 * Copyright 2008-2018 shopxx.net. All rights reserved.
 * Support: localhost
 * License: localhost/license
 * FileId: eFRtBNifYk8ygLB911URNCOLWnwQ2Ux5
 */
package com.bootx.audit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Audit - 审计注解
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.METHOD)
public @interface Audit {

	/**
	 * 动作
	 */
	String action();

}