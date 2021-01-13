
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
public class CrawlerProductSku extends BaseEntity<Long> {

	@OneToOne(fetch = FetchType.LAZY)
	private CrawlerProduct crawlerProduct;

	@Lob
	@Convert(converter = SkuConverter.class)
	@JsonView({EditView.class})
	private List<Sku> skus = new ArrayList<>();

	public CrawlerProductSku() {
	}

	public CrawlerProductSku(CrawlerProduct crawlerProduct) {
		this.crawlerProduct = crawlerProduct;
	}

	public CrawlerProduct getCrawlerProduct() {
		return crawlerProduct;
	}

	public void setCrawlerProduct(CrawlerProduct crawlerProduct) {
		this.crawlerProduct = crawlerProduct;
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