package com.example.bossi.controller.user;

import com.example.bossi.entity.dto.EnteringStoreRequest;
import com.example.bossi.entity.dto.UserJoinRequest;
import com.example.bossi.entity.dto.UserLoginRequest;
import com.example.bossi.response.user.CheckPhoneResponseDto;
import com.example.bossi.response.user.FindIdPwResponseDto;
import com.example.bossi.service.user.MessageService;
import com.example.bossi.service.user.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "UserController", description = "인증 관련 api 입니다.")
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private final MessageService messageService;

    @Operation(summary = "회원가입 메서드", description = "회원가입 메서드입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful join"),
            @ApiResponse(responseCode = "400", description = "bad request"),
            @ApiResponse(responseCode = "409", description = "duplicate ID"),
            @ApiResponse(responseCode = "500", description = "server error"),
    })
    @PostMapping("/join")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserJoinRequest dto){
        log.info("registerUser(): "+ dto.getCheckSMS());

        return userService.join(dto);
    }

    /**
     * 회원가입 시 진행하는 전화번호 중복 체크
     * */
    @Operation(summary = "회원가입 전화번호 중복 체크 메서드", description = "회원가입 전화번호 중복 체크 메서드입니다.",
            parameters = {
                    @Parameter(
                            name = "phoneNum",
                            description = "조회할 전화번호",
                            in = ParameterIn.PATH
                    )
            })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "전화번호 인증코드 발급"),
            @ApiResponse(responseCode = "400", description = "bad request"),
    })
   @GetMapping("/checkPhone/{phoneNum}")
    public ResponseEntity<CheckPhoneResponseDto> checkPhoneNum(@PathVariable String phoneNum){
        log.info("checkPhoneNum(): " + phoneNum);

        return messageService.checkPhoneNum(phoneNum);
   }

    /**
     * 회원가입 시 아이디의 중복 체크 진행
     * */
   @Operation(summary = "아이디 중복 체크 메서드", description = "아이디 중복 체크 메서드입니다.")
   @ApiResponses(value = {
           @ApiResponse(responseCode = "200", description = "중복 체크 성공"),
           @ApiResponse(responseCode = "400", description = "bad request"),
   })
   @ApiImplicitParam(name = "email", value = "유저 아이디")
   @GetMapping("/find/email/{email}")
    public Boolean findIdByEmail(@PathVariable String email){
        log.info(email+"======");
        return userService.checkId(email);
   }

   @Operation(summary = "아이디/ 비밀번호 찾는 메서드", description = "전화번호로 아이디와 비밀번호를 찾는 메서드입니다.")
   @ApiResponses(value =  {
           @ApiResponse(responseCode = "201", description = "찾기 성공"),
           @ApiResponse(responseCode = "400", description = "bad request")
   })
   @ApiImplicitParam(name = "phoneNum", value = "전화번호")
   @GetMapping("/find/phone/{phoneNum}")
   public ResponseEntity<FindIdPwResponseDto> findIdPwByPhone(@PathVariable String phoneNum){
        log.info("전화번호로 아이디/ 비밀번호 찾기");

        return userService.findIdPwByPhone(phoneNum);
   }

   @Operation(summary = "비밀번호 변경하는 메서드", description = "비밀번호를 변경하는 메서드 입니다.")
   @ApiResponses(value = {
           @ApiResponse(responseCode = "200", description = "비밀번호 변경 성공"),
           @ApiResponse(responseCode = "400", description = "bad request")
   })
   @PostMapping("/phone/changePw")
    public ResponseEntity<String> changePw(@Valid @RequestBody UserLoginRequest dto){
        log.info("비밀번호 변경");
        return userService.changePw(dto);
   }

   @Operation(summary = "상점 입점 가입 신청하는 메서드", description = "상점 입점 가입을 신청하는 메서드 입니다.")
   @PostMapping("/enteringStore")
    public ResponseEntity<String> enteringStore(@Valid @RequestBody EnteringStoreRequest dto){
        log.info("상점 입점 문의");
        return userService.enteringStore(dto);
   }
}
