
package com.bootx.eth.common;

import com.fasterxml.jackson.annotation.JsonView;
import net.bdsc.entity.BaseEntity;

/**
 * 消息
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
public class Result {

	@JsonView({BaseEntity.ViewView.class, BaseEntity.ListView.class, BaseEntity.EditView.class, BaseEntity.BaseView.class})
	private Integer code;

	private Integer type;

	private String content;

	@JsonView({BaseEntity.ViewView.class, BaseEntity.ListView.class, BaseEntity.EditView.class, BaseEntity.BaseView.class})
	private String message;

	@JsonView({BaseEntity.ViewView.class, BaseEntity.ListView.class, BaseEntity.EditView.class, BaseEntity.BaseView.class})
	private Object data;


	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	/**
	 * 构造方法
	 */
	public Result() {
	}

	public Result(Integer type,Integer code, String message,String content, Object data) {
		this.code = code;
		this.type = type;
		this.message = message;
		this.content = content;
		this.data = data;
	}

	public static Result success(String message, Object data) {
		return new Result(200,0, message,message, data);
	}

	public static Result success(Object data) {
		return new Result(200,0, "请求成功","请求成功", data);
	}


	public static Result error(String message) {
		return new Result(400,-1, message,message, null);
	}
}