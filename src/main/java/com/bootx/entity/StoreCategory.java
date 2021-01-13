package com.bootx.entity;

import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class StoreCategory extends BaseEntity<Long>{

    @ManyToOne
    @JoinColumn(updatable = false)
    public Member member;

    @NotEmpty
    @Column(nullable = false)
    @JsonView({ListView.class})
    private String name;

    @NotNull
    @Column(nullable = false)
    @JsonView({ListView.class})
    private Boolean isDefault;


    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public String getName() {
        return name;
    }

    public Boolean getIDefault() {
        return isDefault;
    }

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    public void setName(String name) {
        this.name = name;
    }
}
