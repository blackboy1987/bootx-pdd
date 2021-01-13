
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
public class CrawlerProductIntroductionImage extends BaseEntity<Long> {

	@OneToOne(fetch = FetchType.LAZY)
	private CrawlerProduct crawlerProduct;

	@Lob
	@Convert(converter =ImagesConverter.class)
	@JsonView({EditView.class})
	private List<String> images = new ArrayList<>();

	public CrawlerProductIntroductionImage() {
	}

	public CrawlerProductIntroductionImage(CrawlerProduct crawlerProduct) {
		this.crawlerProduct = crawlerProduct;
	}

	public CrawlerProduct getCrawlerProduct() {
		return crawlerProduct;
	}

	public void setCrawlerProduct(CrawlerProduct crawlerProduct) {
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