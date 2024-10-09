package com.example.cafekiosk.spring.api.service.order;

import com.example.cafekiosk.spring.api.service.order.request.OrderCreateRequest;
import com.example.cafekiosk.spring.api.service.order.response.OrderResponse;
import com.example.cafekiosk.spring.domain.order.Order;
import com.example.cafekiosk.spring.domain.product.Product;
import com.example.cafekiosk.spring.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final ProductRepository productRepository;

    public OrderResponse createOrder(OrderCreateRequest request, LocalDateTime registeredDateTime) {
        List<String> productNumbers = request.getProductNumbers();

        // Product
        List<Product> productList = productRepository.findAllByProductNumberIn(productNumbers);

        Order.create(productList, registeredDateTime);

        return null;
    }
}
