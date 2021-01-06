package com.bootx.entity;

import com.bootx.common.BaseAttributeConverter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class CrawlerLog extends BaseEntity<Long>{

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false,updatable = false)
    private Member member;

    @NotEmpty
    @Column(nullable = false,updatable = false,unique = true)
    private String sn;

    @OneToMany(mappedBy = "crawlerLog",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Set<CrawlerUrlLog> crawlerUrlLogs = new HashSet<>();

    @NotNull
    @Column(nullable = false,updatable = false)
    private Integer type;

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public Set<CrawlerUrlLog> getCrawlerUrlLogs() {
        return crawlerUrlLogs;
    }

    public void setCrawlerUrlLogs(Set<CrawlerUrlLog> crawlerUrlLogs) {
        this.crawlerUrlLogs = crawlerUrlLogs;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 类型转换 - 规格项
     *
     * @author IGOMALL  Team
     * @version 1.0
     */
    @Converter
    public static class UrlConverter extends BaseAttributeConverter<List<String>> {
    }
}
