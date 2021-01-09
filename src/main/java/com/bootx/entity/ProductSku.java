
package com.bootx.entity;

import com.bootx.common.BaseAttributeConverter;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity - 会员注册项
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Entity
public class ProductSku extends BaseEntity<Long> {

	@OneToOne(fetch = FetchType.LAZY)
	private Product product;

	@Lob
	@Convert(converter = SkuConverter.class)
	@JsonView({EditView.class})
	private List<Sku> skus = new ArrayList<>();

	public ProductSku() {
	}

	public ProductSku(Product product) {
		this.product = product;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public List<Sku> getSkus() {
		return skus;
	}

	public void setSkus(List<Sku> skus) {
		this.skus = skus;
	}

	@Converter
	public static class SkuConverter extends BaseAttributeConverter<List<Sku>> {
	}
}