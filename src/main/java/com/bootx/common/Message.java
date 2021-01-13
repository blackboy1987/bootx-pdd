
package com.bootx.common;

import com.bootx.entity.BaseEntity;
import com.bootx.util.SpringUtils;
import com.fasterxml.jackson.annotation.JsonView;

/**
 * 消息
 * 
 * @author blackboy
 * @version 1.0
 */
public class Message {

	/**
	 * 类型
	 */
	@JsonView({BaseEntity.BaseView.class, BaseEntity.PageView.class, BaseEntity.ListView.class, BaseEntity.EditView.class, BaseEntity.ViewView.class})
	public enum Type {

		/**
		 * 成功
		 */
		success,

		/**
		 * 警告
		 */
		warn,

		/**
		 * 错误
		 */
		error
	}

	/**
	 * 类型
	 */
	private Type type;

	/**
	 * 内容
	 */
	@JsonView({BaseEntity.BaseView.class, BaseEntity.PageView.class, BaseEntity.ListView.class, BaseEntity.EditView.class, BaseEntity.ViewView.class})
	private String content;

	@JsonView({BaseEntity.BaseView.class, BaseEntity.PageView.class, BaseEntity.ListView.class, BaseEntity.EditView.class, BaseEntity.ViewView.class})
	private Object data;

	/**
	 * 构造方法
	 */
	public Message() {
	}

	/**
	 * 构造方法
	 * 
	 * @param type
	 *            类型
	 * @param content
	 *            内容
	 */
	public Message(Type type, String content) {
		this.type = type;
		this.content = content;
	}

	/**
	 * 构造方法
	 * 
	 * @param type
	 *            类型
	 * @param content
	 *            内容
	 */
	public Message(Type type, String content,Object data) {
		this.type = type;
		this.data = data;
		this.content = SpringUtils.getMessage(content);
	}

	/**
	 * 返回成功消息
	 * 
	 * @param content
	 *            内容
	 * @return 成功消息
	 */
	public static Message success(String content) {
		return new Message(Type.success, content);
	}

	/**
	 * 返回成功消息
	 *
	 * @param data
	 *            内容
	 * @return 成功消息
	 */
	public static Message success(Object data) {
		return new Message(Type.success, "请求成功", data);
	}

	/**
	 * 返回警告消息
	 * 
	 * @param content
	 *            内容
	 * @return 警告消息
	 */
	public static Message warn(String content) {
		return new Message(Type.warn, content);
	}

	/**
	 * 返回错误消息
	 * 
	 * @param content
	 *            内容
	 * @return 错误消息
	 */
	public static Message error(String content) {
		return new Message(Type.error, content);
	}


	/**
	 * 返回成功消息
	 *
	 * @param content
	 *            内容
	 * @return 成功消息
	 */
	public static Message success1(String content,Object data) {
		return new Message(Type.success, content,data);
	}

	/**
	 * 返回警告消息
	 *
	 * @param content
	 *            内容
	 * @return 警告消息
	 */
	public static Message warn1(String content,Object data) {
		return new Message(Type.warn, content,data);
	}

	/**
	 * 返回错误消息
	 *
	 * @param content
	 *            内容
	 * @return 错误消息
	 */
	public static Message error1(String content, Object data) {
		return new Message(Type.error, content,data);
	}


	/**
	 * 获取类型
	 * 
	 * @return 类型
	 */
	public Type getType() {
		return type;
	}

	/**
	 * 设置类型
	 * 
	 * @param type
	 *            类型
	 */
	public void setType(Type type) {
		this.type = type;
	}

	/**
	 * 获取内容
	 * 
	 * @return 内容
	 */
	public String getContent() {
		return content;
	}

	/**
	 * 设置内容
	 * 
	 * @param content
	 *            内容
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 重写toString方法
	 * 
	 * @return 字符串
	 */
	@Override
	public String toString() {
		return content;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}