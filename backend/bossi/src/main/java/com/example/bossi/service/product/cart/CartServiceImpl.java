package com.example.bossi.service.product.cart;

import com.example.bossi.entity.product.Product;
import com.example.bossi.entity.product.ProductOption;
import com.example.bossi.exception.AppException;
import com.example.bossi.exception.ErrorCode;
import com.example.bossi.repository.seller.ProductRepository;
import com.example.bossi.response.product.cart.DirectButOrderItemInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CartServiceImpl implements CartService{

    private final ProductRepository productRepository;

    @Override
    public ResponseEntity<DirectButOrderItemInfo> directBuyOrderList(Long productId, String options, String optionCount) {
        // 작가 이름, 상품이름, 옵션, 선택 갯수, 가격
        try {
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new AppException(ErrorCode.BAD_REQUEST, "상품이 존재하지 않음"));

            // 최종 가격
            // 1. 선택한 옵션 찾기
            // 2. 선택한 옵션 + 원래 가격
            // 3. 옵션 상품에 대한 가격 정리

            // 옵션 주문 수 count.length
            String[] count = optionCount.split(",");
            List<Integer> optionCountList = new ArrayList<>();

            for (String c : count) {
                optionCountList.add(Integer.parseInt(c));
            }

            // 옵션 개수 optionSize
            int optionSize = product.getProductOptionList().size(); // 2개
            String[] part = options.split(","); //
            List<List<Integer>> optionList = new ArrayList<>();// [[1,2], [2,3]].. n개

            // 6개
            for (int i = 0; i < part.length;) { // optionCount만큼 n개씩
                List<Integer> pair = new ArrayList<>();
                for (int j = 0; j < optionSize; j++) {
                    pair.add(Integer.parseInt(part[i]));
                    i++;
                }
                optionList.add(pair);
            }

            List<Float> optionPrice = new ArrayList<>();    // 옵션마다 가격
            List<ProductOption> productOptionList = product.getProductOptionList(); // 색상, 포장
            List<List<Integer>> optionIndex = new ArrayList<>();

            // option 문자열 만들기
            List<String> optionStr = new ArrayList<>();
            for (int i = 0; i < optionList.size(); i++) { // 3개
                StringBuilder str = new StringBuilder();
                float price = product.getRatingSum();   // 옵션 총 가격
                List<Integer> indexList = new ArrayList<>();

                for(int j = 0; j < optionSize; j++){    //2개
                    String optionsName = productOptionList.get(j).getOptionsName();
                    str.append(" • ").append(optionsName);

                    Integer index = optionList.get(i).get(j); //[1, 2]
                    indexList.add(index);

                    String value = productOptionList.get(j).getProductDetailOptionList().get(index).getOptionValue();
                    price += productOptionList.get(j).getProductDetailOptionList().get(index).getPrice();
                    str.append(" : ").append(value);
                }
                optionPrice.add(price);
                optionStr.add(str.toString()); // • 스마트톡 옵션 : 투명아크릴톡 • 추가케이스 : 선택안함
            }

            float optionTotalPrice = 0;
            // 총 가격
            for (Float price : optionPrice) {
                optionTotalPrice += price;
            }

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

            DirectButOrderItemInfo directButOrderItemInfo = new DirectButOrderItemInfo(product.getSeller().getStoreName(), product.getPrice(), product.getRatingCont(), product.getRatingSum(), product.getName(), optionCountList, optionTotalPrice, product.getProductImgs().get(0).getImg(), optionList, optionStr, optionPrice, product.getDeliveryCharge(), product.getFreeDeliverTotalCharge(), product.getStockQuantity(), productOption);
            return ResponseEntity.ok().body(directButOrderItemInfo);

        } catch (Exception e) {
            log.error("상품 조회 중 오류 발생: {}", e.getMessage());

            return ResponseEntity.badRequest().body(null);
        }
    }

    @Transactional
    @Override
    public ResponseEntity<String> modifyDirectOption(List<String> options) {
        return null;
    }
}
