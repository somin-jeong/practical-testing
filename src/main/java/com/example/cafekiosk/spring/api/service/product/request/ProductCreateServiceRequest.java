package com.example.cafekiosk.spring.api.service.product.request;

import com.example.cafekiosk.spring.domain.product.Product;
import com.example.cafekiosk.spring.domain.product.ProductSellingType;
import com.example.cafekiosk.spring.domain.product.ProductType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductCreateServiceRequest {

    private ProductType type;

    private ProductSellingType sellingType;

    private String name;

    private int price;

    @Builder
    public ProductCreateServiceRequest(ProductType type, ProductSellingType sellingType, String name, int price) {
        this.type = type;
        this.sellingType = sellingType;
        this.name = name;
        this.price = price;
    }

    public Product toEntity(String nextProductNumber) {
        return Product.builder()
                .productNumber(nextProductNumber)
                .type(type)
                .sellingType(sellingType)
                .name(name)
                .price(price)
                .build();
    }
}
