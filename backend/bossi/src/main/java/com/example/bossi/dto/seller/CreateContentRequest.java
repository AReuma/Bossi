package com.example.bossi.dto.seller;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class CreateContentRequest {

    public String title;
    public String content;
    //public List<String> imgUrl;
}
