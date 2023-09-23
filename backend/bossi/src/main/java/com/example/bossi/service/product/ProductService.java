package com.example.bossi.service.product;

import com.example.bossi.response.product.LiverProductListResponse;
import com.example.bossi.response.product.ProductContentResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ProductService {
    ResponseEntity<List<LiverProductListResponse>> liverProductList();

    ResponseEntity<ProductContentResponse> showProduct(Long id);
}
