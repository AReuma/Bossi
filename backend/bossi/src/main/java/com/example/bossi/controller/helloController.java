package com.example.bossi.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/hello")
@Tag(name = "hello 컨트롤러", description = "spring boot 세팅 확인때 사용한 컨트롤러")
public class helloController {

    @GetMapping("/")
    public void Hello(){
        log.info("할루!");
    }
}
