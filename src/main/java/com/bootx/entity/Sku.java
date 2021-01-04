/*
 * Copyright 2005-2017 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package com.bootx.entity;

import com.bootx.common.BaseAttributeConverter;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity - SKU
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
@Entity
public class Sku extends BaseEntity<Long> {

	private static final long serialVersionUID = 2167830430439593293L;

	/**
	 * 商品
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false, updatable = false)
	private Product product;

	@Transient
	private String sn;

	/**
	 * 规格值
	 */
	@JsonView(BaseView.class)
	@Valid
	@Column(length = 4000)
	@Convert(converter = SpecificationValueConverter.class)
	private List<SpecificationValue> specificationValues = new ArrayList<>();

	private Long stock;

	private BigDecimal price;


	/**
	 * 获取商品
	 * 
	 * @return 商品
	 */
	public Product getProduct() {
		return product;
	}

	/**
	 * 设置商品
	 * 
	 * @param product
	 *            商品
	 */
	public void setProduct(Product product) {
		this.product = product;
	}

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