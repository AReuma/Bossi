package com.example.bossi.service.product.cart;

import com.example.bossi.entity.product.Product;
import com.example.bossi.entity.product.ProductOption;
import com.example.bossi.exception.AppException;
import com.example.bossi.exception.ErrorCode;
import com.example.bossi.repository.seller.ProductRepository;
import com.example.bossi.response.product.cart.CartProductResponse;
import com.example.bossi.service.product.ProductCheck;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
        //List<String> productDeliveryPrice = new ArrayList<>();
        String productDeliveryFreePrice = "";
        String productDeliveryPrice = "";
        //List<String> productDeliveryFreePrice = new ArrayList<>();
        List<String> optionStr = new ArrayList<>();

        // 상품이 있을 경우
        if(flag){
            // JSON 문자열을 List로 변환
            String optionPriceJsonList = (String) redisTemplate.opsForHash().get(cartKey, productKey+".optionTotalPrice");
            String optionStrJsonList = (String) redisTemplate.opsForHash().get(cartKey, productKey+".optionStr");
            String getDeliveryPrice = (String) redisTemplate.opsForHash().get(cartKey, productKey+".deliveryPrice");
            String getDeliveryFreePrice = (String) redisTemplate.opsForHash().get(cartKey, productKey+".deliveryFreePrice");
            optionPrice = new ObjectMapper().readValue(optionPriceJsonList, new TypeReference<List<String>>() {});
            productDeliveryPrice = getDeliveryPrice;
            productDeliveryFreePrice = getDeliveryFreePrice;
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

                strBuilder.append(productOption.getOptionsName()).append(":").append(productOption.getProductDetailOptionList().get(index).getOptionValue()).append("#");
                log.info(strBuilder.toString());
            }
            productDeliveryPrice = String.valueOf(product.getDeliveryCharge());
            productDeliveryFreePrice = String.valueOf(product.getFreeDeliverTotalCharge());
            //productDeliveryFreePrice.add(String.valueOf(product.getFreeDeliverTotalCharge()));
            optionStr.add(strBuilder.toString());
            optionPrice.add(String.valueOf(productPrice));
        }

        if(!flag){
            redisTemplate.opsForHash().put(cartKey,productKey+".optionTotalPrice", new ObjectMapper().writeValueAsString(optionPrice));
        }

        redisTemplate.opsForHash().put(cartKey,productKey+".deliveryPrice", productDeliveryPrice);
        redisTemplate.opsForHash().put(cartKey,productKey+".deliveryFreePrice",  productDeliveryFreePrice);
        redisTemplate.opsForHash().put(cartKey,productKey+".optionStr",  new ObjectMapper().writeValueAsString(optionStr));

        log.info("저장된 상품의 옵션: {}", optionPrice.size());
    }

    @SneakyThrows
    @Override
    public void addToCart(String email, Long productId, String options, String optionCount){

        String productKey = "productId:"+productId.toString();
        String hasProductKey = "productId";
        String cartKey = "cart:"+ email;
        log.info("cartKey {}", cartKey);

        Product product = productRepository.findById(productId).orElseThrow(() -> new AppException(ErrorCode.BAD_REQUEST, "상품이 존재하지 않음"));

        int optionSize = product.getProductOptionList().size(); // 2개
        String[] part = options.split(",");
        String[] optionCounts = optionCount.split(",");

        ProductCheck productCheck = new ProductCheck();
        List<List<Integer>> optionList = productCheck.getOption(optionSize, part);
        List<String> optionCountList = new ArrayList<>(List.of(optionCounts));

        log.info("optionCountList: {}", optionCountList.toString());

        String getProductId = "";
        List<String> productIdList = new ArrayList<>();
        if(redisTemplate.opsForHash().hasKey(cartKey, "productId")){// 상품 1개 이상이 담겨 있을때
            getProductId = (String) redisTemplate.opsForHash().get(cartKey, "productId");
            productIdList = new ObjectMapper().readValue(getProductId, new TypeReference<List<String>>() {});
        }

        log.info("getProductId: {}", getProductId);

        if(productIdList.contains(productId.toString())){// 이미 장바구니에 해당 상품이 있을 경우
            log.info("이미 장바구니에 해당 상품이 존재함");
            //redisTemplate.opsForHash().get(userId, productKey);
           // 옵션의 상태를 비교해야함.
            //String option1Value = (String) redisTemplate.opsForHash().get(userId, productKey, "option1");
            String redisOptionSize = (String) redisTemplate.opsForHash().get(cartKey, productKey + ".optionSize");
            log.info("저장된 옵션의 개수 {}", redisOptionSize);
            log.info("사용자가 선택한 상품의 옵션 개수 {}", optionList.size());
            log.info("사용자가 선택한 상품의 옵션 {}", optionList);

            for(int j = 0; j < optionList.size(); j++) {    // 사용자가 선택한 상품의 옵션 개수
                String jsonList = new ObjectMapper().writeValueAsString(optionList.get(j));

                boolean checkEqual = false;
                for (int i = 0; i < Integer.parseInt(redisOptionSize); i++) {   // 저장된 옵션 개수만큼
                    String tmp = (String)redisTemplate.opsForHash().get(cartKey, productKey + ".option" + i);
                    log.info("option 가지고 오기 {}", tmp);

                    if (jsonList.equals(tmp)) {  // 옵션이 같을 경우에
                        checkEqual = true;
                        log.info("옵션이 같을 경우에");
                        String optionCountValue = (String) redisTemplate.opsForHash().get(cartKey, productKey + ".optionCount" + i);

                        int num = Integer.parseInt(optionCountValue) + Integer.parseInt(optionCountList.get(j));

                        redisTemplate.opsForHash().put(cartKey, productKey + ".optionCount" + i, String.valueOf(num));
                        break;
                    }
                }

                if(!checkEqual){
                    log.info("옵션이 다를 경우");

                    int tmpOptionSize = Integer.parseInt(redisOptionSize)+1;
                    redisTemplate.opsForHash().put(cartKey, productKey + ".optionSize", String.valueOf(tmpOptionSize));

                    String jsonLists = new ObjectMapper().writeValueAsString(optionList.get(j));

                    int tmpNum = (Integer.parseInt(redisOptionSize) + j);
                    redisTemplate.opsForHash().put(cartKey, productKey + ".option" + tmpNum, jsonLists); // option1: [1, 2], option2: [1, 3]

                    log.info("j: {}", j);
                    redisTemplate.opsForHash().put(cartKey, productKey + ".optionCount" + tmpNum, optionCountList.get(j));   // optionCount1: 2, optionCount2: 0
                    saveProductInfo(product, cartKey, productKey, optionList, true);
                }
            }

        }else { // 추가한 상품이 productId에 없을 경우

            if (redisTemplate.opsForHash().hasKey(cartKey, "cartCount")) {
                String n = (String) redisTemplate.opsForHash().get(cartKey, "cartCount");

                redisTemplate.opsForHash().put(cartKey, "cartCount", String.valueOf(Integer.parseInt(n) + 1));

            } else {
                redisTemplate.opsForHash().put(cartKey, "cartCount", String.valueOf(1));
            }


            if(redisTemplate.opsForHash().hasKey(cartKey, "productId")){    // 다른 상품이 저장되어 있을때
                if(!productIdList.contains(productId.toString())){
                    productIdList.add(String.valueOf(productId));
                    redisTemplate.opsForHash().put(cartKey, "productId", new ObjectMapper().writeValueAsString(productIdList));
                }

            }else {
                getProductId = (String) redisTemplate.opsForHash().get(cartKey, "productId");
                productIdList.add(productId.toString());

                redisTemplate.opsForHash().put(cartKey, "productId", new ObjectMapper().writeValueAsString(productIdList));
            }

            redisTemplate.opsForHash().put(cartKey, productKey+".optionSize", String.valueOf(optionList.size()));
            redisTemplate.opsForHash().put(cartKey, productKey+".storeName", product.getSeller().getStoreName());
            redisTemplate.opsForHash().put(cartKey, productKey+".productName", product.getName());
            redisTemplate.opsForHash().put(cartKey, productKey+".productPrice", String.valueOf(product.getRatingSum()));
            redisTemplate.opsForHash().put(cartKey, productKey+".productImg", product.getProductImgs().get(0).getImg());
            redisTemplate.opsForHash().put(cartKey, productKey+".productStockQuantity", String.valueOf(product.getStockQuantity()));

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

    @Override
    public List<CartProductResponse> showCartProduct(String email) throws JsonProcessingException {
        String cartKey = "cart:"+email;

        String productIdList = (String) redisTemplate.opsForHash().get(cartKey, "productId");

        System.out.println("======");
        System.out.println(productIdList);
        System.out.println("======");
        if (productIdList != null) {
            List<String> productInfoList = new ObjectMapper().readValue(productIdList, new TypeReference<List<String>>() {
            });
            log.info("productInfoList: {}", productInfoList);

            List<CartProductResponse> responses = new ArrayList<>();
            for (String productInfo : productInfoList) {
                String productKey = "productId:" + productInfo;
                log.info("productKey: {}", productKey);

                String redisOptionSize = (String) redisTemplate.opsForHash().get(cartKey, productKey + ".optionSize");

                System.out.println("redisOptionSize: " + redisOptionSize);

                Integer optionSize = Integer.parseInt(redisOptionSize); // redis에 있는 옵션 개수

                log.info("redusIotuibSize: {}", redisOptionSize);

                String getOption;
                List<List<Integer>> optionList = new ArrayList<>();
                List<Integer> optionCountList = new ArrayList<>();
                float totalPrice = 0;
                for (int i = 0; i < optionSize; i++) {
                    String option = (String) redisTemplate.opsForHash().get(cartKey, productKey + ".option" + i);
                    List<Integer> s = new ObjectMapper().readValue(option, new TypeReference<List<Integer>>() {
                    });
                    optionList.add(s);

                    String optionCount = (String) redisTemplate.opsForHash().get(cartKey, productKey + ".optionCount" + i);
                    optionCountList.add(new ObjectMapper().readValue(optionCount, new TypeReference<Integer>() {
                    }));
                }


                String getOptionTotalPrice = (String) redisTemplate.opsForHash().get(cartKey, productKey + ".optionTotalPrice");
                List<Float> getOptionTotalPriceList = new ObjectMapper().readValue(getOptionTotalPrice, new TypeReference<List<Float>>() {
                });

                for (Float s : getOptionTotalPriceList) {
                    totalPrice += s;
                }

                String getStoreName = (String) redisTemplate.opsForHash().get(cartKey, productKey + ".storeName");
                String getProductName = (String) redisTemplate.opsForHash().get(cartKey, productKey + ".productName");
                String getProductImg = (String) redisTemplate.opsForHash().get(cartKey, productKey + ".productImg");
                String getDeliveryPrice = (String) redisTemplate.opsForHash().get(cartKey, productKey + ".deliveryPrice");
                String getProductPrice = (String) redisTemplate.opsForHash().get(cartKey, productKey + ".productPrice");

                float productPrice = Float.parseFloat(getProductPrice);
                float deliverPrice = Float.parseFloat(getDeliveryPrice);

                String getDeliveryFreePrice = (String) redisTemplate.opsForHash().get(cartKey, productKey + ".deliveryFreePrice");
                float deliverFreePrice = Float.parseFloat(getDeliveryFreePrice);

                float totalPricePlusDelivery = totalPrice;
                if (totalPrice < Float.parseFloat(getDeliveryFreePrice)) {
                    totalPricePlusDelivery += Float.parseFloat(getDeliveryPrice);
                }


                String getProductStockQuantity = (String) redisTemplate.opsForHash().get(cartKey, productKey + ".productStockQuantity");

                String getOptionStr = (String) redisTemplate.opsForHash().get(cartKey, productKey + ".optionStr");
                List<String> strOption = new ObjectMapper().readValue(getOptionStr, new TypeReference<List<String>>() {
                });

                List<List<String>> optionStrs = new ArrayList<>();

                for (int i = 0; i < strOption.size(); i++) {
                    StringTokenizer stringTokenizer = new StringTokenizer(strOption.get(i), "#");
                    List<String> str = new ArrayList<>();
                    while (stringTokenizer.hasMoreTokens()) {
                        String token = stringTokenizer.nextToken();
                        str.add(token);
                    }
                    optionStrs.add(str);
                }

                //String storeName, String productName, List<Float> optionTotalPrice, String productImg, List<List<Integer>> option, List<Integer> optionCount, List<List<String>> optionStr, Float deliveryCharge, Float freeDeliverTotalCharge, Float totalPrice, String stockQuantity
                CartProductResponse cartProductResponse = new CartProductResponse(productInfo, getStoreName, getProductName, getOptionTotalPriceList, getProductImg, optionList, optionCountList, optionStrs, deliverPrice, deliverFreePrice, totalPrice, totalPricePlusDelivery, getProductStockQuantity, productPrice);
                responses.add(cartProductResponse);
            }

            return responses;
        }else {
            return null;
        }
    }
// 장바구니에 보여야하는 정보들
    // 수량
    // 전체가격
    // 옵션들
    // 배송비
    // 무료배송 가격
}
