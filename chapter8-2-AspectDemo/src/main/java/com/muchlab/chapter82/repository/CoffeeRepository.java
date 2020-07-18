package com.muchlab.chapter82.repository;

import com.muchlab.chapter82.model.Coffee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeRepository extends JpaRepository<Coffee, Long> {
}
