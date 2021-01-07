package com.bootx.entity;

import com.bootx.common.BaseAttributeConverter;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

/**
 * @author black
 */
@Entity
public class CrawlerUrlLog extends BaseEntity<Long>{

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false,updatable = false)
    private CrawlerLog crawlerLog;

    private String crawlerLogSn;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false,updatable = false)
    private Member member;

    @NotEmpty
    @Column(length = 400,nullable = false,updatable = false,unique = true)
    private String url;

    private String productId;

    @Convert(converter = MoreInfoConverter.class)
    private Map<String,Object> moreInfo = new HashMap<>();

    private Integer type;

    private String memo;


    /**
     * 0：正在抓取
     * 1：抓取完成
     * 2：抓取失败
     */
    @JsonView({PageView.class})
    private Integer status;

    public CrawlerLog getCrawlerLog() {
        return crawlerLog;
    }

    public void setCrawlerLog(CrawlerLog crawlerLog) {
        this.crawlerLog = crawlerLog;
    }

    public String getCrawlerLogSn() {
        return crawlerLogSn;
    }

    public void setCrawlerLogSn(String crawlerLogSn) {
        this.crawlerLogSn = crawlerLogSn;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Map<String, Object> getMoreInfo() {
        return moreInfo;
    }

    public void setMoreInfo(Map<String, Object> moreInfo) {
        this.moreInfo = moreInfo;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 类型转换 - 规格项
     *
     * @author IGOMALL  Team
     * @version 1.0
     */
    @Converter
    public static class MoreInfoConverter extends BaseAttributeConverter<Map<String,Object>> {
    }
}
