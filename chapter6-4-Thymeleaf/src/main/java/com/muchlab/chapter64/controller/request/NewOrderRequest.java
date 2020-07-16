package com.muchlab.chapter64.controller.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import java.util.List;


@Data
public class NewOrderRequest {
    private String customer;
    private List<String> items;
}
