package com.example.bossi.dto.seller;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class TestRequest {

    private int category;
    private String title;
    private Float price;

    public TestRequest(){}
}
