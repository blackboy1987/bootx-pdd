
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
public class CrawlerProductImage extends BaseEntity<Long> {

	@OneToOne(fetch = FetchType.LAZY)
	private CrawlerProduct crawlerProduct;

	@Lob
	@Convert(converter = CrawlerProductIntroductionImage.ImagesConverter.class)
	@JsonView({EditView.class})
	private List<String> images = new ArrayList<>();

	public CrawlerProductImage() {
	}

	public CrawlerProductImage(CrawlerProduct crawlerProduct) {
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