package com.bootx.common;

import com.bootx.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonView;

/**
 * 消息
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
public class Result {

	@JsonView({BaseEntity.ViewView.class, BaseEntity.ListView.class, BaseEntity.EditView.class, BaseEntity.BaseView.class})
	private Integer code;

	@JsonView({BaseEntity.ViewView.class, BaseEntity.ListView.class, BaseEntity.EditView.class, BaseEntity.BaseView.class})
	private String content;

	@JsonView({BaseEntity.ViewView.class, BaseEntity.ListView.class, BaseEntity.EditView.class, BaseEntity.BaseView.class})
	private Object date;


	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Object getDate() {
		return date;
	}

	public void setDate(Object date) {
		this.date = date;
	}

	/**
	 * 构造方法
	 */
	public Result() {
	}

	public Result(Integer code, String content, Object date) {
		this.code = code;
		this.content = content;
		this.date = date;
	}

	public static Result success(String content, Object data) {
		return new Result(0, content, data);
	}

	public static Result ok (Integer code, String content, Object date) {
		return new Result(code,content,date);
	}
	public static Result ok (String content, Object date) {
		return new Result(200,content,date);
	}

	public static Result success(Object data) {
		return new Result(200,"请求成功", data);
	}


	public static Result error(String content) {
		return new Result(400,content, null);
	}
}