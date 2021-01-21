
package com.bootx.pdd.entity;

import com.bootx.common.BaseAttributeConverter;
import com.bootx.entity.BaseEntity;
import com.bootx.entity.ParameterValue;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity - 会员注册项
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Entity(name = "pdd_CrawlerProductParameterValue")
public class PddCrawlerProductParameterValue extends BaseEntity<Long> {

	@NotNull
	@JoinColumn(updatable = false)
	@OneToOne(fetch = FetchType.LAZY)
	private PddCrawlerProduct crawlerProduct;

	@Lob
	@Convert(converter =ParameterValueConverter.class)
	@JsonView({EditView.class})
	private List<ParameterValue> parameterValues = new ArrayList<>();

	public PddCrawlerProductParameterValue() {
	}

	public PddCrawlerProductParameterValue(PddCrawlerProduct crawlerProduct) {
		this.crawlerProduct = crawlerProduct;
	}

	public PddCrawlerProduct getCrawlerProduct() {
		return crawlerProduct;
	}

	public void setCrawlerProduct(PddCrawlerProduct crawlerProduct) {
		this.crawlerProduct = crawlerProduct;
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