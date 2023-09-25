package com.example.bossi.service.seller;

import com.example.bossi.dto.FileDetail;
import com.example.bossi.dto.seller.CreateContentRequest;
import com.example.bossi.dto.seller.JoinSellerRequest;
import com.example.bossi.entity.Seller;
import com.example.bossi.entity.WaitingList;
import com.example.bossi.entity.WaitingListStatus;
import com.example.bossi.entity.product.*;
import com.example.bossi.exception.AppException;
import com.example.bossi.exception.ErrorCode;
import com.example.bossi.repository.manager.WaitingListRepository;
import com.example.bossi.repository.seller.*;
import com.example.bossi.response.product.CategoryResponse;
import com.example.bossi.response.product.FileUploadResponse;
import com.example.bossi.service.aws.AwsS3UploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SellerServiceImpl implements SellerService{

    private final ProductRepository productRepository;
    private final SellerRepository sellerRepository;
    private final WaitingListRepository waitingListRepository;
    private final ProductContentRepository productContentRepository;
    private final CategoryRepository categoryRepository;
    private final AwsS3UploadService uploadService;
    private final ProductImgRepository productImgRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     *  판매 상품 등록
     * */
    @Transactional
    public ResponseEntity<Long> createContent(CreateContentRequest request) {

        // 판매자 찾기
        Seller seller = sellerRepository.findById(Long.parseLong(request.getSellerId())).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUNT, "판매자가 없음"));

        // 옵션 value 값만 가지고 오기
        List<String> values = new ArrayList<>();
        for(Map<String, String> map : request.getOptions()){
            String value = map.get("value");
            values.add(value);
        }

        // content
        ProductContent productContent = ProductContent.builder()
                .content(request.getContent())
                .build();

        // 카테고리 찾기
        Category category = categoryRepository.findById(Long.parseLong(request.getCategory())).orElseThrow(() -> new AppException(ErrorCode.BAD_REQUEST, "존재하지 않는 카테고리"));
        //Category category = categoryRepository.findById(1L).orElseThrow(() ->  new AppException(ErrorCode.BAD_REQUEST, "x"));

        //product
        Product product = Product.createProduct(seller, category, productContent, request.getTitle(), request.getPrice(), request.getStockQuantity(), request.getRating(), request.getRatingPrice(), request.getDeliveryCount(), request.getFreeCount());

        // Content 이미지 저장
        if(!request.getImgUrlLists().isEmpty()){
            for (String imgUrl : request.getImgUrlLists()) {
                ProductContentImg.createProductContentImg(product, imgUrl);
            }
        }

        // 옵션 생성하기
        List<ProductOption> productOptionList = new ArrayList<>();
        for (String s : values) {
            ProductOption productOption =
                    ProductOption.createProductOption(s, product);

            productOptionList.add(productOption);
        }

        // OptionDetail
        for(int i = 0; i < request.getDetailOption().size(); i++){
            for(int j = 0; j < request.getDetailOption().get(i).size(); j++){
                String value = request.getDetailOption().get(i).get(j).get("value");
                Float price = Float.valueOf(request.getDetailOption().get(i).get(j).get("price"));
                ProductDetailOption.createDetailOption(value, price, productOptionList.get(i));
            }
        }


        productRepository.save(product);

        // 이미지 검사
        // getImgUrlLists() 저장했을때 남아있는 사진
        // getAllImgUrlList() 업로드된 모든 사진
        if(!request.getAllImgUrlList().isEmpty() && request.getAllImgUrlList().get(0) == null){

            for (String o : request.getImgUrlLists()) {
                request.getAllImgUrlList().remove(o);
            }

            if(!request.getAllImgUrlList().isEmpty() && request.getAllImgUrlList().get(0) == null) {
                uploadService.deleteMissingImages(request.getAllImgUrlList());
            }
        }

        return ResponseEntity.ok().body(product.getId());
    }

    @Transactional
    public FileUploadResponse save(MultipartFile multipartFile, String id) {
        FileDetail fileDetail = FileDetail.multipartOf(multipartFile);
        String imgUrl = uploadService.store(fileDetail.getPath(), multipartFile, id);
        return new FileUploadResponse(imgUrl, fileDetail.getId());
    }

    public ResponseEntity<List<CategoryResponse>> categoryList() {
        List<Category> findAll = categoryRepository.findAll();
        List<CategoryResponse> categoryList = findAll.stream()
                .map(o -> new CategoryResponse(o.getId(), o.getName()))
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(categoryList);
    }

    @Transactional
    public ResponseEntity<String> saveProductImage(List<MultipartFile> productImages, Long id) {
        // 상품 검색
        Product product = productRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.BAD_REQUEST, "등록할 상품이 없음"));

        // s3 이미지 저장
        for (MultipartFile multipartFile : productImages) {
            FileDetail fileDetail = FileDetail.multipartOf(multipartFile);
            //String imgUrl = uploadService.store(fileDetail.getPath(), multipartFile, product.getSeller().getEmail());
            String imgUrl = uploadService.store(fileDetail.getPath(), multipartFile, String.valueOf(id));

            // db에 이미지 이름 저장
            ProductImg productImg = ProductImg.saveProductImg(imgUrl, product);
            productImgRepository.save(productImg);
        }

        return ResponseEntity.ok().body("이미지 업로드 성공");
    }

    @Transactional
    public ResponseEntity<String> registerSeller(JoinSellerRequest joinSellerRequest) {

        // 승인 받은 메일 확인하기
        // findUser.updateUserWaitingUser(WaitingListStatus.REFUSAL);
        WaitingList waitingUser = waitingListRepository.findBySendEmail(joinSellerRequest.getApprovedEmail()).orElseThrow(() -> new AppException(ErrorCode.FORBIDDEN, "인증되지 않은 회원입니다."));

        // 가입 후 waitingList db에서 삭제
        waitingUser.updateUserWaitingUser(WaitingListStatus.JOIN);

        // s3 이미지 보내기
        FileDetail fileDetail = FileDetail.multipartOf(joinSellerRequest.getProfileImg());
        String imgUrl = uploadService.saveSellerImg(fileDetail.getPath(), joinSellerRequest.getProfileImg());

        // Seller 만들기
        Seller seller = Seller.createSeller(joinSellerRequest.getEmail(), passwordEncoder.encode(joinSellerRequest.getPassword()), joinSellerRequest.getStoreName(), joinSellerRequest.getStoreBio(), imgUrl, joinSellerRequest.getStoreIntroduction());

        sellerRepository.save(seller);

        return ResponseEntity.ok().body("ok");
    }
}
