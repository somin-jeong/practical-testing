package com.example.cafekiosk.spring.api.service.product.response;

import com.example.cafekiosk.spring.domain.product.Product;
import com.example.cafekiosk.spring.domain.product.ProductSellingType;
import com.example.cafekiosk.spring.domain.product.ProductType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
public class ProductResponse {

    private Long id;

    private String productNumber;

    private ProductType type;

    private ProductSellingType sellingType;

    private String name;

    private int price;

    public ProductResponse(Long id, String productNumber, ProductType type, ProductSellingType sellingType, String name, int price) {
        this.id = id;
        this.productNumber = productNumber;
        this.type = type;
        this.sellingType = sellingType;
        this.name = name;
        this.price = price;
    }

    public static ProductResponse of(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .productNumber(product.getProductNumber())
                .price(product.getPrice())
                .type(product.getType())
                .sellingType(product.getSellingType())
                .build();
    }
}
