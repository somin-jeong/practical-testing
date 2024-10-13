package com.example.cafekiosk.spring.api.controller.product;

import com.example.cafekiosk.spring.api.service.product.ProductService;
import com.example.cafekiosk.spring.api.service.product.request.ProductCreateRequest;
import com.example.cafekiosk.spring.domain.product.ProductSellingType;
import com.example.cafekiosk.spring.domain.product.ProductType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;

@WebMvcTest(controllers = ProductController.class)
class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    @DisplayName("신규 상품을 등록한다.")
    @Test
    void createProduct() throws Exception {
        // given
        ProductCreateRequest request = ProductCreateRequest.builder()
                .type(ProductType.HANDMADE)
                .sellingType(ProductSellingType.SEllING)
                .name("아메리카노")
                .price(4000)
                .build();

        // when // then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/products/new")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @DisplayName("신규 상품을 등록할 때, 상품 판매 상태가 없으면 예외를 보낸다.")
    @Test
    void createProductWithoutSellingType() throws Exception {
        // given
        ProductCreateRequest request = ProductCreateRequest.builder()
                .type(ProductType.HANDMADE)
                .name("아메리카노")
                .price(4000)
                .build();

        // when // then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/products/new")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("400"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("상품 판매 상태는 필수입니다."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty());
    }

    @DisplayName("신규 상품을 등록할 때, 상품 이름이 없으면 예외를 보낸다.")
    @Test
    void createProductWithoutName() throws Exception {
        // given
        ProductCreateRequest request = ProductCreateRequest.builder()
                .type(ProductType.HANDMADE)
                .sellingType(ProductSellingType.SEllING)
                .price(4000)
                .build();

        // when // then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/products/new")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("400"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("상품 이름은 필수입니다."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty());
    }

    @DisplayName("신규 상품을 등록할 때, 가격이 양수가 아니면 예외를 보낸다.")
    @Test
    void createProductWithoutPrice() throws Exception {
        // given
        ProductCreateRequest request = ProductCreateRequest.builder()
                .type(ProductType.HANDMADE)
                .sellingType(ProductSellingType.SEllING)
                .name("아메리카노")
                .price(0)
                .build();

        // when // then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/products/new")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("400"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("상품 가격은 0보다 커야합니다."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty());
    }
}