package com.huiluczP.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class AccountInfo implements Serializable {
    private static final long serialVersionUID = -2640241125197227404L;
    int id;
    String account;
    String name;
    BigDecimal money;
    public AccountInfo(){}
    public AccountInfo(int id, String account, String name, BigDecimal money){
        this.id = id;
        this.account = account;
        this.name = name;
        this.money = money;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }
}
