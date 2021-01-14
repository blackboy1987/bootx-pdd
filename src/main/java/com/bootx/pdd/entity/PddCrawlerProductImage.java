
package com.bootx.pdd.entity;

import com.bootx.common.BaseAttributeConverter;
import com.bootx.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity - 商品图片
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
@Entity(name = "pdd_CrawlerProductImage")
public class PddCrawlerProductImage extends BaseEntity<Long> {

	@OneToOne(fetch = FetchType.LAZY)
	private PddCrawlerProduct crawlerProduct;

	@Lob
	@Convert(converter = PddCrawlerProductIntroductionImage.ImagesConverter.class)
	@JsonView({EditView.class})
	private List<String> images = new ArrayList<>();

	public PddCrawlerProductImage() {
	}

	public PddCrawlerProductImage(PddCrawlerProduct crawlerProduct) {
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