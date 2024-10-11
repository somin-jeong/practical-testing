package com.example.cafekiosk.spring.api.service.product;

import com.example.cafekiosk.spring.api.service.product.request.ProductCreateRequest;
import com.example.cafekiosk.spring.api.service.product.response.ProductResponse;
import com.example.cafekiosk.spring.domain.product.Product;
import com.example.cafekiosk.spring.domain.product.ProductSellingType;
import com.example.cafekiosk.spring.domain.product.ProductType;
import com.example.cafekiosk.spring.domain.product.repository.ProductRepository;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class ProductServiceTest {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;

    @AfterEach
    void tearDown() {
        productRepository.deleteAllInBatch();
    }

    @DisplayName("가장 최근 상품의 상품 번호에서 1 증가한 상품 번호를 갖는 신규 상품을 등록한다.")
    @Test
    void createProduct() {
        // given
        Product product1 = createProduct("001", ProductType.HANDMADE, ProductSellingType.SEllING, "아메리카노", 4000);
        Product product2 = createProduct("002", ProductType.HANDMADE, ProductSellingType.HOLD, "카페라떼", 4500);
        Product product3 = createProduct("003", ProductType.HANDMADE, ProductSellingType.STOP_SELLING, "팥빙수", 7000);

        productRepository.saveAll(List.of(product1, product2, product3));

        ProductCreateRequest request = ProductCreateRequest.builder()
                .type(ProductType.HANDMADE)
                .sellingType(ProductSellingType.SEllING)
                .name("카푸치노")
                .price(5000)
                .build();

        // when
        ProductResponse productResponse = productService.createProduct(request);
        List<Product> products = productRepository.findAll();

        // then
        assertThat(productResponse)
                .extracting("productNumber", "type", "sellingType", "name", "price")
                .contains("004", ProductType.HANDMADE, ProductSellingType.SEllING, "카푸치노", 5000);

        assertThat(products).hasSize(4)
                .extracting("productNumber", "type", "sellingType", "name", "price")
                .containsExactlyInAnyOrder(
                        Tuple.tuple("001", ProductType.HANDMADE, ProductSellingType.SEllING, "아메리카노", 4000),
                        Tuple.tuple("002", ProductType.HANDMADE, ProductSellingType.HOLD, "카페라떼", 4500),
                        Tuple.tuple("003", ProductType.HANDMADE, ProductSellingType.STOP_SELLING, "팥빙수", 7000),
                        Tuple.tuple("004", ProductType.HANDMADE, ProductSellingType.SEllING, "카푸치노", 5000)
                );

    }

    @DisplayName("상품이 하나도 없는 경우 신규 상품을 등록하면 상품 번호는 001이다.")
    @Test
    void createProductWhenProductsIsEmpty() {
        // given
        ProductCreateRequest request = ProductCreateRequest.builder()
                .type(ProductType.HANDMADE)
                .sellingType(ProductSellingType.SEllING)
                .name("카푸치노")
                .price(5000)
                .build();

        // when
        ProductResponse productResponse = productService.createProduct(request);
        List<Product> products = productRepository.findAll();

        // then
        assertThat(productResponse)
                .extracting("productNumber", "type", "sellingType", "name", "price")
                .contains("001", ProductType.HANDMADE, ProductSellingType.SEllING, "카푸치노", 5000);

        assertThat(products).hasSize(1)
                .extracting("productNumber", "type", "sellingType", "name", "price")
                .containsExactlyInAnyOrder(
                        Tuple.tuple("001", ProductType.HANDMADE, ProductSellingType.SEllING, "카푸치노", 5000)
                );
    }

    private Product createProduct(String productNumber, ProductType type, ProductSellingType sellingType, String name, int price) {
        return Product.builder()
                .type(type)
                .sellingType(sellingType)
                .price(price)
                .name(name)
                .productNumber(productNumber)
                .build();
    }


}