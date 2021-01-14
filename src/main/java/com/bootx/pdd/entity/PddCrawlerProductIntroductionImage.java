
package com.bootx.pdd.entity;

import com.bootx.common.BaseAttributeConverter;
import com.bootx.entity.BaseEntity;
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
@Entity(name = "pdd_CrawlerProductIntroductionImage")
public class PddCrawlerProductIntroductionImage extends BaseEntity<Long> {

	@OneToOne(fetch = FetchType.LAZY)
	private PddCrawlerProduct crawlerProduct;

	@Lob
	@Convert(converter =ImagesConverter.class)
	@JsonView({EditView.class})
	private List<String> images = new ArrayList<>();

	public PddCrawlerProductIntroductionImage() {
	}

	public PddCrawlerProductIntroductionImage(PddCrawlerProduct crawlerProduct) {
		this.crawlerProduct = crawlerProduct;
	}

	public PddCrawlerProduct getCrawlerProduct() {
		return crawlerProduct;
	}

	public void setCrawlerProduct(PddCrawlerProduct pddCrawlerProduct) {
		this.crawlerProduct = crawlerProduct;
	}

	public List<String> getImages() {
		return images;
	}

	public void setImages(List<String> images) {
		this.images = images;
	}

	@Converter
	public static class ImagesConverter extends BaseAttributeConverter<List<String>> {
	}
}