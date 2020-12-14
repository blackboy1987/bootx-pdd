/*
 * Copyright 2008-2018 shopxx.net. All rights reserved.
 * Support: localhost
 * License: localhost/license
 * FileId: ejXNbjAa7/jxNWKZtyljM4aj1bziaCV+
 */
package com.bootx.service.impl;

import com.bootx.common.Setting;
import com.bootx.common.TemplateConfig;
import com.bootx.entity.Member;
import com.bootx.entity.MessageConfig;
import com.bootx.entity.SafeKey;
import com.bootx.entity.User;
import com.bootx.service.MailService;
import com.bootx.service.MessageConfigService;
import com.bootx.util.SpringUtils;
import com.bootx.util.SystemUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.util.Assert;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Service - 邮件
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Service
public class MailServiceImpl implements MailService {

	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;
	@Autowired
	private TaskExecutor taskExecutor;
	@Autowired
	private MessageConfigService messageConfigService;

	/**
	 * 添加邮件发送任务
	 * 
	 * @param smtpHost
	 *            SMTP服务器地址
	 * @param smtpPort
	 *            SMTP服务器端口
	 * @param smtpUsername
	 *            SMTP用户名
	 * @param smtpPassword
	 *            SMTP密码
	 * @param smtpSSLEnabled
	 *            SMTP是否启用SSL
	 * @param smtpFromMail
	 *            发件人邮箱
	 * @param toMails
	 *            收件人邮箱
	 * @param subject
	 *            主题
	 * @param content
	 *            内容
	 */
	private void addSendTask(final String smtpHost, final int smtpPort, final String smtpUsername, final String smtpPassword, final boolean smtpSSLEnabled, final String smtpFromMail, final String[] toMails, final String subject, final String content) {
		taskExecutor.execute(new Runnable() {
			public void run() {
				send(smtpHost, smtpPort, smtpUsername, smtpPassword, smtpSSLEnabled, smtpFromMail, toMails, subject, content);
			}
		});
	}

