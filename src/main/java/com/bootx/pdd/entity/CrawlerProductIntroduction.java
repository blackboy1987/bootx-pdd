
package com.bootx.pdd.entity;

import com.bootx.entity.BaseEntity;

import javax.persistence.*;

/**
 * Entity - 会员注册项
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Entity(name = "pdd_CrawlerProductIntroduction")
public class CrawlerProductIntroduction extends BaseEntity<Long> {

	@OneToOne(fetch = FetchType.LAZY)
	private CrawlerProduct crawlerProduct;

	@Lob
	private String content;

	public CrawlerProductIntroduction() {
	}

	public CrawlerProductIntroduction(CrawlerProduct crawlerProduct) {
		this.crawlerProduct = crawlerProduct;
	}

	public CrawlerProduct getCrawlerProduct() {
		return crawlerProduct;
	}

	public void setCrawlerProduct(CrawlerProduct crawlerProduct) {
		this.crawlerProduct = crawlerProduct;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}