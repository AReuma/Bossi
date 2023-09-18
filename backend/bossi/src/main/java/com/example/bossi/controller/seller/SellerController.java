package com.example.bossi.controller.seller;

import com.example.bossi.dto.seller.CreateContentRequest;
import com.example.bossi.dto.seller.JoinSellerRequest;
import com.example.bossi.dto.seller.TestRequest;
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
@RequestMapping("/api/v1/seller")
public class SellerController {

    private final SellerService sellerService;

    @PostMapping("/join")
    public ResponseEntity<String> sellerRegister(@ModelAttribute JoinSellerRequest joinSellerRequest){

        return sellerService.registerSeller(joinSellerRequest);
    }

    @GetMapping("/product/category")
    public ResponseEntity<List<CategoryResponse>> categoryList(){
        log.info("categoryList");

        return sellerService.categoryList();
    }

    @PostMapping("/product/{id}/saveProductImage")
    public ResponseEntity<String> saveProductImage(@RequestPart(value = "productImages") List<MultipartFile> productImages, @PathVariable Long id){
        log.info("saveProductImage");
        return sellerService.saveProductImage(productImages, id);
    }

    @PostMapping("/product/create")
    public ResponseEntity<Long> createContent(@RequestBody CreateContentRequest contentRequest){
        log.info("createContent");
        log.info("saveCOntent{}", contentRequest.getSellerId());

        return sellerService.createContent(contentRequest);
    }

    @PostMapping("/product/save")
    public ResponseEntity<String> saveContent(@RequestPart("productImages") List<MultipartFile> multipartFile, @RequestPart("jsonData") TestRequest testRequest){
        log.info("saveCOntent{}", multipartFile);
        log.info("saveCOntent{}", testRequest);
        return ResponseEntity.ok().body("sdf");
    }

    @PostMapping(path = "/product/save/contentImg")
    public ResponseEntity<FileUploadResponse> saveContentImg(@RequestPart("image") MultipartFile multipartFile, @RequestPart("id") String id) {
        log.info("saveContentImg{}", id);
        return ResponseEntity.ok(sellerService.save(multipartFile, id));
    }
}