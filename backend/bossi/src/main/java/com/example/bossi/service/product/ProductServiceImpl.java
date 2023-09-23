package com.example.bossi.service.product;

import com.example.bossi.entity.product.*;
import com.example.bossi.exception.AppException;
import com.example.bossi.exception.ErrorCode;
import com.example.bossi.repository.product.ProductOptionRepository;
import com.example.bossi.repository.seller.ProductImgRepository;
import com.example.bossi.repository.seller.ProductRepository;
import com.example.bossi.response.product.LiverProductListResponse;
import com.example.bossi.response.product.ProductContentResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final ProductOptionRepository productOptionRepository;
    private final ProductImgRepository productImgRepository;

    @Override
    public ResponseEntity<List<LiverProductListResponse>> liverProductList() {
        List<Product> findProduct = productRepository.findFirst5ByOrderByIdAsc();

        log.info(findProduct.size() + "poductList size()");
        List<LiverProductListResponse> responses = new ArrayList<>();

        for (Product product : findProduct) {
            responses.add(new LiverProductListResponse(product.getId(), product.getSeller().getStoreName(), product.getName(), 4, product.getName(), product.getProductImgs().get(0).getImg()));
        }

        return ResponseEntity.ok().body(responses);
    }

    @Override
    public ResponseEntity<ProductContentResponse> showProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.BAD_REQUEST, "해당 상품이 없습니다."));
        List<ProductOption> productOptionList = productOptionRepository.findByProductId(product.getId());

        List<Map<String, Object>> productOption = new ArrayList<>();

        for (ProductOption option : productOptionList) {
            Map<String, Object> data1 = new HashMap<>();
            Map<String, String> detail1 = new HashMap<>();
            Map<String, Float> price = new HashMap<>();

            data1.put("option", option.getOptionsName());
            for (int i = 0; i < option.getProductDetailOptionList().size(); i++) {
                String name = String.valueOf(i);
                detail1.put(name, option.getProductDetailOptionList().get(i).getOptionValue());
                price.put(name, option.getProductDetailOptionList().get(i).getPrice());
            }

            data1.put("optionDetail", detail1);
            data1.put("price", price);
            productOption.add(data1);
        }

        List<String> productImage = new ArrayList<>();

        for (ProductImg productImg : product.getProductImgs()) {
            productImage.add(productImg.getImg());
        }

        ProductContentResponse response = new ProductContentResponse(product.getId(),product.getCategory().getName(), product.getSeller().getStoreName(), product.getName(), product.getPrice(), product.getRatingCont(), product.getRatingSum(), product.getDeliveryCharge(), product.getFreeDeliverTotalCharge(), product.getSalesQuantity(), productOption, product.getStockQuantity(), product.getProductContent().getContent(), productImage);
        //String category, String title, int price, int rating, int ratingPrice, int deliveryCount, int freeCount, List<String> options, List<List<String>> detailOption, int stockQuantity, String content
        return ResponseEntity.ok().body(response);
    }

}
