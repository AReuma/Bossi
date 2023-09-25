package com.example.bossi.response.product;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LiverProductListResponse {

    private final Long productId;
    private final String storeName;
    private final String productTitle;
    private final int rating;
    private final String preview;
    private final String productImg;

}
