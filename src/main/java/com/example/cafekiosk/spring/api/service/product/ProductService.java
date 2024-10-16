package com.example.cafekiosk.spring.api.service.product;

import com.example.cafekiosk.spring.api.controller.product.request.ProductCreateRequest;
import com.example.cafekiosk.spring.api.service.product.request.ProductCreateServiceRequest;
import com.example.cafekiosk.spring.api.service.product.response.ProductResponse;
import com.example.cafekiosk.spring.domain.product.Product;
import com.example.cafekiosk.spring.domain.product.ProductSellingType;
import com.example.cafekiosk.spring.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepository;

    public List<ProductResponse> getSellingProducts() {
        List<Product> productList = productRepository.findAllBySellingTypeIn(ProductSellingType.display());

        return productList.stream()
                .map(ProductResponse::of)
                .collect(Collectors.toList());
    }

    // 동시셩 이슈
    // DB의 ProductNumber라는 필드에 유니크 인덱스 제약조건을 걸고 재시도하는 로직을 추가한다. 누군가 먼저 번호를 선점했다면 재시도
    // 해결: 상품 번호가 증가하는 값이 아니라 UUID 사용 -> 아예 유니크한 값
    @Transactional
    public ProductResponse createProduct(ProductCreateServiceRequest request) {
        // DB에서 마지막에 저장된 Product의 상품 번호를 읽어와서 +1
        // 009 -> 010
        String nextProductNumber = createNextProductNumber();

        Product product = request.toEntity(nextProductNumber);
        Product savedProduct = productRepository.save(product);

        return ProductResponse.of(savedProduct);
    }

    private String createNextProductNumber() {
        String latestProductNumber = productRepository.findLatestProductNumber();
        if (latestProductNumber == null) {
            return "001";
        }

        int latestProductNumberInt = Integer.parseInt(latestProductNumber);
        int nextProductNumberInt = latestProductNumberInt + 1;

        return String.format("%03d", nextProductNumberInt);
    }
}
