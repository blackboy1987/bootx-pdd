
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
public class ProductSpecification extends BaseEntity<Long> {

	@OneToOne(fetch = FetchType.LAZY)
	private Product product;

	@Lob
	@Convert(converter =SpecificationConverter.class)
	private List<Specification> parameterValues = new ArrayList<>();


	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public List<Specification> getParameterValues() {
		return parameterValues;
	}

	public void setParameterValues(List<Specification> parameterValues) {
		this.parameterValues = parameterValues;
	}

	@Converter
	public static class SpecificationConverter extends BaseAttributeConverter<List<Specification>> {
	}
}