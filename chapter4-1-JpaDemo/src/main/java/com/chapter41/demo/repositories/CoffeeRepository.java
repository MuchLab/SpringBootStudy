package com.chapter41.demo.repositories;

import com.chapter41.demo.entities.Coffee;
import org.springframework.data.repository.CrudRepository;

public interface CoffeeRepository extends CrudRepository<Coffee, Long> {
}
