
package com.bootx.entity;

import org.hibernate.search.annotations.Indexed;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Transient;

/**
 * Entity - 文章
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Indexed
@Entity
public class Notice extends BaseEntity<Long> {

	private Long categoryId;

	private String title;

	private String digest;

	private String thumb;

	@Lob
	private String content;

	private Long author;

	private Long type;

	private Long name;

	private Long linkUrl;

	/**
	 * 1:已读
	 * 0：未读
	 */
	@Transient
	private Integer isRead;

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDigest() {
		return digest;
	}

	public void setDigest(String digest) {
		this.digest = digest;
	}

	public String getThumb() {
		return thumb;
	}

	public void setThumb(String thumb) {
		this.thumb = thumb;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getAuthor() {
		return author;
	}

	public void setAuthor(Long author) {
		this.author = author;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public Long getName() {
		return name;
	}

	public void setName(Long name) {
		this.name = name;
	}

	public Long getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(Long linkUrl) {
		this.linkUrl = linkUrl;
	}

	public Integer getIsRead() {
		return isRead;
	}

	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
	}
}