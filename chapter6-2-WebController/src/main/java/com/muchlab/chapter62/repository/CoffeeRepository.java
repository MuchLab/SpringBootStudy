package com.muchlab.chapter62.repository;

import com.muchlab.chapter62.model.Coffee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CoffeeRepository extends JpaRepository<Coffee, Long> {
    Coffee findByName(String name);
    List<Coffee> findByNameInOrderById(List<String> names);
}
