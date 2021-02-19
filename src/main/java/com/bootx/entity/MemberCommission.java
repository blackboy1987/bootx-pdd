package com.bootx.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
public class MemberCommission extends BaseEntity<Long>{

    @NotNull
    @JoinColumn(nullable = false,updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @NotNull
    @Column(nullable = false,updatable = false,precision = 27, scale = 12)
    private BigDecimal beforeBalance;

    @NotNull
    @Column(nullable = false,updatable = false,precision = 27, scale = 12)
    private BigDecimal balance;

    @NotNull
    @Column(nullable = false,updatable = false,precision = 27, scale = 12)
    private BigDecimal afterBalance;

    @NotNull
    @Column(nullable = false,updatable = false)
    private Long storeId;

    @NotNull
    @Column(nullable = false,updatable = false)
    private Long mallId;

    @NotNull
    @Column(nullable = false,updatable = false)
    private Long mallName;

    @NotEmpty
    @Column(nullable = false,updatable = false)
    private String orderSn;

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public BigDecimal getBeforeBalance() {
        return beforeBalance;
    }

    public void setBeforeBalance(BigDecimal beforeBalance) {
        this.beforeBalance = beforeBalance;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getAfterBalance() {
        return afterBalance;
    }

    public void setAfterBalance(BigDecimal afterBalance) {
        this.afterBalance = afterBalance;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Long getMallId() {
        return mallId;
    }

    public void setMallId(Long mallId) {
        this.mallId = mallId;
    }

    public Long getMallName() {
        return mallName;
    }

    public void setMallName(Long mallName) {
        this.mallName = mallName;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }
}
