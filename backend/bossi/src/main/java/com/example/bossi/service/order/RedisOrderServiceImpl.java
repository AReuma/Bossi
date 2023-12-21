package com.example.bossi.service.order;

import com.example.bossi.dto.order.RedisOrderProductInfo;
import com.example.bossi.repository.seller.ProductRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RedisOrderServiceImpl implements RedisOrderService{

    private final RedisTemplate<String, Object> redisTemplate;
    private final ProductRepository productRepository;

    @Override
    public void saveOrderNum(String orderNum) {
        redisTemplate.opsForValue().set("order:"+orderNum, orderNum, Duration.ofMinutes(2));
    }

    @Override
    public String getOrderNum(String orderNum) {
        //String orderNumber = (String) redisTemplate.opsForValue().get("order:" + orderNum);
        return (String) redisTemplate.opsForValue().get("order:" + orderNum);
    }

    @Override
    public List<RedisOrderProductInfo> orderProductInfo(List<Long> productIdList, String email) throws JsonProcessingException {
        String cartKey = "cart:"+ email;
        List<RedisOrderProductInfo> response = new ArrayList<>();
        for (Long productId : productIdList) {
            String productKey = "productId:"+productId.toString();

            String optionSize = (String) redisTemplate.opsForHash().get(cartKey, productKey + ".optionSize");   // 2

            //productId:5.optionSize
            //2

            //productId:5.option1
            //[1,0]
            //productId:5.optionCount1
            //1

            //productId:5.option0
            //[0,0]
            //productId:5.optionCount0
            //1

            /*
            {
                productId: 1,
                productOption: [[1,2], [2,3]],
                productCount: [1,2]
            },
            * */
            List<List<Integer>> productOption = new ArrayList<>();
            List<Integer> optionList = new ArrayList<>();
            List<Integer> optionCountList = new ArrayList<>();
            for(int i = 0; i < Integer.parseInt(optionSize); i++){
                String option = (String) redisTemplate.opsForHash().get(cartKey, productKey + ".option" + i);
                optionList = new ObjectMapper().readValue(option, new TypeReference<List<Integer>>() {});

                productOption.add(optionList);
                String optionCount = (String) redisTemplate.opsForHash().get(cartKey, productKey + ".optionCount" + i);
                optionCountList.add(Integer.parseInt(optionCount));
            }

            response.add(new RedisOrderProductInfo(productId, productOption, optionCountList));
        }

        return response;
    }

    @Override
    public void removerProduct(List<Long> productIdList, String email) {
        String cartKey = "cart:"+ email;
        for (Long productId : productIdList) {
            String productKey = "productId:" + productId.toString();

            String optionSize = (String) redisTemplate.opsForHash().get(cartKey, productKey + ".optionSize");

            for(int i = 0; i < Integer.parseInt(optionSize); i++){
                redisTemplate.opsForHash().delete(cartKey, productKey+".option"+i);
                redisTemplate.opsForHash().delete(cartKey, productKey+".optionCount"+i);
            }

            redisTemplate.opsForHash().delete(cartKey, productKey+".productName");
            redisTemplate.opsForHash().delete(cartKey, productKey+".optionTotalPrice");
            redisTemplate.opsForHash().delete(cartKey, productKey+".productPrice");
            redisTemplate.opsForHash().delete(cartKey, productKey+".optionStr");
            redisTemplate.opsForHash().delete(cartKey, productKey+".deliveryPrice");
            redisTemplate.opsForHash().delete(cartKey, productKey+".deliveryFreePrice");
            redisTemplate.opsForHash().delete(cartKey, productKey+".storeName");
            redisTemplate.opsForHash().delete(cartKey, productKey+".productStockQuantity");
            redisTemplate.opsForHash().delete(cartKey, productKey+".productImg");
        }

        // 삭제할 상품 개수
        int size = productIdList.size();
        String cartCount = (String) redisTemplate.opsForHash().get(cartKey, "cartCount");
        int newCartCount = Integer.parseInt(cartCount) - size;
        redisTemplate.opsForHash().put(cartKey, "cartCount", String.valueOf(newCartCount));

        // productId
        String productId = (String) redisTemplate.opsForHash().get(cartKey, "productId");
        try {
            List<String> productIds = new ObjectMapper().readValue(productId, new TypeReference<List<String>>() {});

            List<String> updatedList = new ArrayList<>();
            for (Long productIdLong : productIdList) {

                for (String id : productIds) {
                    if (!id.equals(productIdLong.toString())) {
                        updatedList.add(productId);
                    }
                }
            }

            redisTemplate.opsForHash().put(cartKey, "productId", new ObjectMapper().writeValueAsString(updatedList));

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
