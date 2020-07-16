package com.muchlab.chapter44.service;

import com.muchlab.chapter44.entity.Coffee;
import com.muchlab.chapter44.entity.CoffeeOrder;
import com.muchlab.chapter44.entity.OrderState;
import com.muchlab.chapter44.repository.CoffeeOrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;

@Slf4j
@Service
@Transactional
public class CoffeeOrderService {
    @Autowired
    private CoffeeOrderRepository orderRepository;

    /**
     * 创建订单
     * @param customer
     * @param coffees
     * @return
     */
    public CoffeeOrder createOrder(String customer, Coffee...coffees){
        CoffeeOrder order = CoffeeOrder.builder()
                .customer(customer)
                .items(new ArrayList<>(Arrays.asList(coffees)))
                .state(OrderState.INIT)
                .build();
        CoffeeOrder savedOrder = orderRepository.save(order);
        log.info("New Order:{}", savedOrder);
        return savedOrder;
    }

    /**
     * 更新状态
     * @param order
     * @param state
     * @return
     */
    public boolean updateState(CoffeeOrder order, OrderState state){
        if (state.compareTo(order.getState())<=0){
            log.warn("Wrong State order:{},{}", state, order.getState());
            return false;
        }
        order.setState(state);
        orderRepository.save(order);
        log.info("Update Order:{}", order);
        return true;
    }
}
