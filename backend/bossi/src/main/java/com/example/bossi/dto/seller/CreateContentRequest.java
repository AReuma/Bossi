package com.example.bossi.dto.seller;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.awt.*;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Getter
public class CreateContentRequest {

    private String sellerId;
    private String category;
    private String title;
    private Float price;
    private int rating;
    private Float ratingPrice;
    private Float deliveryCount;
    private Float freeCount;
    private List<Map<String, String>> options;
    private List<List<Map<String, String>>> detailOption;
    private int stockQuantity;
    private String content;
    private List<String> imgUrlLists;
    private List<String> allImgUrlList;

    public CreateContentRequest(){}
}
