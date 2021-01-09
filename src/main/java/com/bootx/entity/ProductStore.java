package com.bootx.entity;

import com.fasterxml.jackson.annotation.JsonView;

import java.io.Serializable;

public class ProductStore implements Serializable {

    private String id;

    @JsonView({BaseEntity.EditView.class})
    private String url;

    @JsonView({BaseEntity.EditView.class})
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
