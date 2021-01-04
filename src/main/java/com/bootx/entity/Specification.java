/*
 * Copyright 2005-2017 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package com.bootx.entity;

import cn.hutool.core.builder.EqualsBuilder;
import cn.hutool.core.builder.HashCodeBuilder;
import com.bootx.common.BaseAttributeConverter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity - 规格
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
@Entity
public class Specification extends OrderedEntity<Long> {

	private static final long serialVersionUID = -6346775052811140926L;

	/**
	 * 名称
	 */
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false)
	private String name;

	/**
	 * 绑定分类
	 */
	@NotNull(groups = Save.class)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, updatable = false)
	private ProductCategory productCategory;

	/**
	 * 可选项
	 */
	@NotEmpty
	@Column(nullable = false, length = 4000)
	@Convert(converter = OptionConverter.class)
	private List<String> options = new ArrayList<>();

	/**
	 * 条目
	 */
	@Valid
	@javax.validation.constraints.NotEmpty
	@Transient
	private List<Specification.Entry> entries = new ArrayList<>();

	/**
	 * 获取名称
	 * 
	 * @return 名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置名称
	 * 
	 * @param name
	 *            名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取绑定分类
	 * 
	 * @return 绑定分类
	 */
	public ProductCategory getProductCategory() {
		return productCategory;
	}

	/**
	 * 设置绑定分类
	 * 
	 * @param productCategory
	 *            绑定分类
	 */
	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}

	/**
	 * 获取可选项
	 * 
	 * @return 可选项
	 */
	public List<String> getOptions() {
		return options;
	}

	/**
	 * 设置可选项
	 * 
	 * @param options
	 *            可选项
	 */
	public void setOptions(List<String> options) {
		this.options = options;
	}

	/**
	 * 获取条目
	 *
	 * @return 条目
	 */
	public List<Specification.Entry> getEntries() {
		return entries;
	}

	/**
	 * 设置条目
	 *
	 * @param entries
	 *            条目
	 */
	public void setEntries(List<Specification.Entry> entries) {
		this.entries = entries;
	}

	/**
	 * 类型转换 - 可选项
	 * 
	 * @author IGOMALL  Team
	 * @version 1.0
	 */
	@Converter
	public static class OptionConverter extends BaseAttributeConverter<List<String>> {
	}



	/**
	 * Entity - 条目
	 *
	 * @author IGOMALL  Team
	 * @version 1.0
	 */
	public static class Entry implements Serializable {

		private static final long serialVersionUID = 4931882586949537777L;

		/**
		 * 名称
		 */
		@javax.validation.constraints.NotEmpty
		@Length(max = 200)
		private String name;

		/**
		 * 值
		 */
		@javax.validation.constraints.NotEmpty
		@Length(max = 200)
		private String value;

		private String img;

		public Entry() {
		}

		public Entry(@javax.validation.constraints.NotEmpty @Length(max = 200) String name, @javax.validation.constraints.NotEmpty @Length(max = 200) String value,String img) {
			this.name = name;
			this.value = value;
			this.img = img;
		}

		/**
		 * 获取名称
		 *
		 * @return 名称
		 */
		public String getName() {
			return name;
		}

		/**
		 * 设置名称
		 *
		 * @param name
		 *            名称
		 */
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

		public String getImg() {
			return img;
		}

		public void setImg(String img) {
			this.img = img;
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
}