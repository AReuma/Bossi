package com.example.bossi.service.seller;

import com.example.bossi.dto.FileDetail;
import com.example.bossi.dto.seller.CreateContentRequest;
import com.example.bossi.entity.product.Category;
import com.example.bossi.entity.product.Product;
import com.example.bossi.entity.product.ProductContent;
import com.example.bossi.repository.seller.CategoryRepository;
import com.example.bossi.repository.seller.ProductContentRepository;
import com.example.bossi.repository.seller.ProductRepository;
import com.example.bossi.repository.seller.SellerRepository;
import com.example.bossi.response.product.CategoryResponse;
import com.example.bossi.response.product.FileUploadResponse;
import com.example.bossi.response.product.ProductContentResponse;
import com.example.bossi.service.aws.AwsS3UploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
@RequiredArgsConstructor
public class SellerService {

    private final ProductRepository productRepository;
    private final ProductContentRepository productContentRepository;
    private final CategoryRepository categoryRepository;
    private final AwsS3UploadService uploadService;

    public ResponseEntity<String> createContent(CreateContentRequest request) {
        System.out.println("================");
        System.out.println(request.getContent());
        System.out.println("================");

        Product product = Product.builder()
                .name("test")
                .build();

        productRepository.save(product);

        if(!request.getImgUrlLists().isEmpty()){

            for (String o : request.getImgUrlLists()) {
                request.getAllImgUrlList().remove(o);
            }

            uploadService.deleteMissingImages(request.getAllImgUrlList());
        }

        ProductContent productContent = ProductContent.builder()
                .content(request.getContent())
                .product(product)
                .build();

        productContentRepository.save(productContent);

        return ResponseEntity.ok().body("");
    }

    public FileUploadResponse save(MultipartFile multipartFile, String id) {
        FileDetail fileDetail = FileDetail.multipartOf(multipartFile);
        String imgUrl = uploadService.store(fileDetail.getPath(), multipartFile, id);
        return new FileUploadResponse(imgUrl, fileDetail.getId());
    }

    public CreateContentRequest showContent(Long id) {
        ProductContent productContentByProductId = productContentRepository.findProductContentByProductId(id);
        List<String> test = new ArrayList<>();
        List<String> test2 = new ArrayList<>();

        return new CreateContentRequest("title", productContentByProductId.getContent(), test, test2);
    }

    public ResponseEntity<List<CategoryResponse>> categoryList() {
        List<Category> findAll = categoryRepository.findAll();
        List<CategoryResponse> categoryList = findAll.stream()
                .map(o -> new CategoryResponse(o.getId(), o.getName()))
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(categoryList);
    }
}
