package com.bootx.entity;

import com.bootx.common.BaseAttributeConverter;

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

    @NotEmpty
    @Column(length = 400,nullable = false,updatable = false,unique = true)
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    @NotEmpty
    @Column(nullable = false,updatable = false,unique = true)
    private String productId;

    @Convert(converter = MoreInfoConverter.class)
    private Map<String,Object> moreInfo = new HashMap<>();

    private String memo;

    public CrawlerLog getCrawlerLog() {
        return crawlerLog;
    }

    public void setCrawlerLog(CrawlerLog crawlerLog) {
        this.crawlerLog = crawlerLog;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
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

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
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
