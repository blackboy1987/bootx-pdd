
package com.bootx.pdd.entity;

import com.bootx.common.BaseAttributeConverter;
import com.bootx.entity.BaseEntity;
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
@Entity(name = "pdd_CrawlerProductSpecification")
public class PddCrawlerProductSpecification extends BaseEntity<Long> {

	@NotNull
	@JoinColumn(updatable = false)
	@OneToOne(fetch = FetchType.LAZY)
	private PddCrawlerProduct crawlerProduct;

	@Lob
	@Convert(converter =CrawlerSpecificationConverter.class)
	@JsonView({EditView.class})
	private List<PddCrawlerSpecification> crawlerSpecifications = new ArrayList<>();

	public PddCrawlerProductSpecification() {
		setCrawlerSpecifications(new ArrayList<>());

	}

	public PddCrawlerProductSpecification(PddCrawlerProduct crawlerProduct) {
		this.crawlerProduct = crawlerProduct;
	}

	public PddCrawlerProduct getCrawlerProduct() {
		return crawlerProduct;
	}

	public void setCrawlerProduct(PddCrawlerProduct crawlerProduct) {
		this.crawlerProduct = crawlerProduct;
	}

	public List<PddCrawlerSpecification> getCrawlerSpecifications() {
		return crawlerSpecifications;
	}

	public void setCrawlerSpecifications(List<PddCrawlerSpecification> pddCrawlerSpecifications) {
		this.crawlerSpecifications = pddCrawlerSpecifications;
	}

	@Converter
	public static class CrawlerSpecificationConverter extends BaseAttributeConverter<List<PddCrawlerSpecification>> {
	}
}