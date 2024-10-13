package com.example.cafekiosk.spring.api.controller.product.request;

import com.example.cafekiosk.spring.domain.product.Product;
import com.example.cafekiosk.spring.domain.product.ProductSellingType;
import com.example.cafekiosk.spring.domain.product.ProductType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductCreateRequest {
    @NotNull(message = "상품 타입은 필수입니다.")
    private ProductType type;
    @NotNull(message = "상품 판매 상태는 필수입니다.")
    private ProductSellingType sellingType;
    @NotBlank(message = "상품 이름은 필수입니다.")
    private String name;
    @Positive(message = "상품 가격은 0보다 커야합니다.")
    private int price;

    @Builder
    public ProductCreateRequest(ProductType type, ProductSellingType sellingType, String name, int price) {
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
