package com.example.cafekiosk.spring.api.controller.order;

import com.example.cafekiosk.spring.api.ApiResponse;
import com.example.cafekiosk.spring.api.service.order.OrderService;
import com.example.cafekiosk.spring.api.controller.order.request.OrderCreateRequest;
import com.example.cafekiosk.spring.api.service.order.response.OrderResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/api/v1/orders/new")
    public ApiResponse<OrderResponse> createOrder(@Valid @RequestBody OrderCreateRequest request) {
        LocalDateTime registeredDateTime = LocalDateTime.now();
        return ApiResponse.of(orderService.createOrder(request, registeredDateTime));
    }
}
