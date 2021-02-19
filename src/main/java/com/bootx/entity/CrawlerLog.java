package com.bootx.entity;

import com.bootx.common.BaseAttributeConverter;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class CrawlerLog extends BaseEntity<Long>{

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false,updatable = false)
    private Member member;

    @ManyToMany(mappedBy = "crawlerLogs",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Set<CrawlerProduct> crawlerProducts = new HashSet<>();

    @Column(length = 500)
    @Convert(converter = PluginIdConverter.class)
    @JsonView({PageView.class})
    private List<String> pluginIds = new ArrayList<>();

    @NotNull
    @Column(nullable = false,updatable = false)
    @JsonView({PageView.class})
    private Integer type;

    /**
     * 0：正在抓取
     * 1：抓取完成
     */
    @JsonView({PageView.class})
    private Integer status;

    @NotNull
    @Min(0)
    @Column(nullable = false)
    @JsonView({PageView.class})
    private Integer total;

    @NotNull
    @Min(0)
    @Column(nullable = false)
    @JsonView({PageView.class})
    private Integer success;

    @NotNull
    @Min(0)
    @Column(nullable = false)
    @JsonView({PageView.class})
    private Integer fail;

    @Column(updatable = false)
    private String batchId;

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Set<CrawlerProduct> getCrawlerProducts() {
        return crawlerProducts;
    }

    public void setCrawlerProducts(Set<CrawlerProduct> crawlerProducts) {
        this.crawlerProducts = crawlerProducts;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<String> getPluginIds() {
        return pluginIds;
    }

    public void setPluginIds(List<String> pluginIds) {
        this.pluginIds = pluginIds;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public Integer getFail() {
        return fail;
    }

    public void setFail(Integer fail) {
        this.fail = fail;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    /**
     * 类型转换 - 规格项
     *
     * @author IGOMALL  Team
     * @version 1.0
     */
    @Converter
    public static class PluginIdConverter extends BaseAttributeConverter<List<String>> {
    }
}
