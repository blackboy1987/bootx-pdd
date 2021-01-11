/*
 * Copyright 2005-2017 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package com.bootx.entity;
import com.bootx.common.BaseAttributeConverter;
import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Entity - 商品
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
@Entity
public class Product extends BaseEntity<Long> {

	private static final long serialVersionUID = -6977025562650112419L;

	@JsonView({PageView.class,EditView.class})
	private String sn;

	@Transient
	private String crawlerLogSn;

	/**
	 * 名称
	 */
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false)
	@JsonView({PageView.class,EditView.class})
	private String name;

	@JsonView({PageView.class,EditView.class})
	private String price;

	/**
	 * 商品图片
	 */
	@Lob
	@Convert(converter = ProductImageConverter.class)
	@JsonView({EditView.class})
	private List<ProductImage> productImages = new ArrayList<>();

	/**
	 * 介绍
	 */
	@OneToOne(mappedBy = "product",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private ProductIntroduction productIntroduction;

	@OneToOne(mappedBy = "product",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JsonView({EditView.class})
	private ProductIntroductionImage productIntroductionImage;


	@OneToOne(mappedBy = "product",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JsonView({EditView.class})
	private ProductParameterValue productParameterValue;

	@OneToOne(mappedBy = "product",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JsonView({EditView.class})
	private ProductSku productSku;

	/**
	 * 规格项
	 */
	@Valid
	@Column(length = 4000)
	@Convert(converter = SpecificationConverter.class)
	@JsonView({EditView.class})
	private List<Specification> specifications = new ArrayList<>();
	/**
	 * 商品分类
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	private ProductCategory productCategory;

	@Column(length = 60)
	@Convert(converter = ProductCategoryIdConverter.class)
	private List<Long> productCategoryIds = new ArrayList<>();

	@Column(length = 300)
	@Convert(converter = ProductCategoryNamesConverter.class)
	@JsonView({PageView.class})
	private List<String> productCategoryNames = new ArrayList<>();



	@JsonView({EditView.class})
	private Long stock;

	@JsonView({PageView.class})
	private String pluginId;

	@JsonView({PageView.class})
	private String url;

	@Column(length = 100)
	@Convert(converter = ProductStoreConverter.class)
	@JsonView({EditView.class})
	private ProductStore productStore;


	public Product() {
		setSn("");
		setCrawlerLogSn("");
		setName("");
		setPrice("");
		setProductImages(new ArrayList<>());
		setProductIntroduction(new ProductIntroduction(this));
		setProductParameterValue(new ProductParameterValue(this));
		setProductSku(new ProductSku(this));
		setSpecifications(new ArrayList<>());
		setProductCategory(new ProductCategory());
		setProductCategoryIds(new ArrayList<>());
		setProductCategoryNames(new ArrayList<>());
		setProductStore(new ProductStore());
		setStock(0L);
		setPluginId("");
		setUrl("");
		setProductIntroductionImage(new ProductIntroductionImage(this));

	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getCrawlerLogSn() {
		return crawlerLogSn;
	}

	public void setCrawlerLogSn(String crawlerLogSn) {
		this.crawlerLogSn = crawlerLogSn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public List<ProductImage> getProductImages() {
		return productImages;
	}

	public void setProductImages(List<ProductImage> productImages) {
		this.productImages = productImages;
	}

	public ProductIntroduction getProductIntroduction() {
		return productIntroduction;
	}

	public void setProductIntroduction(ProductIntroduction productIntroduction) {
		this.productIntroduction = productIntroduction;
	}

	public ProductParameterValue getProductParameterValue() {
		return productParameterValue;
	}

	public void setProductParameterValue(ProductParameterValue productParameterValue) {
		this.productParameterValue = productParameterValue;
	}

	public ProductSku getProductSku() {
		return productSku;
	}

	public void setProductSku(ProductSku productSku) {
		this.productSku = productSku;
	}

	public List<Specification> getSpecifications() {
		return specifications;
	}

	public void setSpecifications(List<Specification> specifications) {
		this.specifications = specifications;
	}

	public ProductCategory getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}

	public List<Long> getProductCategoryIds() {
		return productCategoryIds;
	}

	public void setProductCategoryIds(List<Long> productCategoryIds) {
		this.productCategoryIds = productCategoryIds;
	}

	public List<String> getProductCategoryNames() {
		return productCategoryNames;
	}

	public void setProductCategoryNames(List<String> productCategoryNames) {
		this.productCategoryNames = productCategoryNames;
	}

	public ProductStore getProductStore() {
		return productStore;
	}

	public void setProductStore(ProductStore productStore) {
		this.productStore = productStore;
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}


	@Transient
	@JsonView({PageView.class})
	public String getImage(){
		return getProductImages().get(0).getThumbnail();
	}

	public ProductIntroductionImage getProductIntroductionImage() {
		return productIntroductionImage;
	}

	public void setProductIntroductionImage(ProductIntroductionImage productIntroductionImage) {
		this.productIntroductionImage = productIntroductionImage;
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
	 * 类型转换 - 参数值
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


	/**
	 * 类型转换 - 规格项
	 *
	 * @author IGOMALL  Team
	 * @version 1.0
	 */
	@Converter
	public static class SkuConverter extends BaseAttributeConverter<List<Sku>> {
	}
	@Converter
	public static class ProductCategoryIdConverter extends BaseAttributeConverter<List<Long>> {
	}
	@Converter
	public static class ProductCategoryNamesConverter extends BaseAttributeConverter<List<String>> {
	}
	@Converter
	public static class ProductStoreConverter extends BaseAttributeConverter<ProductStore> {
	}




}