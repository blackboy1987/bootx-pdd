
package com.bootx.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.OneToOne;

/**
 * Entity - 会员注册项
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Entity
public class ProductIntroduction extends BaseEntity<Long> {

	@OneToOne(fetch = FetchType.LAZY)
	private Product product;

	@Lob
	private String content;

	public ProductIntroduction() {
	}

	public ProductIntroduction(Product product) {
		this.product = product;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}