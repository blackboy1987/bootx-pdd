/*
 * Copyright 2008-2018 shopxx.net. All rights reserved.
 * Support: localhost
 * License: localhost/license
 * FileId: U7I16l1bR0iVulaFB44yszxoUvzo4zB6
 */
package com.bootx.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Security - 当前用户注解
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.PARAMETER)
public @interface CurrentUser {

}