package com.example.cafekiosk.spring.domain.stock.repository;

import com.example.cafekiosk.spring.domain.stock.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Long> {
}
