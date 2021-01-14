
package com.bootx.pdd.entity;

import cn.hutool.core.builder.EqualsBuilder;
import cn.hutool.core.builder.HashCodeBuilder;
import com.bootx.common.BaseAttributeConverter;
import com.bootx.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Converter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity - 规格
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
public class PddCrawlerSpecification implements Serializable {

	/**
	 * 名称
	 */
	@JsonView({BaseEntity.EditView.class})
	private String name;

	@Column(length = 2000)
	@Convert(converter = OptionConverter.class)
	@JsonView({BaseEntity.EditView.class})
	private List<String> options = new ArrayList<>();

	@Column(length = 6000)
	@Convert(converter = SpecificationEntityConverter.class)
	@JsonView({BaseEntity.EditView.class})
	private List<Entry> entries = new ArrayList<>();

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
	 * 类型转换 - 可选项
	 * 
	 * @author IGOMALL  Team
	 * @version 1.0
	 */
	@Converter
	public static class OptionConverter extends BaseAttributeConverter<List<String>> {
	}

	/**
	 * 类型转换 - 规格项
	 *
	 * @author IGOMALL  Team
	 * @version 1.0
	 */
	@Converter
	public static class SpecificationEntityConverter extends BaseAttributeConverter<List<Entry>> {
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