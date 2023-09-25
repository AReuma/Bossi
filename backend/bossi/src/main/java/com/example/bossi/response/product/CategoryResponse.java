package com.example.bossi.response.product;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CategoryResponse {

    @ApiModelProperty(example = "카테고리 아이디")
    private final Long categoryId;

    @ApiModelProperty(example = "카테고리 이름")
    private final String category;
}
