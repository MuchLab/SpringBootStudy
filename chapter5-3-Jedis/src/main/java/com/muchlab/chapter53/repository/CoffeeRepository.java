package com.muchlab.chapter53.repository;

import com.muchlab.chapter53.entity.Coffee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeRepository extends JpaRepository<Coffee, Long> {
}
