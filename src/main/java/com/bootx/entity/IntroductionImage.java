
package com.bootx.entity;

import com.bootx.common.BaseAttributeConverter;

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
public class IntroductionImage extends BaseEntity<Long> {

	@OneToOne(fetch = FetchType.LAZY)
	private Product product;

	@Lob
	@Convert(converter =ImagesConverter.class)
	private List<String> images = new ArrayList<>();


	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
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