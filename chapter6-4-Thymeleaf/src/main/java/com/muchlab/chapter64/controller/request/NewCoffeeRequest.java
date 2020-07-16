package com.muchlab.chapter64.controller.request;

import org.joda.money.Money;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class NewCoffeeRequest {
    @NotBlank(message = "咖啡名称不能为空")
    private String name;
    @NotNull(message = "咖啡价格不能为null")
    private Money price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Money getPrice() {
        return price;
    }

    public void setPrice(Money price) {
        this.price = price;
    }
}
