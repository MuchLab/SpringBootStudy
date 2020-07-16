package com.chapter41.demo.repositories;

import com.chapter41.demo.entities.Coffee;
import com.chapter41.demo.entities.CoffeeOrder;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
//findTop3OrderByUpdateTimeDescIdAsc
public interface CoffeeOrderRepository extends BaseRepository<CoffeeOrder, Long> {
    List<CoffeeOrder> findByCustomerOrderById(String customer);
    List<CoffeeOrder> findByItems_Name(String items_name);
    List<CoffeeOrder> findAllOrOrderById();
}
