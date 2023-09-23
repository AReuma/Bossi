package com.example.bossi.response.product;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ProductOption {
    private final String option;
    private final List<String> optionDetail;
}
