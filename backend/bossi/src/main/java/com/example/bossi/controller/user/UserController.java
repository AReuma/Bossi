package com.example.bossi.controller.user;

import com.example.bossi.entity.dto.UserJoinRequest;
import com.example.bossi.response.user.CheckPhoneResponse;
import com.example.bossi.service.user.MessageService;
import com.example.bossi.service.user.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "인증", description = "인증 관련 api 입니다.")
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private final MessageService messageService;

    @Operation(summary = "회원가입 메서드", description = "회원가입 메서드입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful join"),
            @ApiResponse(responseCode = "100", description = "id duplicated"),
            @ApiResponse(responseCode = "400", description = "bad request"),
            @ApiResponse(responseCode = "500", description = "server error"),
            @ApiResponse(responseCode = "409", description = "Validation failed"),
    })
    @PostMapping("/join")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserJoinRequest dto){
        log.info("registerUser(): "+ dto.getCheckSMS());

        return userService.join(dto);

    }

    @Operation(summary = "회원가입 전화번호 중복 체크 메서드", description = "회원가입 전화번호 중복 체크 메서드입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "전화번호 인증코드 발급"),
            @ApiResponse(responseCode = "400", description = "전화번호 형식이 다름"),
    })
    @ApiImplicitParam(name = "phoneNum", value = "전화번호")
   @GetMapping("/checkPhone/{phoneNum}")
    public ResponseEntity<?> checkPhoneNum(@PathVariable String phoneNum){
        log.info("checkPhoneNum(): " + phoneNum);

        return messageService.checkPhoneNum(phoneNum);
   }

   @Operation(summary = "아이디 중복 체크 메서드", description = "아이디 중복 체크 메서드입니다.")
   @ApiResponses(value = {
           @ApiResponse(responseCode = "200", description = ""),
           @ApiResponse(responseCode = "400", description = ""),
   })
   @ApiImplicitParam(name = "email", value = "유저 아이디")
   @GetMapping("/checkId/{email}")
    public Boolean checkId(@PathVariable String email){
        log.info(email+"======");
        return userService.checkId(email);
   }
}
