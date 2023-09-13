package com.example.bossi.controller.seller;

import com.example.bossi.dto.seller.CreateContentRequest;
import com.example.bossi.dto.FileDetail;
import com.example.bossi.response.product.CategoryResponse;
import com.example.bossi.response.product.FileUploadResponse;
import com.example.bossi.service.seller.SellerService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "SellerController", description = "판매자 관련 api 입니다.")
@RequestMapping("/api/v1/seller/product")
public class SellerController {

    private final SellerService sellerService;

    @GetMapping("/category")
    public ResponseEntity<List<CategoryResponse>> categoryList(){
        log.info("categoryList");

        return sellerService.categoryList();
    }

    @PostMapping("/create")
    public ResponseEntity<String> createContent(@RequestBody CreateContentRequest contentRequest){
        log.info("createContent");

        return sellerService.createContent(contentRequest);
    }

    @PostMapping(path = "/save/contentImg")
    public ResponseEntity<FileUploadResponse> saveContentImg(@RequestPart("image") MultipartFile multipartFile, @RequestPart("id") String id) {
        return ResponseEntity.ok(sellerService.save(multipartFile, id));
    }
}