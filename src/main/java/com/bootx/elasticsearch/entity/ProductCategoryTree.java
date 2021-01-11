package com.bootx.elasticsearch.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductCategoryTree implements Serializable {

    private Long id;

    private String name;

    private List<ProductCategoryTree> children = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ProductCategoryTree> getChildren() {
        return children;
    }

    public void setChildren(List<ProductCategoryTree> children) {
        this.children = children;
    }
}
