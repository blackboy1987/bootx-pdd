/*
 * Copyright 2005-2017 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package com.bootx.entity;

import com.bootx.common.BaseAttributeConverter;
import com.fasterxml.jackson.annotation.JsonView;
import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.*;

/**
 * Entity - 商品
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
@Entity
public class Product extends BaseEntity<Long> {

	private static final long serialVersionUID = -6977025562650112419L;

	/**
	 * 名称
	 */
	@JsonView(BaseView.class)
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false)
	private String name;

	private String price;

	/**
	 * 商品图片
	 */
	@Valid
	@Column(length = 4000)
	@Convert(converter = ProductImageConverter.class)
	private List<ProductImage> productImages = new ArrayList<>();

	/**
	 * 介绍
	 */
	@Lob
	private String introduction;

	@Length(max = 6000)
	@Convert(converter = IntroductionImagesConverter.class)
	private List<String> introductionImages = new ArrayList<>();

	/**
	 * 参数值
	 */
	@Valid
	@Column(length = 4000)
	@Convert(converter = ParameterValueConverter.class)
	private List<ParameterValue> parameterValues = new ArrayList<>();

	/**
	 * SKU
	 */
	@OneToMany(mappedBy = "product", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	private Set<Sku> skus = new HashSet<>();

	/**
	 * 规格项
	 */
	@Valid
	@Column(length = 4000)
	@Convert(converter = SpecificationConverter.class)
	private List<Specification> specifications = new ArrayList<>();
	/**
	 * 商品分类
	 */
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	private ProductCategory productCategory;

	@Valid
	@Column(length = 4000)
	@Convert(converter = MoreInfoConverter.class)
	@Transient
	private Map<String,Object> moreInfo = new HashMap<>();


	private Long stock;

	private String pluginId;

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取商品图片
	 *
	 * @return 商品图片
	 */
	public List<ProductImage> getProductImages() {
		return productImages;
	}

	/**
	 * 设置商品图片
	 *
	 * @param productImages
	 *            商品图片
	 */
	public void setProductImages(List<ProductImage> productImages) {
		this.productImages = productImages;
	}

	/**
	 * 获取介绍
	 *
	 * @return 介绍
	 */
	public String getIntroduction() {
		return introduction;
	}

	/**
	 * 设置介绍
	 *
	 * @param introduction
	 *            介绍
	 */
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public List<String> getIntroductionImages() {
		return introductionImages;
	}

	public void setIntroductionImages(List<String> introductionImages) {
		this.introductionImages = introductionImages;
	}

	/**
	 * 获取参数值
	 *
	 * @return 参数值
	 */
	public List<ParameterValue> getParameterValues() {
		return parameterValues;
	}

	/**
	 * 设置参数值
	 *
	 * @param parameterValues
	 *            参数值
	 */
	public void setParameterValues(List<ParameterValue> parameterValues) {
		this.parameterValues = parameterValues;
	}

	public Map<String, Object> getMoreInfo() {
		return moreInfo;
	}

	public void setMoreInfo(Map<String, Object> moreInfo) {
		this.moreInfo = moreInfo;
	}

	/**
	 * 获取SKU
	 *
	 * @return SKU
	 */
	public Set<Sku> getSkus() {
		return skus;
	}

	/**
	 * 设置SKU
	 *
	 * @param skus
	 *            SKU
	 */
	public void setSkus(Set<Sku> skus) {
		this.skus = skus;
	}
	/**
	 * 获取规格项
	 *
	 * @return 规格项
	 */
	public List<Specification> getSpecifications() {
		return specifications;
	}

	/**
	 * 设置规格项
	 *
	 * @param specifications
	 *            规格项
	 */
	public void setSpecifications(List<Specification> specifications) {
		this.specifications = specifications;
	}

	/**
	 * 获取商品分类
	 *
	 * @return 商品分类
	 */
	public ProductCategory getProductCategory() {
		return productCategory;
	}

	/**
	 * 设置商品分类
	 *
	 * @param productCategory
	 *            商品分类
	 */
	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}

	public Long getStock() {
		return stock;
	}

	public void setStock(Long stock) {
		this.stock = stock;
	}

	public String getPluginId() {
		return pluginId;
	}

	public void setPluginId(String pluginId) {
		this.pluginId = pluginId;
	}

	/**
	 * 获取缩略图
	 *
	 * @return 缩略图
	 */
	@JsonView(BaseView.class)
	@Transient
	public String getThumbnail() {
		if (CollectionUtils.isEmpty(getProductImages())) {
			return null;
		}
		return getProductImages().get(0).getThumbnail();
	}

	/**
	 * 类型转换 - 商品图片
	 *
	 * @author IGOMALL  Team
	 * @version 1.0
	 */
	@Converter
	public static class ProductImageConverter extends BaseAttributeConverter<List<ProductImage>> {
	}

	/**
	 * 类型转换 - 参数值
	 *
	 * @author IGOMALL  Team
	 * @version 1.0
	 */
	@Converter
	public static class ParameterValueConverter extends BaseAttributeConverter<List<ParameterValue>> {
	}

	/**
	 * 类型转换 - 规格项
	 *
	 * @author IGOMALL  Team
	 * @version 1.0
	 */
	@Converter
	public static class SpecificationConverter extends BaseAttributeConverter<List<Specification>> {
	}

	/**
	 * 类型转换 - 规格项
	 *
	 * @author IGOMALL  Team
	 * @version 1.0
	 */
	@Converter
	public static class MoreInfoConverter extends BaseAttributeConverter<Map<String,Object>> {
	}

	@Converter
	public static class IntroductionImagesConverter extends BaseAttributeConverter<List<String>> {
	}
}