package com.example.cafekiosk.spring.domain.product;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
public enum ProductSellingType {
    SElling("판매중"),
    HOLD("판매 보류"),
    STOP_SELLING("판매 중지");

    private final String text;

    public static List<ProductSellingType> display() {
        return List.of(SElling, HOLD);
    }
}
