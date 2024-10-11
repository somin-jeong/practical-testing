package com.example.cafekiosk.spring.api.service.product.request;

import com.example.cafekiosk.spring.domain.product.ProductSellingType;
import com.example.cafekiosk.spring.domain.product.ProductType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductCreateRequest {
    private ProductType type;

    private ProductSellingType sellingType;

    private String name;

    private int price;
}
