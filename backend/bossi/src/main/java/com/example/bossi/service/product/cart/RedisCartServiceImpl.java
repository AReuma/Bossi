package com.example.bossi.service.product.cart;

import com.example.bossi.entity.product.Product;
import com.example.bossi.entity.product.ProductOption;
import com.example.bossi.exception.AppException;
import com.example.bossi.exception.ErrorCode;
import com.example.bossi.repository.seller.ProductRepository;
import com.example.bossi.service.product.ProductCheck;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RedisCartServiceImpl implements RedisCartService{

    private final ProductRepository productRepository;
    private final RedisTemplate<String, Object> redisTemplate;


    public void saveProductInfo(Product product, String cartKey, String productKey, List<List<Integer>> option, boolean flag) throws JsonProcessingException {
        //option [[1,2], [2,3]]

        // 옵션들
        // 배송비
        // 무료배송 가격
        int productOptionSize = product.getProductOptionList().size();
        List<String> optionPrice = new ArrayList<>();
        List<String> productDeliveryPrice = new ArrayList<>();
        List<String> productDeliveryFreePrice = new ArrayList<>();
        List<String> optionStr = new ArrayList<>();

        // 상품이 있을 경우
        if(flag){
// JSON 문자열을 List로 변환
            String optionPriceJsonList = (String) redisTemplate.opsForHash().get(cartKey, productKey+".optionTotalPrice");
            String optionStrJsonList = (String) redisTemplate.opsForHash().get(cartKey, productKey+".optionStr");
            String deliveryPriceJsonList = (String) redisTemplate.opsForHash().get(cartKey, productKey+".deliveryPrice");
            String deliveryFreePriceJsonList = (String) redisTemplate.opsForHash().get(cartKey, productKey+".deliveryFreePrice");
            optionPrice = new ObjectMapper().readValue(optionPriceJsonList, new TypeReference<List<String>>() {});
            productDeliveryPrice = new ObjectMapper().readValue(deliveryPriceJsonList, new TypeReference<List<String>>() {});
            productDeliveryFreePrice = new ObjectMapper().readValue(deliveryFreePriceJsonList, new TypeReference<List<String>>() {});
            optionStr = new ObjectMapper().readValue(optionStrJsonList, new TypeReference<List<String>>() {});

            log.info(optionStr.get(0));
        }

        for(int i = 0; i < option.size(); i++){     // 2번
            float productPrice = product.getRatingSum();
            StringBuilder strBuilder = new StringBuilder();
            for(int j = 0; j < productOptionSize; j++){ // 판매자가 저장한 옵션 개수
                ProductOption productOption = product.getProductOptionList().get(j);
                Integer index = option.get(i).get(j);

                productPrice += productOption.getProductDetailOptionList().get(index).getPrice();
                productDeliveryPrice.add(String.valueOf(product.getDeliveryCharge()));
                productDeliveryFreePrice.add(String.valueOf(product.getFreeDeliverTotalCharge()));

                strBuilder.append(productOption.getOptionsName()).append(":").append(productOption.getProductDetailOptionList().get(index).getOptionValue()).append(" ");
                log.info(strBuilder.toString());
            }
            optionStr.add(strBuilder.toString());
            optionPrice.add(String.valueOf(productPrice));
        }

        redisTemplate.opsForHash().put(cartKey,productKey+".optionTotalPrice", new ObjectMapper().writeValueAsString(optionPrice));
        redisTemplate.opsForHash().put(cartKey,productKey+".optionStr",  new ObjectMapper().writeValueAsString(optionStr));
        redisTemplate.opsForHash().put(cartKey,productKey+".deliveryPrice",  new ObjectMapper().writeValueAsString(productDeliveryPrice));
        redisTemplate.opsForHash().put(cartKey,productKey+".deliveryFreePrice",  new ObjectMapper().writeValueAsString(productDeliveryFreePrice));

        log.info("저장된 상품의 옵션: {}", optionPrice.size());
    }

    @SneakyThrows
    @Override
    public void addToCart(String email, Long productId, String options, String optionCount){

        String productKey = "productId:"+productId.toString();
        String hasProductKey = "productId"+productId;
        String cartKey = "cart:"+ email;
        log.info("cartKey {}", cartKey);

        Product product = productRepository.findById(productId).orElseThrow(() -> new AppException(ErrorCode.BAD_REQUEST, "상품이 존재하지 않음"));

        int optionSize = product.getProductOptionList().size(); // 2개
        String[] part = options.split(",");
        String[] optionCounts = optionCount.split(",");

        ProductCheck productCheck = new ProductCheck();
        List<List<Integer>> optionList = productCheck.getOption(optionSize, part);
        List<String> optionCountList = new ArrayList<>(List.of(optionCounts));

        if(redisTemplate.opsForHash().hasKey(cartKey, hasProductKey)){// 이미 장바구니에 해당 상품이 있을 경우
            log.info("이미 장바구니에 해당 상품이 존재함");
            //redisTemplate.opsForHash().get(userId, productKey);
           // 옵션의 상태를 비교해야함.
            //String option1Value = (String) redisTemplate.opsForHash().get(userId, productKey, "option1");
            String redisOptionSize = (String) redisTemplate.opsForHash().get(cartKey, productKey + ".optionSize");
            log.info("저장된 옵션의 개수 {}", redisOptionSize);
            log.info("사용자가 선택한 상품의 옵션 개수 {}", optionList.size());

            for(int j = 0; j < optionList.size(); j++) {    // 사용자가 선택한 상품의 옵션 개수
                String jsonList = new ObjectMapper().writeValueAsString(optionList.get(j));

                for (int i = 0; i < Integer.parseInt(redisOptionSize); i++) {   // 저장된 옵션 개수만큼
                    String tmp = (String)redisTemplate.opsForHash().get(cartKey, productKey + ".option" + i);
                    log.info("option 가지고 오기 {}", tmp);

                    if (jsonList.equals(tmp)) {  // 옵션이 같을 경우에
                        log.info("옵션이 같을 경우에");
                        String optionCountValue = (String) redisTemplate.opsForHash().get(cartKey, productKey + ".optionCount" + i);
                        int num = Integer.parseInt(optionCountValue) + Integer.parseInt(optionCountList.get(j));

                        redisTemplate.opsForHash().put(cartKey, productKey + ".optionCount" + i, String.valueOf(num));
                    } else { // 옵션이 다른경우
                        log.info("옵션이 다를 경우");
                        int tmpOptionSize = Integer.parseInt(redisOptionSize)+1;
                        redisTemplate.opsForHash().put(cartKey, productKey + ".optionSize", String.valueOf(tmpOptionSize));

                        String jsonLists = new ObjectMapper().writeValueAsString(optionList.get(j));

                        int tmpNum = (Integer.parseInt(redisOptionSize) + j);
                        redisTemplate.opsForHash().put(cartKey, productKey + ".option" + tmpNum, jsonLists); // option1: [1, 2], option2: [1, 3]
                        redisTemplate.opsForHash().put(cartKey, productKey + ".optionCount" + tmpNum, optionCountList.get(j));   // optionCount1: 2, optionCount2: 0
                        saveProductInfo(product, cartKey, productKey, optionList, true);
                    }
                }
            }

        }else {

            if (redisTemplate.opsForHash().hasKey(cartKey, "cartCount")) {
                String n = (String) redisTemplate.opsForHash().get(cartKey, "cartCount");

                redisTemplate.opsForHash().put(cartKey, "cartCount", String.valueOf(Integer.parseInt(n) + 1));

            } else {
                redisTemplate.opsForHash().put(cartKey, "cartCount", String.valueOf(1));
            }

            redisTemplate.opsForHash().put(cartKey, "productId"+productId, productId.toString());
            redisTemplate.opsForHash().put(cartKey, productKey+".optionSize", String.valueOf(optionList.size()));

            for (int i = 0; i < optionList.size(); i++) {
                String jsonList = new ObjectMapper().writeValueAsString(optionList.get(i));

                redisTemplate.opsForHash().put(cartKey, productKey+".option" + i, jsonList); // option1: [1, 2], option2: [1, 3]
                redisTemplate.opsForHash().put(cartKey, productKey+".optionCount" + i, optionCountList.get(i));   // optionCount1: 2, optionCount2: 0
            }
            saveProductInfo(product, cartKey, productKey, optionList, false);

            log.info("저장 완료");
        }

    }

    @Override
    public Integer checkCartCount(String email) {
        String cartKey = "cart:"+email;

        String cartCount = (String) redisTemplate.opsForHash().get(cartKey, "cartCount");
        log.info("checkCartCount: {}", cartCount);

        if (cartCount != null) return Integer.parseInt(cartCount);
        else return 0;
    }
// 장바구니에 보여야하는 정보들
    // 수량
    // 전체가격
    // 옵션들
    // 배송비
    // 무료배송 가격
}
