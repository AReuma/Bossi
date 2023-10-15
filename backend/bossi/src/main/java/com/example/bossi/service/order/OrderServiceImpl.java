package com.example.bossi.service.order;

import com.example.bossi.dto.order.CompleteOrderRequest;
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
import com.example.bossi.response.order.OrderProductInfoResponse;
import com.example.bossi.service.product.ProductCheck;
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
    public void orderComplete(CompleteOrderRequest dto) {
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
        Delivery delivery = Delivery.builder()
                .status(READY_PRODUCT)
                .city(dto.getAddress())
                .street(dto.getDetailAddr())
                .zipcode(dto.getZipcode())
                .recipient(dto.getReceiver())
                .phoneNum(dto.getPhoneNum())
                .build();

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

            OrderProduct orderProduct = OrderProduct.createOrderProduct(product, optionPrice, Integer.parseInt(part[i]));
            orderProductList.add(orderProduct);
        }

        // productDetailOption 찾기
        List<OrderProductDetailOption> productDetailOptionList = new ArrayList<>();
        for(int i = 0; i < optionList.size(); i++) { // 0 ~ 1 2개
            //createDetailOption(String optionValue, Float price, ProductOption productOption)
            // 주문 생성 - OrderProductDetailOption
            for(int j = 0; j < optionList.get(i).size(); j++){
                Integer n = optionList.get(i).get(j);
                ProductDetailOption productDetailOption = product.getProductOptionList().get(j).getProductDetailOptionList().get(n);
                productDetailOptionList.add(OrderProductDetailOption.createOrderProductDetailOption(productDetailOption, orderProductList.get(i)));
            }
        }

        // productDetailOption 저장
        orderProductDetailOptionRepository.saveAll(productDetailOptionList);

        // 주문 생성 - Order
        /*List<Order> orderList = new ArrayList<>();
        for (OrderProductDetailOption orderProductDetailOption : productDetailOptionList) {

            orderList.add(Order.createOrder(user, delivery, orderProductList, Long.parseLong(dto.getOrderNum())));
        }*/
        productOrderTotalPrice -= dto.getUsePoint();
        Order order = Order.createOrder(user, delivery, orderProductList, dto.getOrderNum(), Float.parseFloat(dto.getTotalPrice()), dto.getUsePoint());
        orderRepository.save(order);

        // 저장 후 redis에 저장 번호 저장
        //redisOrderService.saveOrderNum(dto.getOrderNum());
    }

    @Override
    public ResponseEntity<OrderProductInfoResponse> showOrderComplete(String orderNum) {
        //String orderNum, String address, String detailAddr, String zipcode, String recipient, String poneNum, Integer point, Float price, Integer usePoint
        Order findOrder = orderRepository.findByOrderNum(orderNum);
        Delivery delivery = findOrder.getDelivery();

        Integer readyPoint = 10;
        return ResponseEntity.ok().body(new OrderProductInfoResponse(orderNum, delivery.getCity(), delivery.getStreet(), delivery.getZipcode(), delivery.getRecipient(), delivery.getPhoneNum(), readyPoint, findOrder.getTotalPrice(), findOrder.getUsePoint()));
    }
}
