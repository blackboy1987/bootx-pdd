
package com.bootx.entity;

import com.bootx.common.BaseAttributeConverter;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity - 文章
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Indexed
@Entity
public class NoticeReadLog extends BaseEntity<Long> {

	@Column(updatable = false, length = 6000)
	@Convert(converter = AuditLog.ParameterConverter.class)
	private List<Long> noticeIds = new ArrayList<>();

	private Long userId;

	public List<Long> getNoticeIds() {
		return noticeIds;
	}

	public void setNoticeIds(List<Long> noticeIds) {
		this.noticeIds = noticeIds;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Convert
	public static class NoticeIdsConvert extends BaseAttributeConverter<List<Long>> {

	}
}