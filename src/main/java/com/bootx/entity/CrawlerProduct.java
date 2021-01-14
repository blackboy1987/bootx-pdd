
package com.bootx.entity;
import com.bootx.common.BaseAttributeConverter;
import com.bootx.pdd.entity.PddCrawlerProduct;
import com.fasterxml.jackson.annotation.JsonView;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.compress.utils.Lists;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Entity - 商品
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
@Entity
public class CrawlerProduct extends BaseEntity<Long> {

	private static final long serialVersionUID = -6977025562650112419L;

	@JsonView({PageView.class,EditView.class})
	private String sn;

	@ManyToMany(fetch = FetchType.LAZY)
	private Set<CrawlerLog> crawlerLogs = new HashSet<>();

	/**
	 * 名称
	 */
	@Length(max = 200)
	@JsonView({PageView.class,EditView.class})
	private String name;

	@JsonView({PageView.class,EditView.class})
	private String price;

	/**
	 * 商品图片
	 */
	@OneToOne(mappedBy = "crawlerProduct",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JsonView({EditView.class})
	private CrawlerProductImage crawlerProductImage;

	/**
	 * 介绍
	 */
	@OneToOne(mappedBy = "crawlerProduct",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private CrawlerProductIntroduction crawlerProductIntroduction;

	@OneToOne(mappedBy = "crawlerProduct",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JsonView({EditView.class})
	private CrawlerProductIntroductionImage crawlerProductIntroductionImage;


	@OneToOne(mappedBy = "crawlerProduct",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JsonView({EditView.class})
	private CrawlerProductParameterValue crawlerProductParameterValue;

	@OneToOne(mappedBy = "crawlerProduct",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JsonView({EditView.class})
	private CrawlerProductSku crawlerProductSku;

	/**
	 * 规格项
	 */
	@OneToOne(mappedBy = "crawlerProduct",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JsonView({EditView.class})
	private CrawlerProductSpecification crawlerProductSpecification;
	/**
	 * 商品分类
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	private ProductCategory productCategory;

	@Column(length = 60)
	@Convert(converter = CrawlerProductCategoryIdConverter.class)
	private List<Long> productCategoryIds = new ArrayList<>();

	@Column(length = 300)
	@Convert(converter = CrawlerProductCategoryNamesConverter.class)
	@JsonView({PageView.class})
	private List<String> productCategoryNames = new ArrayList<>();


	@JsonView({EditView.class})
	private Long stock;

	@JsonView({PageView.class})
	@Column(updatable = false)
	private String pluginId;

	@NotEmpty
	@Column(length = 1000,nullable = false,updatable = false,unique = true)
	private String url;

	/**
	 * 对url进行sha1加密，方便用来比较是否已经抓取过了
	 */
	@NotEmpty
	@Column(nullable = false,updatable = false,unique = true)
	private String md5;

	@Column(length = 100)
	@Convert(converter = CrawlerProductStoreConverter.class)
	@JsonView({EditView.class})
	private CrawlerProductStore crawlerProductStore;

	/**
	 * 0：待采集
	 * 1：采集完成
	 * 2：采集失败
	 */
	@JsonView(PageView.class)
	private Integer status;

	@Transient
	private PddCrawlerProduct pddCrawlerProduct;


	public CrawlerProduct() {
		init();
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public Set<CrawlerLog> getCrawlerLogs() {
		return crawlerLogs;
	}

	public void setCrawlerLogs(Set<CrawlerLog> crawlerLogs) {
		this.crawlerLogs = crawlerLogs;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public CrawlerProductImage getCrawlerProductImage() {
		return crawlerProductImage;
	}

	public void setCrawlerProductImage(CrawlerProductImage crawlerProductImage) {
		this.crawlerProductImage = crawlerProductImage;
	}

	public CrawlerProductIntroduction getCrawlerProductIntroduction() {
		return crawlerProductIntroduction;
	}

	public void setCrawlerProductIntroduction(CrawlerProductIntroduction crawlerProductIntroduction) {
		this.crawlerProductIntroduction = crawlerProductIntroduction;
	}

	public CrawlerProductIntroductionImage getCrawlerProductIntroductionImage() {
		return crawlerProductIntroductionImage;
	}

	public void setCrawlerProductIntroductionImage(CrawlerProductIntroductionImage crawlerProductIntroductionImage) {
		this.crawlerProductIntroductionImage = crawlerProductIntroductionImage;
	}

	public CrawlerProductParameterValue getCrawlerProductParameterValue() {
		return crawlerProductParameterValue;
	}

	public void setCrawlerProductParameterValue(CrawlerProductParameterValue crawlerProductParameterValue) {
		this.crawlerProductParameterValue = crawlerProductParameterValue;
	}

	public CrawlerProductSku getCrawlerProductSku() {
		return crawlerProductSku;
	}

	public void setCrawlerProductSku(CrawlerProductSku crawlerProductSku) {
		this.crawlerProductSku = crawlerProductSku;
	}

	public CrawlerProductSpecification getCrawlerProductSpecification() {
		return crawlerProductSpecification;
	}

	public void setCrawlerProductSpecification(CrawlerProductSpecification crawlerProductSpecification) {
		this.crawlerProductSpecification = crawlerProductSpecification;
	}

	public ProductCategory getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}

	public List<Long> getProductCategoryIds() {
		return productCategoryIds;
	}

	public void setProductCategoryIds(List<Long> productCategoryIds) {
		this.productCategoryIds = productCategoryIds;
	}

	public List<String> getProductCategoryNames() {
		return productCategoryNames;
	}

	public void setProductCategoryNames(List<String> productCategoryNames) {
		this.productCategoryNames = productCategoryNames;
	}

	public Long getStock() {
		return stock;
	}

	public void setStock(Long stock) {
		this.stock = stock;
	}

	public String getPluginId() {
		return pluginId;
	}

	public void setPluginId(String pluginId) {
		this.pluginId = pluginId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
		if (url != null) {
			setMd5(DigestUtils.md5Hex(url));
		}
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public CrawlerProductStore getCrawlerProductStore() {
		return crawlerProductStore;
	}

	public void setCrawlerProductStore(CrawlerProductStore crawlerProductStore) {
		this.crawlerProductStore = crawlerProductStore;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public PddCrawlerProduct getPddCrawlerProduct() {
		return pddCrawlerProduct;
	}

	public void setPddCrawlerProduct(PddCrawlerProduct pddCrawlerProduct) {
		this.pddCrawlerProduct = pddCrawlerProduct;
	}

	public void init(){
		setStatus(0);
		setCrawlerLogs(new HashSet<>());
		setCrawlerProductImage(new CrawlerProductImage(this));
		setCrawlerProductIntroduction(new CrawlerProductIntroduction(this));
		setCrawlerProductIntroductionImage(new CrawlerProductIntroductionImage(this));
		setCrawlerProductParameterValue(new CrawlerProductParameterValue(this));
		setCrawlerProductSku(new CrawlerProductSku(this));
		setCrawlerProductSpecification(new CrawlerProductSpecification(this));
		setProductCategory(null);
		setProductCategoryIds(Lists.newArrayList());
		setProductCategoryNames(Lists.newArrayList());
		setStock(0L);
		setCrawlerProductStore(new CrawlerProductStore());
	}

	@Transient
	@JsonView({PageView.class})
	public String getImage(){
		try {
			return getCrawlerProductImage().getImages().get(0);
		}catch (Exception e){
			return null;
		}
	}



	@Converter
	public static class CrawlerProductCategoryIdConverter extends BaseAttributeConverter<List<Long>> {
	}
	@Converter
	public static class CrawlerProductCategoryNamesConverter extends BaseAttributeConverter<List<String>> {
	}
	@Converter
	public static class CrawlerProductStoreConverter extends BaseAttributeConverter<CrawlerProductStore> {
	}

}