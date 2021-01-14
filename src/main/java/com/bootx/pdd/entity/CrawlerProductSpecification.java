
package com.bootx.pdd.entity;

import com.bootx.common.BaseAttributeConverter;
import com.bootx.entity.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity - 会员注册项
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Entity(name = "pdd_CrawlerProductSpecification")
public class CrawlerProductSpecification extends BaseEntity<Long> {

	@OneToOne(fetch = FetchType.LAZY)
	private CrawlerProduct crawlerProduct;

	@Lob
	@Convert(converter =CrawlerSpecificationConverter.class)
	private List<CrawlerSpecification> crawlerSpecifications = new ArrayList<>();

	public CrawlerProductSpecification() {
		setCrawlerSpecifications(new ArrayList<>());

	}

	public CrawlerProductSpecification(CrawlerProduct crawlerProduct) {
		this.crawlerProduct = crawlerProduct;
	}

	public CrawlerProduct getCrawlerProduct() {
		return crawlerProduct;
	}

	public void setCrawlerProduct(CrawlerProduct crawlerProduct) {
		this.crawlerProduct = crawlerProduct;
	}

	public List<CrawlerSpecification> getCrawlerSpecifications() {
		return crawlerSpecifications;
	}

	public void setCrawlerSpecifications(List<CrawlerSpecification> crawlerSpecifications) {
		this.crawlerSpecifications = crawlerSpecifications;
	}

	@Converter
	public static class CrawlerSpecificationConverter extends BaseAttributeConverter<List<CrawlerSpecification>> {
	}
}