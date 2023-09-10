package com.example.bossi.service.seller;

import com.example.bossi.dto.seller.CreateContentRequest;
import com.example.bossi.entity.product.Product;
import com.example.bossi.entity.product.ProductContent;
import com.example.bossi.repository.seller.ProductContentRepository;
import com.example.bossi.repository.seller.ProductRepository;
import com.example.bossi.repository.seller.SellerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SellerService {

    private final ProductRepository productRepository;
    private final ProductContentRepository productContentRepository;

    public ResponseEntity<String> createContent(CreateContentRequest request) {
        System.out.println("================");
        System.out.println(request.getContent());
        System.out.println("================");

        Product product = Product.builder()
                .name("test")
                .build();

        productRepository.save(product);

        ProductContent productContent = ProductContent.builder()
                .content(request.getContent())
                .product(product)
                .build();

        productContentRepository.save(productContent);

        return ResponseEntity.ok().body("");
    }

    public CreateContentRequest showContent(Long id) {
        ProductContent productContentByProductId = productContentRepository.findProductContentByProductId(id);

        return new CreateContentRequest("title", productContentByProductId.getContent());
    }
}
