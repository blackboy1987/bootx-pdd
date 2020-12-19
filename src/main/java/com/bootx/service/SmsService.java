
package com.bootx.service;

import com.bootx.entity.Member;

import java.util.Date;
import java.util.Map;


/**
 * Service - 短信
 * 
 * @author 好源++ Team
 * @version 6.1
 */
public interface SmsService {

	/**
	 * 发送短信
	 * 
	 * @param mobiles
	 *            手机号码
	 * @param content
	 *            内容
	 * @param sendTime
	 *            发送时间
	 * @param async
	 *            是否异步
	 */
	void send(String[] mobiles, String content, Date sendTime, boolean async);

	/**
	 * 发送短信
	 * 
	 * @param mobiles
	 *            手机号码
	 * @param templatePath
	 *            模板路径
	 * @param model
	 *            数据
	 * @param sendTime
	 *            发送时间
	 * @param async
	 *            是否异步
	 */
	void send(String[] mobiles, String templatePath, Map<String, Object> model, Date sendTime, boolean async);

	/**
	 * 发送短信(异步)
	 * 
	 * @param mobile
	 *            手机号码
	 * @param content
	 *            内容
	 */
	void send(String mobile, String content);

	/**
	 * 发送短信(异步)
	 * 
	 * @param mobile
	 *            手机号码
	 * @param templatePath
	 *            模板路径
	 * @param model
	 *            数据
	 */
	void send(String mobile, String templatePath, Map<String, Object> model);

	/**
	 * 发送会员注册短信(异步)
	 * 
	 * @param member
	 *            会员
	 */
	void sendRegisterMemberSms(Member member);
	/**
	 * 获取短信余额
	 * 
	 * @return 短信余额
	 */
	long getBalance();

}