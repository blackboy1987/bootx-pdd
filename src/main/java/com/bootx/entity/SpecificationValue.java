/*
 * Copyright 2005-2017 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package com.bootx.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

/**
 * Entity - 规格值
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
public class SpecificationValue implements Serializable {

	private static final long serialVersionUID = 1977183660698901347L;

	/**
	 * ID
	 */
	private String id;

	private String name;

	/**
	 * 值
	 */
	@Length(max = 200)
	private String value;

	public SpecificationValue() {
	}

	public SpecificationValue(String id, @Length(max = 200) String value) {
		this.id = id;
		this.value = value;
	}

	public SpecificationValue(String id, String name,  @Length(max = 200) String value) {
		this.id = id;
		this.name = name;
		this.value = value;
	}

	/**
	 * 获取ID
	 * 
	 * @return ID
	 */
	public String getId() {
		return id;
	}

	/**
	 * 设置ID
	 * 
	 * @param id
	 *            ID
	 */
	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取值
	 * 
	 * @return 值
	 */
	public String getValue() {
		return value;
	}

	/**
	 * 设置值
	 * 
	 * @param value
	 *            值
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * 重写equals方法
	 * 
	 * @param obj
	 *            对象
	 * @return 是否相等
	 */
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

	/**
	 * 重写hashCode方法
	 * 
	 * @return HashCode
	 */
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

}