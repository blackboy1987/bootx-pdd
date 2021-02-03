
package com.bootx.elasticsearch.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Entity - 商品
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
@Document(indexName="pdd_crawler_product")
public class EsPddCrawlerProduct implements Serializable {

	private static final long serialVersionUID = -6977025562650112419L;

	@Id
	private Long id;

	private Long crawlerProductId;

	private String sn;

	private String name;

	private String price;

	private Long stock;

	private String pluginId;

	private String url;

	private Long memberId;

	/**
	 * 0：待采集
	 * 1：采集完成
	 * 2：采集失败
	 */
	private Integer status;

	/**
	 * 是否删除
	 */
	private Boolean isDeleted;

	/**
	 * 发布状态
	 * 10：待发布
	 * 11：发布中
	 * 12：发布成功
	 * 13：发布失败
	 * 14：草稿箱
	 */
	private Integer publishStatus;

	private List<Map<String, Object>> pddLogs = new ArrayList<>();

	private String image;

	private Date createdDate;

	private Date lastModifiedDate;

	private String productCategoryName;

	private Long productCategoryId;

	private String batchId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCrawlerProductId() {
		return crawlerProductId;
	}

	public void setCrawlerProductId(Long crawlerProductId) {
		this.crawlerProductId = crawlerProductId;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
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
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Integer getPublishStatus() {
		return publishStatus;
	}

	public void setPublishStatus(Integer publishStatus) {
		this.publishStatus = publishStatus;
	}

	public List<Map<String, Object>> getPddLogs() {
		return pddLogs;
	}

	public void setPddLogs(List<Map<String, Object>> pddLogs) {
		this.pddLogs = pddLogs;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public String getProductCategoryName() {
		return productCategoryName;
	}

	public void setProductCategoryName(String productCategoryName) {
		this.productCategoryName = productCategoryName;
	}

	public Long getProductCategoryId() {
		return productCategoryId;
	}

	public void setProductCategoryId(Long productCategoryId) {
		this.productCategoryId = productCategoryId;
	}

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
}