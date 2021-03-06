
package com.bootx.entity;

import cn.hutool.core.builder.EqualsBuilder;
import cn.hutool.core.builder.HashCodeBuilder;
import com.fasterxml.jackson.annotation.JsonView;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Entity - 参数值
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
public class ParameterValue implements Serializable {

	private static final long serialVersionUID = 1915986624257267840L;

	/**
	 * 参数组
	 */
	@Length(max = 200)
	@JsonView({BaseEntity.EditView.class})
	private String group;

	/**
	 * 条目
	 */
	@JsonView({BaseEntity.EditView.class})
	private List<Entry> entries = new ArrayList<>();

	/**
	 * 获取参数组
	 * 
	 * @return 参数组
	 */
	public String getGroup() {
		return group;
	}

	/**
	 * 设置参数组
	 * 
	 * @param group
	 *            参数组
	 */
	public void setGroup(String group) {
		this.group = group;
	}

	/**
	 * 获取条目
	 * 
	 * @return 条目
	 */
	public List<Entry> getEntries() {
		return entries;

	}

	/**
	 * 设置条目
	 * 
	 * @param entries
	 *            条目
	 */
	public void setEntries(List<Entry> entries) {
		this.entries = entries;
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
		@JsonView({BaseEntity.EditView.class})
		private String name;

		/**
		 * 值
		 */
		@javax.validation.constraints.NotEmpty
		@Length(max = 200)
		@JsonView({BaseEntity.EditView.class})
		private String value;

		public Entry() {
		}

		public Entry(@javax.validation.constraints.NotEmpty @Length(max = 200) String name, @javax.validation.constraints.NotEmpty @Length(max = 200) String value) {
			this.name = name;
			this.value = value;
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