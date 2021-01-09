/*
 * Copyright 2005-2017 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package com.bootx.entity;

import com.bootx.common.BaseAttributeConverter;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Converter;
import javax.validation.Valid;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity - SKU
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
public class Sku implements Serializable {

	@JsonView({BaseEntity.EditView.class})
	private String sn;

	/**
	 * 规格值
	 */
	@Valid
	@Column(length = 4000)
	@Convert(converter = SpecificationValueConverter.class)
	@JsonView({BaseEntity.EditView.class})
	private List<SpecificationValue> specificationValues = new ArrayList<>();

	@JsonView({BaseEntity.EditView.class})
	private Long stock;

	@JsonView({BaseEntity.EditView.class})
	private BigDecimal price;

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	/**
	 * 获取规格值
	 *
	 * @return 规格值
	 */
	public List<SpecificationValue> getSpecificationValues() {
		return specificationValues;
	}

	/**
	 * 设置规格值
	 *
	 * @param specificationValues
	 *            规格值
	 */
	public void setSpecificationValues(List<SpecificationValue> specificationValues) {
		this.specificationValues = specificationValues;
	}

	public Long getStock() {
		return stock;
	}

	public void setStock(Long stock) {
		this.stock = stock;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	/**
	 * 类型转换 - 规格值
	 *
	 * @author IGOMALL  Team
	 * @version 1.0
	 */
	@Converter
	public static class SpecificationValueConverter extends BaseAttributeConverter<List<SpecificationValue>> {
	}
}