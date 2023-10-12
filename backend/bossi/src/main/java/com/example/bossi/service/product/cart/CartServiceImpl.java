package com.example.bossi.service.product.cart;

import com.example.bossi.entity.Address;
import com.example.bossi.entity.User;
import com.example.bossi.entity.product.Product;
import com.example.bossi.entity.product.ProductOption;
import com.example.bossi.exception.AppException;
import com.example.bossi.exception.ErrorCode;
import com.example.bossi.repository.seller.ProductRepository;
import com.example.bossi.repository.user.UserRepository;
import com.example.bossi.response.product.cart.AddrInfo;
import com.example.bossi.response.product.cart.DirectButOrderItemInfo;
import com.example.bossi.response.product.cart.OrderProductInfo;
import com.example.bossi.service.product.ProductCheck;
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
    private final UserRepository userRepository;

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
            List<Integer> optionCountList = new ArrayList<>();  // 수량 개수

            for (String c : count) {
                optionCountList.add(Integer.parseInt(c));
            }

            // 옵션 개수 optionSize
            int optionSize = product.getProductOptionList().size(); // 2개
            String[] part = options.split(","); // 프론트에서 전달된 옵션의 개수
            //List<List<Integer>> optionList = new ArrayList<>();// [[1,2], [2,3]].. n개
            ProductCheck productCheck = new ProductCheck();
            List<List<Integer>> optionList = productCheck.getOption(optionSize, part);// [[1,2], [2,3]].. n개

            // 6개
           /* for (int i = 0; i < part.length;) { // optionCount만큼 n개씩
                List<Integer> pair = new ArrayList<>();
                for (int j = 0; j < optionSize; j++) {
                    pair.add(Integer.parseInt(part[i]));
                    i++;
                }
                optionList.add(pair);
            }*/

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
                optionPrice.add(price * optionCountList.get(i));
                optionStr.add(str.toString()); // • 스마트톡 옵션 : 투명아크릴톡 • 추가케이스 : 선택안함
            }

            float optionTotalPrice = 0;
            // 총 가격
            for (Float price : optionPrice) {
                optionTotalPrice += price;
            }

            //List<Map<String, Object>> productOption = new ArrayList<>();
            List<Map<String, Object>> productOption = productCheck.getOptionAndOptionValue(productOptionList);

            /*for (ProductOption option : productOptionList) {
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
            }*/

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

    @Override
    public ResponseEntity<OrderProductInfo> orderProduct(Long productId, String options, String optionCount, String email) {
        // 고객 정보 (이름, 전화번호)
        User user = userRepository.findByEmail(email).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUNT, "사용자를 찾을 수 없습니다."));

        // 주문 작품 정보 (작가 이름, 상품 이름, 상품 사진, 옵션, 수량, 전체가격, 배송비)
        Product product = productRepository.findById(productId).orElseThrow(() -> new AppException(ErrorCode.BAD_REQUEST, "주문하려는 상품이 없습니다."));


        // List<List<String>>
        // 옵션 : 디테일 옵션
        // 수량 : n개
        // options 0, 0 (선택된 디테일 옵션) -> 상품의 옵션 개수만큼 for문을 돌아서 optionInfo에 정보 넣기
        // optionCount 1 (수량)

        List<List<String>> optionInfo = new ArrayList<>();
        List<Float> optionPrice = new ArrayList<>();

        String[] count = optionCount.split(",");
        List<Integer> optionCountList = new ArrayList<>();

        for (String c : count) {
            optionCountList.add(Integer.parseInt(c));
        }

        // 옵션 개수 optionSize
        int optionSize = product.getProductOptionList().size(); // 2개
        String[] part = options.split(","); //
        /*List<List<Integer>> optionList = new ArrayList<>();// [[1,2], [2,3]].. n개

        for (int i = 0; i < part.length;) { // optionCount만큼 n개씩
            List<Integer> pair = new ArrayList<>();
            for (int j = 0; j < optionSize; j++) {
                pair.add(Integer.parseInt(part[i]));
                i++;
            }
            optionList.add(pair);
        }*/
        ProductCheck productCheck = new ProductCheck();
        List<List<Integer>> optionList = productCheck.getOption(optionSize, part);// [[1,2], [2,3]].. n개

        List<ProductOption> productOptionList = product.getProductOptionList();
        float totalProductPrice = 0;
        for (int i = 0; i < optionList.size(); i++) { // 3개
            float price = product.getRatingSum();   // 옵션 총 가격
            List<String> optionString = new ArrayList<>();

            for(int j = 0; j < optionSize; j++) {    //2개
                Integer index = optionList.get(i).get(j);
                optionString.add(productOptionList.get(j).getOptionsName()+" : "+productOptionList.get(j).getProductDetailOptionList().get(index).getOptionValue());
                price += productOptionList.get(j).getProductDetailOptionList().get(index).getPrice();
            }

            optionInfo.add(optionString);
            float tmpPrice = price * optionCountList.get(i);
            optionPrice.add(tmpPrice);
            totalProductPrice += tmpPrice;
        }

        float deliveryCharge = product.getDeliveryCharge();

        if(product.getFreeDeliverTotalCharge() != -1) { // 무료 배송이 있을 경우
            if (totalProductPrice >= product.getFreeDeliverTotalCharge()) {
                deliveryCharge = 0;
            }
        }

        // 배송 정보 (배송지)
        // 수령인
        // 배송지명
        // 전화번호
        // 우편번호
        // 주소
        boolean checkDelivery = false;

        List<AddrInfo> deliveryAddr = new ArrayList<>();
        if(user.getAddressList().size() > 0){ // 배송지가 있을 경우
            checkDelivery = true;

            Address address = user.getAddressList().get(0);

            deliveryAddr.add(new AddrInfo(address.getCity() + address.getStreet(), address.getZipcode(), address.getAddrName(), address.getRecipient(), address.getPoneNum()));
        }

        float totalPrice = totalProductPrice + deliveryCharge;

        // 포인트 게산하기
        float pointPercentage = 0.001f;
        float productSum = totalPrice * pointPercentage;
        productSum = Math.round(productSum * 100.0f) / 100.0f; // 반올림

        return ResponseEntity.ok().body(
                new OrderProductInfo(user.getName(), user.getPhoneNum(), checkDelivery, deliveryAddr, product.getSeller().getStoreName(), product.getName(), product.getProductImgs().get(0).getImg(), optionCountList, totalProductPrice, totalPrice, deliveryCharge, optionInfo, optionPrice, user.getPoint(), productSum));
    }

}