	/**
	 * 发送邮件
	 * 
	 * @param smtpHost
	 *            SMTP服务器地址
	 * @param smtpPort
	 *            SMTP服务器端口
	 * @param smtpUsername
	 *            SMTP用户名
	 * @param smtpPassword
	 *            SMTP密码
	 * @param smtpSSLEnabled
	 *            SMTP是否启用SSL
	 * @param smtpFromMail
	 *            发件人邮箱
	 * @param toMails
	 *            收件人邮箱
	 * @param subject
	 *            主题
	 * @param content
	 *            内容
	 */
	private void send(String smtpHost, int smtpPort, String smtpUsername, String smtpPassword, boolean smtpSSLEnabled, String smtpFromMail, String[] toMails, String subject, String content) {
		Assert.hasText(smtpHost, "[Assertion failed] - smtpHost must have text; it must not be null, empty, or blank");
		Assert.hasText(smtpUsername, "[Assertion failed] - smtpUsername must have text; it must not be null, empty, or blank");
		Assert.hasText(smtpPassword, "[Assertion failed] - smtpPassword must have text; it must not be null, empty, or blank");
		Assert.hasText(smtpFromMail, "[Assertion failed] - smtpFromMail must have text; it must not be null, empty, or blank");
		Assert.notEmpty(toMails, "[Assertion failed] - toMails must not be empty: it must contain at least 1 element");
		Assert.hasText(subject, "[Assertion failed] - subject must have text; it must not be null, empty, or blank");
		Assert.hasText(content, "[Assertion failed] - content must have text; it must not be null, empty, or blank");

		try {
			Setting setting = SystemUtils.getSetting();
			HtmlEmail email = new HtmlEmail();
			email.setHostName(smtpHost);
			email.setSmtpPort(smtpPort);
			email.setAuthentication(smtpUsername, smtpPassword);
			email.setSSLOnConnect(smtpSSLEnabled);
			email.setFrom(smtpFromMail, setting.getSiteName());
			email.addTo(toMails);
			email.setSubject(subject);
			email.setCharset("UTF-8");
			email.setHtmlMsg(content);
			email.send();
		} catch (EmailException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public void send(String smtpHost, int smtpPort, String smtpUsername, String smtpPassword, boolean smtpSSLEnabled, String smtpFromMail, String[] toMails, String subject, String content, boolean async) {
		Assert.hasText(smtpHost, "[Assertion failed] - smtpHost must have text; it must not be null, empty, or blank");
		Assert.hasText(smtpUsername, "[Assertion failed] - smtpUsername must have text; it must not be null, empty, or blank");
		Assert.hasText(smtpPassword, "[Assertion failed] - smtpPassword must have text; it must not be null, empty, or blank");
		Assert.hasText(smtpFromMail, "[Assertion failed] - smtpFromMail must have text; it must not be null, empty, or blank");
		Assert.notEmpty(toMails, "[Assertion failed] - toMails must not be empty: it must contain at least 1 element");
		Assert.hasText(subject, "[Assertion failed] - subject must have text; it must not be null, empty, or blank");
		Assert.hasText(content, "[Assertion failed] - content must have text; it must not be null, empty, or blank");

		if (async) {
			addSendTask(smtpHost, smtpPort, smtpUsername, smtpPassword, smtpSSLEnabled, smtpFromMail, toMails, subject, content);
		} else {
			send(smtpHost, smtpPort, smtpUsername, smtpPassword, smtpSSLEnabled, smtpFromMail, toMails, subject, content);
		}
	}

	@Override
	public void send(String smtpHost, int smtpPort, String smtpUsername, String smtpPassword, boolean smtpSSLEnabled, String smtpFromMail, String[] toMails, String subject, String templatePath, Map<String, Object> model, boolean async) {
		Assert.hasText(smtpHost, "[Assertion failed] - smtpHost must have text; it must not be null, empty, or blank");
		Assert.hasText(smtpUsername, "[Assertion failed] - smtpUsername must have text; it must not be null, empty, or blank");
		Assert.hasText(smtpPassword, "[Assertion failed] - smtpPassword must have text; it must not be null, empty, or blank");
		Assert.hasText(smtpFromMail, "[Assertion failed] - smtpFromMail must have text; it must not be null, empty, or blank");
		Assert.notEmpty(toMails, "[Assertion failed] - toMails must not be empty: it must contain at least 1 element");
		Assert.hasText(subject, "[Assertion failed] - subject must have text; it must not be null, empty, or blank");
		Assert.hasText(templatePath, "[Assertion failed] - templatePath must have text; it must not be null, empty, or blank");

		try {
			Configuration configuration = freeMarkerConfigurer.getConfiguration();
			Template template = configuration.getTemplate(templatePath);
			String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
			send(smtpHost, smtpPort, smtpUsername, smtpPassword, smtpSSLEnabled, smtpFromMail, toMails, subject, content, async);
		} catch (TemplateException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public void send(String[] toMails, String subject, String content, boolean async) {
		Assert.notEmpty(toMails, "[Assertion failed] - toMails must not be empty: it must contain at least 1 element");
		Assert.hasText(subject, "[Assertion failed] - subject must have text; it must not be null, empty, or blank");
		Assert.hasText(content, "[Assertion failed] - content must have text; it must not be null, empty, or blank");

		Setting setting = SystemUtils.getSetting();
		String smtpHost = setting.getSmtpHost();
		int smtpPort = setting.getSmtpPort();
		String smtpUsername = setting.getSmtpUsername();
		String smtpPassword = setting.getSmtpPassword();
		boolean smtpSSLEnabled = setting.getSmtpSSLEnabled();
		String smtpFromMail = setting.getSmtpFromMail();
		send(smtpHost, smtpPort, smtpUsername, smtpPassword, smtpSSLEnabled, smtpFromMail, toMails, subject, content, async);
	}

	@Override
	public void send(String[] toMails, String subject, String templatePath, Map<String, Object> model, boolean async) {
		Assert.notEmpty(toMails, "[Assertion failed] - toMails must not be empty: it must contain at least 1 element");
		Assert.hasText(subject, "[Assertion failed] - subject must have text; it must not be null, empty, or blank");
		Assert.hasText(templatePath, "[Assertion failed] - templatePath must have text; it must not be null, empty, or blank");

		Setting setting = SystemUtils.getSetting();
		String smtpHost = setting.getSmtpHost();
		int smtpPort = setting.getSmtpPort();
		String smtpUsername = setting.getSmtpUsername();
		String smtpPassword = setting.getSmtpPassword();
		boolean smtpSSLEnabled = setting.getSmtpSSLEnabled();
		String smtpFromMail = setting.getSmtpFromMail();
		send(smtpHost, smtpPort, smtpUsername, smtpPassword, smtpSSLEnabled, smtpFromMail, toMails, subject, templatePath, model, async);
	}

	@Override
	public void send(String toMail, String subject, String content) {
		Assert.hasText(toMail, "[Assertion failed] - toMail must have text; it must not be null, empty, or blank");
		Assert.hasText(subject, "[Assertion failed] - subject must have text; it must not be null, empty, or blank");
		Assert.hasText(content, "[Assertion failed] - content must have text; it must not be null, empty, or blank");

		send(new String[] { toMail }, subject, content, true);
	}

	@Override
	public void send(String toMail, String subject, String templatePath, Map<String, Object> model) {
		Assert.hasText(toMail, "[Assertion failed] - toMail must have text; it must not be null, empty, or blank");
		Assert.hasText(subject, "[Assertion failed] - subject must have text; it must not be null, empty, or blank");
		Assert.hasText(templatePath, "[Assertion failed] - templatePath must have text; it must not be null, empty, or blank");

		send(new String[] { toMail }, subject, templatePath, model, true);
	}

	@Override
	public void sendTestSmtpMail(String smtpHost, int smtpPort, String smtpUsername, String smtpPassword, boolean smtpSSLEnabled, String smtpFromMail, String toMail) {
		Assert.hasText(smtpHost, "[Assertion failed] - smtpHost must have text; it must not be null, empty, or blank");
		Assert.hasText(smtpUsername, "[Assertion failed] - smtpUsername must have text; it must not be null, empty, or blank");
		Assert.hasText(smtpPassword, "[Assertion failed] - smtpPassword must have text; it must not be null, empty, or blank");
		Assert.hasText(smtpFromMail, "[Assertion failed] - smtpFromMail must have text; it must not be null, empty, or blank");
		Assert.hasText(toMail, "[Assertion failed] - toMail must have text; it must not be null, empty, or blank");

		Setting setting = SystemUtils.getSetting();
		String subject = SpringUtils.getMessage("admin.mail.testSmtpSubject", setting.getSiteName());
		TemplateConfig templateConfig = SystemUtils.getTemplateConfig("testSmtpMail");
		send(smtpHost, smtpPort, smtpUsername, smtpPassword, smtpSSLEnabled, smtpFromMail, new String[] { toMail }, subject, templateConfig.getTemplatePath(), null, false);
	}

	@Override
	public void sendForgotPasswordMail(User user) {
		Setting setting = SystemUtils.getSetting();
		Map<String, Object> model = new HashMap<>();
		SafeKey safeKey = null;
		String toMail = null;
		String username = null;
		User.Type type = null;
		if (user instanceof Member) {
			Member member = (Member) user;
			toMail = member.getEmail();
			username = member.getUsername();
			safeKey = member.getSafeKey();
			type = User.Type.MEMBER;
		}
		if (safeKey == null) {
			return;
		}
		String subject = SpringUtils.getMessage("shop.mail.forgotPasswordSubject", setting.getSiteName());
		TemplateConfig templateConfig = SystemUtils.getTemplateConfig("forgotPasswordMail");
		model.put("type", type);
		model.put("username", username);
		model.put("safeKey", safeKey);
		send(toMail, subject, templateConfig.getTemplatePath(), model);
	}

	@Override
	public void sendRegisterMemberMail(Member member) {
		if (member == null || StringUtils.isEmpty(member.getEmail())) {
			return;
		}
		MessageConfig messageConfig = messageConfigService.find(MessageConfig.Type.REGISTER_MEMBER);
		if (messageConfig == null || !messageConfig.getIsMailEnabled()) {
			return;
		}
		Setting setting = SystemUtils.getSetting();
		Map<String, Object> model = new HashMap<>();
		model.put("member", member);
		String subject = SpringUtils.getMessage("shop.mail.registerMemberSubject", setting.getSiteName());
		TemplateConfig templateConfig = SystemUtils.getTemplateConfig("registerMemberMail");
		send(member.getEmail(), subject, templateConfig.getTemplatePath(), model);
	}

}