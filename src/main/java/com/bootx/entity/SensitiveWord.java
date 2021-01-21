package com.bootx.entity;

import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class SensitiveWord extends BaseEntity<Long>{

    @NotEmpty
    @Column(nullable = false,unique = true)
    @JsonView({PageView.class})
    private String word;

    @Column(updatable = false)
    private Long memberId;

    @NotNull
    @Column(nullable = false)
    private Boolean isEnabled;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Boolean getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }
}
