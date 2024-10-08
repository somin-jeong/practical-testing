package com.example.cafekiosk.spring.domain.product.repository;

import com.example.cafekiosk.spring.domain.product.Product;
import com.example.cafekiosk.spring.domain.product.ProductSellingType;
import com.example.cafekiosk.spring.domain.product.ProductType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

//@SpringBootTest
@DataJpaTest
@ActiveProfiles("test")
class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;

    @DisplayName("원하는 판매상태를 가진 상품들을 조회한다.")
    @Test
    void calculateTotalPrice() {
        // given
        Product product1 = Product.builder()
                .type(ProductType.HANDMADE)
                .sellingType(ProductSellingType.SEllING)
                .price(4000)
                .name("아메리카노")
                .productNumber("001")
                .build();

        Product product2 = Product.builder()
                .type(ProductType.HANDMADE)
                .sellingType(ProductSellingType.HOLD)
                .price(5000)
                .name("카페라떼")
                .productNumber("002")
                .build();

        Product product3 = Product.builder()
                .type(ProductType.HANDMADE)
                .sellingType(ProductSellingType.STOP_SELLING)
                .price(7000)
                .name("팥빙수")
                .productNumber("003")
                .build();

        productRepository.saveAll(List.of(product1, product2, product3));

        // when
        List<Product> productList = productRepository.findAllBySellingTypeIn(List.of(ProductSellingType.SEllING, ProductSellingType.HOLD));

        // then
        assertThat(productList).hasSize(2)
                .extracting("productNumber", "name", "sellingType")
                .containsExactlyInAnyOrder(
                        Assertions.tuple("001", "아메리카노", ProductSellingType.SEllING),
                        Assertions.tuple("002", "카페라떼", ProductSellingType.HOLD)
                );
    }
}