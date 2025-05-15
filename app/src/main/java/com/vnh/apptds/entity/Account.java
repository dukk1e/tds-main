package com.vnh.apptds.entity;

import java.math.BigDecimal;

public class Account {
    private String username;
    private BigDecimal totalCoin;
    public Account() {
    }
    public Account(String username, BigDecimal totalCoin) {
        this.username = username;
        this.totalCoin = totalCoin;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public BigDecimal getTotalCoin() {
        return totalCoin;
    }
    public void setTotalCoin(BigDecimal totalCoin) {
        this.totalCoin = totalCoin;
    }
}
