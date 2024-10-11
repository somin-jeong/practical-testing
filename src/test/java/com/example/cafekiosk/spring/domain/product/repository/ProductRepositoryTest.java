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

@DataJpaTest
@ActiveProfiles("test")
class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;

    @DisplayName("원하는 판매상태를 가진 상품들을 조회한다.")
    @Test
    void findAllBySellingTypeIn() {
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

    @DisplayName("상품번호 리스트로 상품들을 조회한다.")
    @Test
    void findAllByProductNumberIn() {
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
        List<Product> productList = productRepository.findAllByProductNumberIn(List.of("001", "002"));

        // then
        assertThat(productList).hasSize(2)
                .extracting("productNumber", "name", "sellingType")
                .containsExactlyInAnyOrder(
                        Assertions.tuple("001", "아메리카노", ProductSellingType.SEllING),
                        Assertions.tuple("002", "카페라떼", ProductSellingType.HOLD)
                );
    }

    @DisplayName("가장 마지막으로 저장한 상품의 상품 번호를 읽어온다.")
    @Test
    void findLatestProduct() {
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

        String targetProductNumber = "003";
        Product product3 = Product.builder()
                .type(ProductType.HANDMADE)
                .sellingType(ProductSellingType.STOP_SELLING)
                .price(7000)
                .name("팥빙수")
                .productNumber(targetProductNumber)
                .build();

        productRepository.saveAll(List.of(product1, product2, product3));

        // when
        String productNumber = productRepository.findLatestProductNumber();

        // then
        assertThat(productNumber).isEqualTo(targetProductNumber);
    }

    @DisplayName("가장 마지막으로 저장한 상품의 상품 번호를 읽어올 때, 상품이 하나도 없는 경우에는 NULL을 반환한다.")
    @Test
    void findLatestProductWhenProductIsEmpty() {
        // when
        String productNumber = productRepository.findLatestProductNumber();

        // then
        assertThat(productNumber).isNull();
    }
}