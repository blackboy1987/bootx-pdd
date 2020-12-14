/*
 * Copyright 2008-2018 shopxx.net. All rights reserved.
 * Support: localhost
 * License: localhost/license
 * FileId: UAXE3mN51KpQ9RFBznzudiMXYK4X+kzi
 */
package com.bootx.audit;

import com.bootx.entity.AuditLog;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * Audit - 审计日志MethodArgumentResolver
 * 
 * @author 好源++ Team
 * @version 6.1
 */
public class AuditLogMethodArgumentResolver implements HandlerMethodArgumentResolver {

	/**
	 * 支持参数
	 * 
	 * @param methodParameter
	 *            MethodParameter
	 * @return 是否支持参数
	 */
	@Override
	public boolean supportsParameter(MethodParameter methodParameter) {
		return AuditLog.class.isAssignableFrom(methodParameter.getParameterType());
	}

	/**
	 * 解析变量
	 * 
	 * @param methodParameter
	 *            MethodParameter
	 * @param modelAndViewContainer
	 *            ModelAndViewContainer
	 * @param nativeWebRequest
	 *            NativeWebRequest
	 * @param webDataBinderFactory
	 *            WebDataBinderFactory
	 * @return 变量
	 */
	@Override
	public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
		return nativeWebRequest.getAttribute(AuditLog.AUDIT_LOG_ATTRIBUTE_NAME, RequestAttributes.SCOPE_REQUEST);
	}

}