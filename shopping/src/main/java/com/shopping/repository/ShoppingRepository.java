package com.shopping.repository;

import com.shopping.entity.Shopping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoppingRepository extends JpaRepository<Shopping, Long> {
    List<Shopping> findByCustomerId(Long customerId);
    // Custom query methods can be added here if needed
}

