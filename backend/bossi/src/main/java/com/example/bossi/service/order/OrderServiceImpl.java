package com.example.bossi.service.order;

import com.example.bossi.dto.order.CompleteOrderMultiProductRequest;
import com.example.bossi.dto.order.CompleteOrderRequest;
import com.example.bossi.dto.order.RedisOrderProductInfo;
import com.example.bossi.entity.*;
import com.example.bossi.entity.product.Product;
import com.example.bossi.entity.product.ProductDetailOption;
import com.example.bossi.exception.AppException;
import com.example.bossi.exception.ErrorCode;
import com.example.bossi.repository.order.AddressRepository;
import com.example.bossi.repository.order.OrderProductDetailOptionRepository;
import com.example.bossi.repository.order.OrderRepository;
import com.example.bossi.repository.seller.ProductRepository;
import com.example.bossi.repository.user.UserRepository;
import com.example.bossi.response.order.OrderProductInfo;
import com.example.bossi.response.order.OrderProductInfoResponse;
import com.example.bossi.service.product.ProductCheck;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.bossi.entity.DeliveryStatus.READY_PRODUCT;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final OrderProductDetailOptionRepository orderProductDetailOptionRepository;
    private final RedisOrderService redisOrderService;

    @Override
    @Transactional
    public String orderComplete(CompleteOrderRequest dto) {
        // 주문 저장 - 상점이 1개일 경우 -> 상점별로 저장

        // 주문자
        User user = userRepository.findByEmail(dto.getEmail()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUNT, "주문자의 아이디를 찾을 수 없음."));

        // 포인트 차감
        user.subPoint(dto.getUsePoint());

        if(dto.getIsBasic() && dto.getIsSave()){
            Optional<Address> findBasicAddr = addressRepository.findAddressByUserEmailAndBasic(dto.getEmail(), true);
            findBasicAddr.ifPresent(address -> address.changeBasicAddress(false));

            Address address = Address.createAddress(dto.getAddress(), dto.getDetailAddr(), dto.getZipcode(), dto.getDeliveryName(), dto.getReceiver(), dto.getPhoneNum(), true, user);
            addressRepository.save(address);
        }else if(!dto.getIsBasic() && dto.getIsSave()){
            Address address = Address.createAddress(dto.getAddress(), dto.getDetailAddr(), dto.getZipcode(), dto.getDeliveryName(), dto.getReceiver(), dto.getPhoneNum(), false, user);
            addressRepository.save(address);
        }

        // 배송정보 저장 - Delivery
        // address와 order를 OneToOne으로 연관관계를 안맺은 이유는 고객이 주소를 변경할때 DB에 저장된 정보를 변경하지 않기 위해서
        Delivery delivery;

        if(dto.getAddress().equals("")){
            Address address = addressRepository.findAddressByUserEmailAndBasic(dto.getEmail(), true).orElseThrow(() -> new AppException(ErrorCode.HAVE_NOT_DELIVERY, "주소지가 없음."));
            delivery = Delivery.builder()
                    .status(READY_PRODUCT)
                    .city(address.getCity())
                    .street(address.getStreet())
                    .zipcode(address.getZipcode())
                    .recipient(address.getRecipient())
                    .phoneNum(address.getPhoneNum())
                    .build();
        }else {
            delivery = Delivery.builder()
                    .status(READY_PRODUCT)
                    .city(dto.getAddress())
                    .street(dto.getDetailAddr())
                    .zipcode(dto.getZipcode())
                    .recipient(dto.getReceiver())
                    .phoneNum(dto.getPhoneNum())
                    .build();
        }

        log.info("deliver: {}", delivery.getRecipient());

        // 주문 생성 - OrderProduct
        Product product = productRepository.findById(dto.getProductId()).orElseThrow(() ->  new AppException(ErrorCode.BAD_REQUEST, "주문한 상품이 존재하지않음."));

        // createOrderProduct(product, orderPrice, count)
        // 옵션 개수 optionSize
        int optionSize = product.getProductOptionList().size();
        String[] part = dto.getOptions().split(",");

        ProductCheck productCheck = new ProductCheck();
        List<List<Integer>> optionList = productCheck.getOption(optionSize, part);// [[1,2], [2,3]]... n개

        log.info("============");
        log.info("optionList.size: {}", optionList.size());
        log.info("============");

        float productOrderTotalPrice = 0;
        // OrderProduct 생성
        List<OrderProduct> orderProductList = new ArrayList<>();
        for (int i = 0; i < optionList.size(); i++) { // [0,0] , [1,0] // i는 0 ~ 1
            // 상품 가격 + 옵션
            float optionPrice = product.getRatingSum();
            for(int j = 0; j < optionList.get(i).size(); j++){
                Integer n = optionList.get(i).get(j);
                log.info("j, n : {}, {}, {}", j, n, product.getProductOptionList().get(j).getProductDetailOptionList().get(n).getPrice());
                optionPrice += product.getProductOptionList().get(j).getProductDetailOptionList().get(n).getPrice();
                productOrderTotalPrice += optionPrice;
            }

            OrderProduct orderProduct = OrderProduct.createOrderProduct(product, optionPrice, Integer.parseInt(part[i]), Float.parseFloat(dto.getDeliveryPrice()));
            orderProductList.add(orderProduct);
        }

        // productDetailOption 찾기
        List<OrderProductDetailOption> productDetailOptionList = new ArrayList<>();
        for(int i = 0; i < optionList.size(); i++) { // 0 ~ 1 2개 // [[1,2], [2,3]]... n개
            //createDetailOption(String optionValue, Float price, ProductOption productOption)
            // 주문 생성 - OrderProductDetailOption
            for(int j = 0; j < optionList.get(i).size(); j++){
                Integer n = optionList.get(i).get(j);
                ProductDetailOption productDetailOption = product.getProductOptionList().get(j).getProductDetailOptionList().get(n);
                productDetailOptionList.add(OrderProductDetailOption.createOrderProductDetailOption(productDetailOption, orderProductList.get(i)));
            }
        }

        for (OrderProductDetailOption orderProductDetailOption : productDetailOptionList) {
            orderProductDetailOption.getOrderProduct().addOrderProductDetailOption(orderProductDetailOption);
        }

        // productDetailOption 찾은 후 저장하기

        // productDetailOption 저장
        orderProductDetailOptionRepository.saveAll(productDetailOptionList);

        // 주문 생성 - Order
        /*List<Order> orderList = new ArrayList<>();
        for (OrderProductDetailOption orderProductDetailOption : productDetailOptionList) {

            orderList.add(Order.createOrder(user, delivery, orderProductList, Long.parseLong(dto.getOrderNum())));
        }*/
        productOrderTotalPrice -= dto.getUsePoint();
        Order order = Order.createOrder(user, delivery, orderProductList, dto.getOrderNum(), Float.parseFloat(dto.getTotalPrice()), dto.getUsePoint(), Float.parseFloat(dto.getDeliveryPrice()));
        orderRepository.save(order);

        // 저장 후 redis에 저장 번호 저장
        redisOrderService.saveOrderNum(dto.getOrderNum());

        return dto.getOrderNum();
    }

    @Override
    public ResponseEntity<OrderProductInfoResponse> showOrderComplete(String orderNum) {
        //String orderNum, String address, String detailAddr, String zipcode, String recipient, String poneNum, Integer point, Float price, Integer usePoint
        Order findOrder = orderRepository.findByOrderNum(orderNum);
        Delivery delivery = findOrder.getDelivery();

        Integer readyPoint = 10;

        //======
        //List<String> optionList = new ArrayList<>();
        //List<Float> totalPrice = new ArrayList<>();
        //=====
        float totalPrice = 0;
        List<OrderProductInfo> orderProductInfoList = new ArrayList<>();
        List<OrderProduct> orderProducts = findOrder.getOrderProducts();

        for(OrderProduct orderProduct : orderProducts){ // 주문한 상품 찾기
            Long productId = orderProduct.getProduct().getId();

            List<String> optionList = new ArrayList<>();
            List<Float> totalPriceList = new ArrayList<>();

            List<OrderProductDetailOption> orderProductDetailOptions = orderProduct.getOrderProductDetailOptions();
            StringBuilder optionStr = new StringBuilder();
            log.info("orderProductDetailOptions.size: {}", orderProductDetailOptions.size());

            //for (OrderProductDetailOption orderProductDetailOption : orderProductDetailOptions) {   // 상품의 디테일 옵션
            for(int i = 0; i < orderProductDetailOptions.size(); i++){
                ProductDetailOption productDetailOption = orderProductDetailOptions.get(i).getProductDetailOption();
                log.info("productId: {}, {}", productId, i);
                log.info("check productId: {}", orderProduct.getProduct().getId());
                log.info("check option: {}", productDetailOption.getOptionValue());

                optionStr.append(productDetailOption.getOptionValue()).append("/");
                //totalPriceList.add(orderProductDetailOption.getOrderProduct().getOrderPrice());

                //totalPrice += orderProductDetailOptions.get(i).getOrderProduct().getOrderPrice();
                //log.info("{}: 1. !!!!!!!totalPrice: {}",i,  orderProductDetailOptions.get(i).getOrderProduct().getOrderPrice());
            }

            //totalPrice += orderProduct.getOrderProduct();
            totalPriceList.add(orderProduct.getOrderPrice());
            optionList.add(optionStr.toString());

            //log.info("2. !!!!!!!totalPrice: {}", totalPrice);

            boolean existingProduct = false;

            for (OrderProductInfo existingInfo : orderProductInfoList) {
                if (existingInfo.getProductId().equals(productId)) {    // productId와 일치한지 확인
                    existingInfo.getOptionList().addAll(optionList);
                    existingInfo.getTotalPrice().addAll(totalPriceList);
                    log.info("productId: {}", productId);
                    log.info("totalPriceList: {}", totalPriceList);

                    /*for (Float price : totalPriceList) {
                        totalPrice += price;
                    }
                    existingInfo.setTotalProductPrice(totalPrice);*/
                    existingProduct = true;
                    break;
                }
            }

            /*if(totalPrice < orderProduct.getProduct().getFreeDeliverTotalCharge()){
                totalPrice += orderProduct.getProduct().getDeliveryCharge();
                log.info("3. !!!!!!!totalPrice: {}", totalPrice);
            }*/

            if (!existingProduct) {
                orderProductInfoList.add(
                        new OrderProductInfo(productId, orderProduct.getProduct().getSeller().getStoreName(), orderProduct.getProduct().getName(), orderProduct.getProduct().getProductImgs().get(0).getImg(), optionList, totalPriceList, totalPrice, orderProduct.getProduct().getDeliveryCharge()));
            }

        }

        for (OrderProductInfo existingInfo : orderProductInfoList) {
            List<Float> totalPriceList = existingInfo.getTotalPrice();
            Float totalProductPrice = 0f;

            for (Float price : totalPriceList) {
                totalProductPrice += price;
            }

            if(totalPrice < existingInfo.getDeliveryCharge()){
                totalPrice += existingInfo.getDeliveryCharge();
            }else {
                existingInfo.setDeliveryCharge(0f);
            }

            existingInfo.setTotalProductPrice(totalProductPrice);
        }

        // 가격
        // 주문한 상품

        //(String orderNum, String address, String detailAddr, String zipcode, String recipient, String phoneNum, Integer point, Float price, Float deliveryPrice, Integer usePoint, List<OrderProductInfo> orderProductInfoList)
        return ResponseEntity.ok().body(
                new OrderProductInfoResponse(orderNum, delivery.getCity(), delivery.getStreet(), delivery.getZipcode(), delivery.getRecipient(), delivery.getPhoneNum(), readyPoint, findOrder.getTotalPrice(), findOrder.getTotalDeliveryPrice(), findOrder.getUsePoint(), orderProductInfoList));
    }

    @Override
    @Transactional
    public String multiOrderComplete(CompleteOrderMultiProductRequest dto) throws JsonProcessingException {
        // 주문 n개 저장.

        // 주문자 찾기
        User user = userRepository.findByEmail(dto.getEmail()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUNT, "주문자를 찾을 수 없음."));
        user.subPoint(dto.getUsePoint());

        // address 저장 여부 체크
        if(dto.getIsBasic() && dto.getIsSave()){
            Optional<Address> findBasicAddr = addressRepository.findAddressByUserEmailAndBasic(dto.getEmail(), true);
            findBasicAddr.ifPresent(address -> address.changeBasicAddress(false));

            Address address = Address.createAddress(dto.getAddress(), dto.getDetailAddr(), dto.getZipcode(), dto.getDeliveryName(), dto.getReceiver(), dto.getPhoneNum(), true, user);
            addressRepository.save(address);
        }else if(!dto.getIsBasic() && dto.getIsSave()){
            Address address = Address.createAddress(dto.getAddress(), dto.getDetailAddr(), dto.getZipcode(), dto.getDeliveryName(), dto.getReceiver(), dto.getPhoneNum(), false, user);
            addressRepository.save(address);
        }

        // Delivery 저장
        // address와 order를 OneToOne으로 연관관계를 안맺은 이유는 고객이 주소를 변경할때 DB에 저장된 정보를 변경하지 않기 위해서
        Delivery delivery;

        if(dto.getAddress().equals("")){
            Address address = addressRepository.findAddressByUserEmailAndBasic(dto.getEmail(), true).orElseThrow(() -> new AppException(ErrorCode.HAVE_NOT_DELIVERY, "주소지가 없음."));
            delivery = Delivery.builder()
                    .status(READY_PRODUCT)
                    .city(address.getCity())
                    .street(address.getStreet())
                    .zipcode(address.getZipcode())
                    .recipient(address.getRecipient())
                    .phoneNum(address.getPhoneNum())
                    .build();
        }else {
            delivery = Delivery.builder()
                    .status(READY_PRODUCT)
                    .city(dto.getAddress())
                    .street(dto.getDetailAddr())
                    .zipcode(dto.getZipcode())
                    .recipient(dto.getReceiver())
                    .phoneNum(dto.getPhoneNum())
                    .build();
        }

        // OrderProduct
        String[] part = dto.getSelectProductId().split(",");
        List<Long> productIdList = new ArrayList<>();
        for (String s : part) {
            productIdList.add(Long.parseLong(s));
        }

        List<RedisOrderProductInfo> redisOrderProductInfos = redisOrderService.orderProductInfo(productIdList, dto.getEmail());
        /*
            {
                productId: 1,
                productOption: [[1,2], [2,3]],
                productCount: [1,2]
            },
        */


        List<OrderProduct> totalOrderProductList = new ArrayList<>();
        //[{"productId":1,"productOption":[[0,0]],"productCount":[2]},{"productId":5,"productOption":[[0,0],[1,0]],"productCount":[2,1]}]
        for(int i = 0; i < redisOrderProductInfos.size(); i++){ // 저장된 상품의 개수만큼 반복
            RedisOrderProductInfo redisOrderProductInfo = redisOrderProductInfos.get(i);

            Product product = productRepository.findById(redisOrderProductInfo.getProductId()).orElseThrow(() -> new AppException(ErrorCode.BAD_REQUEST, "주문한 상품이 존재하지않음."));

            // OrderProduct 생성
            List<OrderProduct> orderProductList = new ArrayList<>();

            for(int j = 0; j < redisOrderProductInfo.getProductOption().size(); j++){ //  저장된 상품의 옵션 개수만큼 반복

                float optionPrice = product.getRatingSum();

                for(int k = 0; k < redisOrderProductInfo.getProductOption().get(j).size(); k++){    // 1개의 상품에 저장된 옵션 개수 반복
                    Integer n = redisOrderProductInfo.getProductOption().get(j).get(k);
                    optionPrice += product.getProductOptionList().get(k).getProductDetailOptionList().get(n).getPrice();
                }

                float deliveryPrice = product.getDeliveryCharge();
                if(optionPrice >= product.getFreeDeliverTotalCharge()){
                    deliveryPrice = 0;
                }

                OrderProduct orderProduct = OrderProduct.createOrderProduct(product, optionPrice, redisOrderProductInfo.getProductCount().get(j), deliveryPrice);
                orderProductList.add(orderProduct);
                totalOrderProductList.add(orderProduct);
            }


            //ProductDetailOption 찾기
            List<OrderProductDetailOption> productDetailOptionList = new ArrayList<>();

            for(int j = 0; j < redisOrderProductInfo.getProductOption().size(); j++) { //  저장된 상품의 옵션 개수만큼 반복

                for(int k = 0; k < redisOrderProductInfo.getProductOption().get(j).size(); k++) {    // 1개의 상품에 저장된 옵션 개수 반복
                    Integer n = redisOrderProductInfo.getProductOption().get(j).get(k);
                    ProductDetailOption productDetailOption = product.getProductOptionList().get(k).getProductDetailOptionList().get(n);
                    productDetailOptionList.add(OrderProductDetailOption.createOrderProductDetailOption(productDetailOption, orderProductList.get(j)));
                }
            }

            // productDetailOption 찾은 후 저장하기
            for(OrderProductDetailOption orderProductDetailOption : productDetailOptionList){
                orderProductDetailOption.getOrderProduct().addOrderProductDetailOption(orderProductDetailOption);
            }

            orderProductDetailOptionRepository.saveAll(productDetailOptionList);
        }

        // Order
        Order order = Order.createOrder(user, delivery, totalOrderProductList, dto.getOrderNum(), Float.parseFloat(dto.getTotalPrice()), dto.getUsePoint(), Float.parseFloat(dto.getTotalDeliveryPrice()));
        orderRepository.save(order);

        //  redis에 저장된 상품 삭제
        redisOrderService.removerProduct(productIdList, dto.getEmail());
        return dto.getOrderNum();
    }

    public void redisOrderProduct(){

    }
}
