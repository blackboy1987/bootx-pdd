/*
 * Copyright 2008-2018 shopxx.net. All rights reserved.
 * Support: localhost
 * License: localhost/license
 * FileId: CR5FbOmEEM2qB0hoFKw+i2Uwzxr1mY9m
 */
package com.bootx.service.impl;

import com.bootx.common.Setting;
import com.bootx.common.TemplateConfig;
import com.bootx.entity.Member;
import com.bootx.entity.MessageConfig;
import com.bootx.service.MessageConfigService;
import com.bootx.service.SmsService;
import com.bootx.util.JsonUtils;
import com.bootx.util.SecurityUtils;
import com.bootx.util.SystemUtils;
import com.bootx.util.WebUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.http.Header;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.message.BasicHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.util.Assert;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Service - 短信
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Service
public class SmsServiceImpl implements SmsService {

	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;
	@Autowired
	private TaskExecutor taskExecutor;
	@Autowired
	private MessageConfigService messageConfigService;

	/**
	 * 添加短信发送任务
	 * 
	 * @param mobiles
	 *            手机号码
	 * @param content
	 *            内容
	 * @param sendTime
	 *            发送时间
	 */
	private void addSendTask(final String[] mobiles, final String content, final Date sendTime) {
		taskExecutor.execute(new Runnable() {
			@Override
			public void run() {
				send(mobiles, content, sendTime);
			}
		});
	}

	/**
	 * 发送短信
	 * 
	 * @param mobiles
	 *            手机号码
	 * @param content
	 *            内容
	 * @param sendTime
	 *            发送时间
	 */
	private void send(String[] mobiles, String content, Date sendTime) {
		Assert.notEmpty(mobiles, "[Assertion failed] - mobiles must not be empty: it must contain at least 1 element");
		Assert.hasText(content, "[Assertion failed] - content must have text; it must not be null, empty, or blank");

		Setting setting = SystemUtils.getSetting();
		String smsAppId = setting.getSmsAppId();
		String smsSecretKey = setting.getSmsSecretKey();
		if (StringUtils.isEmpty(smsAppId) || StringUtils.isEmpty(smsSecretKey)) {
			return;
		}

		try {
			SecretKeySpec secretKeySpec = new SecretKeySpec(smsSecretKey.getBytes(), SecurityUtils.AES_KEY_ALGORITHM);
			Map<String, Object> parameterMap = new HashMap<>();
			parameterMap.put("mobiles", mobiles);
			parameterMap.put("content", content);
			parameterMap.put("requestTime", new Date().getTime());
			parameterMap.put("requestValidPeriod", 60);
			if (sendTime != null) {
				parameterMap.put("timerTime", DateFormatUtils.format(sendTime, "yyyy-MM-dd HH:mm:ss"));
			}
			byte[] encodedParameter = SecurityUtils.encrypt(secretKeySpec, JsonUtils.toJson(parameterMap).getBytes("UTF-8"), SecurityUtils.AES_TRANSFORMATION);
			Header header = new BasicHeader("appId", smsAppId);
			WebUtils.post("http://shmtn.b2m.cn/inter/sendBatchOnlySMS", header, new ByteArrayEntity(encodedParameter));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public void send(String[] mobiles, String content, Date sendTime, boolean async) {
		Assert.notEmpty(mobiles, "[Assertion failed] - mobiles must not be empty: it must contain at least 1 element");
		Assert.hasText(content, "[Assertion failed] - content must have text; it must not be null, empty, or blank");

		if (async) {
			addSendTask(mobiles, content, sendTime);
		} else {
			send(mobiles, content, sendTime);
		}
	}

	@Override
	public void send(String[] mobiles, String templatePath, Map<String, Object> model, Date sendTime, boolean async) {
		Assert.notEmpty(mobiles, "[Assertion failed] - mobiles must not be empty: it must contain at least 1 element");
		Assert.hasText(templatePath, "[Assertion failed] - templatePath must have text; it must not be null, empty, or blank");

		try {
			Configuration configuration = freeMarkerConfigurer.getConfiguration();
			Template template = configuration.getTemplate(templatePath);
			String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
			send(mobiles, content, sendTime, async);
		} catch (TemplateException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public void send(String mobile, String content) {
		Assert.hasText(mobile, "[Assertion failed] - mobile must have text; it must not be null, empty, or blank");
		Assert.hasText(content, "[Assertion failed] - content must have text; it must not be null, empty, or blank");

		send(new String[] { mobile }, content, null, true);
	}

	@Override
	public void send(String mobile, String templatePath, Map<String, Object> model) {
		Assert.hasText(mobile, "[Assertion failed] - mobile must have text; it must not be null, empty, or blank");
		Assert.hasText(templatePath, "[Assertion failed] - templatePath must have text; it must not be null, empty, or blank");

		send(new String[] { mobile }, templatePath, model, null, true);
	}

	@Override
	public void sendRegisterMemberSms(Member member) {
		if (member == null || StringUtils.isEmpty(member.getMobile())) {
			return;
		}
		MessageConfig messageConfig = messageConfigService.find(MessageConfig.Type.REGISTER_MEMBER);
		if (messageConfig == null || !messageConfig.getIsSmsEnabled()) {
			return;
		}
		Map<String, Object> model = new HashMap<>();
		model.put("member", member);
		TemplateConfig templateConfig = SystemUtils.getTemplateConfig("registerMemberSms");
		send(member.getMobile(), templateConfig.getTemplatePath(), model);
	}

	@Override
	public long getBalance() {
		Setting setting = SystemUtils.getSetting();
		String smsAppId = setting.getSmsAppId();
		String smsSecretKey = setting.getSmsSecretKey();

		Assert.hasText(smsAppId, "[Assertion failed] - smsAppId must have text; it must not be null, empty, or blank");
		Assert.hasText(smsSecretKey, "[Assertion failed] - smsSecretKey must have text; it must not be null, empty, or blank");

		try {
			SecretKeySpec secretKeySpec = new SecretKeySpec(smsSecretKey.getBytes(), SecurityUtils.AES_KEY_ALGORITHM);
			Map<String, Object> parameterMap = new HashMap<>();
			parameterMap.put("requestTime", new Date().getTime());
			parameterMap.put("requestValidPeriod", 60);
			byte[] encodedParameter = SecurityUtils.encrypt(secretKeySpec, JsonUtils.toJson(parameterMap).getBytes("UTF-8"), SecurityUtils.AES_TRANSFORMATION);
			Header header = new BasicHeader("appId", smsAppId);
			byte[] byteArrayResult = WebUtils.post("http://bjmtn.b2m.cn/inter/getBalance", header, new ByteArrayEntity(encodedParameter), byte[].class);
			if (ArrayUtils.isEmpty(byteArrayResult)) {
				throw new ConnectException();
			}

			String result = new String(SecurityUtils.decrypt(secretKeySpec, byteArrayResult, SecurityUtils.AES_TRANSFORMATION));
			Map<String, Object> data = JsonUtils.toObject(result, new TypeReference<Map<String, Object>>() {
			});
			return Long.parseLong(data.get("balance").toString());
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

}