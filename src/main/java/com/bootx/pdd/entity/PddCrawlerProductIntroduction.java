
package com.bootx.pdd.entity;

import com.bootx.entity.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Entity - 会员注册项
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Entity(name = "pdd_CrawlerProductIntroduction")
public class PddCrawlerProductIntroduction extends BaseEntity<Long> {

	@NotNull
	@JoinColumn(updatable = false)
	@OneToOne(fetch = FetchType.LAZY)
	private PddCrawlerProduct crawlerProduct;

	@Lob
	private String content;

	public PddCrawlerProductIntroduction() {
	}

	public PddCrawlerProductIntroduction(PddCrawlerProduct pddCrawlerProduct) {
		this.crawlerProduct = pddCrawlerProduct;
	}

	public PddCrawlerProduct getCrawlerProduct() {
		return crawlerProduct;
	}

	public void setCrawlerProduct(PddCrawlerProduct crawlerProduct) {
		this.crawlerProduct = crawlerProduct;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}