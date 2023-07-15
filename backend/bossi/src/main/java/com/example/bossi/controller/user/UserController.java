package com.example.bossi.controller.user;

import com.example.bossi.entity.dto.UserJoinRequest;
import com.example.bossi.entity.dto.UserLoginRequest;
import com.example.bossi.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<String> registerUser(@RequestBody UserJoinRequest dto){
        log.info("registerUser(): "+ dto.getCheckSMS());

        userService.join(dto);
        return ResponseEntity.ok().body("회원가입 성공");
    }

   /* @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody UserLoginRequest dto){
        log.info("login 시작");
        String token = userService.login(dto.getEmail(), dto.getPassword());

        return ResponseEntity.ok().body(token);
    }*/
}
