package com.example.bossi.service.product;

import com.example.bossi.entity.product.ProductOption;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductCheck {

    // 프론트에서 상품, 상품 옵션, 옵션 개수가 있을 경우 어떻게 구현할까

    // 상품 옵션 출력하기
    public List<List<Integer>> getOption(int optionSize, String[] choiceOption){
        List<List<Integer>> optionList = new ArrayList<>();// [[1,2], [2,3]].. n개

        // 6개
        for (int i = 0; i < choiceOption.length;) { // optionCount만큼 n개씩
            List<Integer> pair = new ArrayList<>();
            for (int j = 0; j < optionSize; j++) {
                pair.add(Integer.parseInt(choiceOption[i]));
                i++;
            }
            optionList.add(pair);
        }

        return optionList;
    }

    public List<Map<String, Object>> getOptionAndOptionValue(List<ProductOption> productOptionList){
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

        return productOption;
    }
}
