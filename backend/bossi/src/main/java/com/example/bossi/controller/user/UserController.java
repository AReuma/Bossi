package com.example.bossi.controller.user;

import com.example.bossi.entity.dto.UserJoinRequest;
import com.example.bossi.response.user.CheckPhoneResponse;
import com.example.bossi.service.user.MessageService;
import com.example.bossi.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private final MessageService messageService;

    @PostMapping("/join")
    public ResponseEntity<String> registerUser(@RequestBody UserJoinRequest dto){
        log.info("registerUser(): "+ dto.getCheckSMS());

        userService.join(dto);
        return ResponseEntity.ok().body("회원가입 성공");

    }

   @GetMapping("/checkPhone/{phoneNum}")
    public CheckPhoneResponse checkPhoneNum(@PathVariable String phoneNum){
        log.info("checkPhoneNum(): " + phoneNum);

        return messageService.checkPhoneNum(phoneNum);
   }

   @GetMapping("/checkId/{email}")
    public Boolean checkId(@PathVariable String email){
        log.info(email+"======");
        return userService.checkId(email);
   }
}
