package com.example.cafekiosk.spring.api.controller.order;

import com.example.cafekiosk.spring.api.service.order.OrderService;
import com.example.cafekiosk.spring.api.service.order.request.OrderCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("api/vi/orders/new")
    public void createOrder(@RequestBody OrderCreateRequest request) {
        orderService.createOrder(request);
    }
}
