package com.muchlab.chapter64.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "T_ORDER")
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
public class CoffeeOrder extends BaseEntity implements Serializable {

    private String name;

    private String customer;

    @ManyToMany
    @JoinTable(name = "T_ORDER_COFFEE")

    private List<Coffee> items;

    private OrderState state;
}
