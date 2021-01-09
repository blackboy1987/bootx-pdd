
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
public class ProductParameterValue extends BaseEntity<Long> {

	@OneToOne(fetch = FetchType.LAZY)
	private Product product;

	@Lob
	@Convert(converter =ParameterValueConverter.class)
	@JsonView({EditView.class})
	private List<ParameterValue> parameterValues = new ArrayList<>();

	public ProductParameterValue() {
	}

	public ProductParameterValue(Product product) {
		this.product = product;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public List<ParameterValue> getParameterValues() {
		return parameterValues;
	}

	public void setParameterValues(List<ParameterValue> parameterValues) {
		this.parameterValues = parameterValues;
	}

	@Converter
	public static class ParameterValueConverter extends BaseAttributeConverter<List<ParameterValue>> {
	}
}