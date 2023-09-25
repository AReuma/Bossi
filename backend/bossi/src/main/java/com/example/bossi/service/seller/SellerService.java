package com.example.bossi.service.seller;

import com.example.bossi.dto.seller.CreateContentRequest;
import com.example.bossi.dto.seller.JoinSellerRequest;
import com.example.bossi.response.product.CategoryResponse;
import com.example.bossi.response.product.FileUploadResponse;
import com.example.bossi.response.product.ProductContentResponse;
import org.springframework.http.ResponseEntity;;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;


public interface SellerService {


    ResponseEntity<Long> createContent(CreateContentRequest contentRequest);

    ResponseEntity<String> saveProductImage(List<MultipartFile> productImages, Long id);

    ResponseEntity<String> registerSeller(JoinSellerRequest joinSellerRequest);

    ResponseEntity<List<CategoryResponse>> categoryList();

    FileUploadResponse save(MultipartFile multipartFile, String id);
}
