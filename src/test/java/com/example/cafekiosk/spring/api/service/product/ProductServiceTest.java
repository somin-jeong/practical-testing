package com.example.cafekiosk.spring.api.service.product;

import com.example.cafekiosk.spring.api.service.product.request.ProductCreateRequest;
import com.example.cafekiosk.spring.api.service.product.response.ProductResponse;
import com.example.cafekiosk.spring.domain.product.Product;
import com.example.cafekiosk.spring.domain.product.ProductSellingType;
import com.example.cafekiosk.spring.domain.product.ProductType;
import com.example.cafekiosk.spring.domain.product.repository.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.PriorityQueue;

@SpringBootTest
@ActiveProfiles("test")
class ProductServiceTest {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;

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

        // then
        Assertions.assertThat(productResponse)
                .extracting("productNumber", "type", "sellingStatus", "name", "price")
                .contains("004", ProductType.HANDMADE, ProductSellingType.SEllING, "카푸치노", 5000);
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