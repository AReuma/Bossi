package com.example.bossi.response.product;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FileUploadResponse {

    private final String imgUrl;

    private final String imgName;
}
