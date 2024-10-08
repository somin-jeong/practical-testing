package com.example.cafekiosk.spring.api.service.order.request;

import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
public class OrderCreateRequest {
    private List<String> productNumbers;
}
