package com.muchlab.chapter82.service;

import com.muchlab.chapter82.model.Coffee;
import com.muchlab.chapter82.model.CoffeeOrder;
import com.muchlab.chapter82.model.OrderState;
import com.muchlab.chapter82.repository.CoffeeOrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;

@Service
@Slf4j
@Transactional
public class CoffeeOrderService {
    @Autowired
    private CoffeeOrderRepository orderRepository;

    public CoffeeOrder createOrder(String customer, Coffee...coffees){
        CoffeeOrder order = CoffeeOrder.builder()
                .customer(customer)
                .items(Arrays.asList(coffees))
                .state(OrderState.INIT)
                .build();
        CoffeeOrder saved = orderRepository.save(order);
        log.info("New Order:{}", saved);
        return saved;
    }

    public boolean updateState(CoffeeOrder order, OrderState state){
        //若订单状态相等
        if (state.compareTo(order.getState())<=0){
            log.warn("Wrong State Order:{} {}", state, order.getState());
            return false;
        }
        order.setState(state);
        orderRepository.save(order);
        log.info("Updated Order:{}", order);
        return true;
    }
}
