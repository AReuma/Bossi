package com.example.bossi.controller.user;

import com.example.bossi.request.UserRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @PostMapping("/register")
    public void registerUser(@RequestBody UserRequest userRequest){
        log.info("registerUser(): "+ userRequest.getNickName());
    }
}
