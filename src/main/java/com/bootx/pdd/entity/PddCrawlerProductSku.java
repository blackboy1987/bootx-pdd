
package com.bootx.pdd.entity;

import com.bootx.common.BaseAttributeConverter;
import com.bootx.entity.BaseEntity;
import com.bootx.entity.Sku;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity - 会员注册项
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Entity(name = "pdd_CrawlerProductSku")
public class PddCrawlerProductSku extends BaseEntity<Long> {

	@NotNull
	@JoinColumn(updatable = false)
	@OneToOne(fetch = FetchType.LAZY)
	private PddCrawlerProduct crawlerProduct;

	@Lob
	@Convert(converter = SkuConverter.class)
	@JsonView({EditView.class})
	private List<Sku> skus = new ArrayList<>();

	public PddCrawlerProductSku() {
	}

	public PddCrawlerProductSku(PddCrawlerProduct crawlerProduct) {
		this.crawlerProduct = crawlerProduct;
	}

	public PddCrawlerProduct getCrawlerProduct() {
		return crawlerProduct;
	}

	public void setCrawlerProduct(PddCrawlerProduct crawlerProduct) {
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