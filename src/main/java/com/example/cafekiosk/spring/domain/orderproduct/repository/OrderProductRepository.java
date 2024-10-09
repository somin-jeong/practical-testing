package com.example.cafekiosk.spring.domain.orderproduct.repository;

import com.example.cafekiosk.spring.domain.orderproduct.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
}
