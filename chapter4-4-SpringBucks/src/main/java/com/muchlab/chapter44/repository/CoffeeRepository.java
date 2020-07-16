package com.muchlab.chapter44.repository;

import com.muchlab.chapter44.entity.Coffee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeRepository extends JpaRepository<Coffee, Long> {
}
