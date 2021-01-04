package com.bootx.entity;

import com.bootx.common.BaseAttributeConverter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Converter;
import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.HashMap;
import java.util.Map;

/**
 * @author black
 */
@Entity
public class Platform extends BaseEntity<Long>{

    @NotEmpty
    @Column(unique = true,nullable = false)
    private String name;

    @NotEmpty
    @Column(unique = true,nullable = false)
    private String siteUrl;


    @Size(max = 100)
    @Column(length = 4000)
    @Convert(converter = ApiUrlsConverter.class)
    private Map<String,String> apiUrls = new HashMap<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSiteUrl() {
        return siteUrl;
    }

    public void setSiteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
    }

    public Map<String, String> getApiUrls() {
        return apiUrls;
    }

    public void setApiUrls(Map<String, String> apiUrls) {
        this.apiUrls = apiUrls;
    }

    @Converter
    public static class ApiUrlsConverter extends BaseAttributeConverter<Map<String,String>> {

    }
}
