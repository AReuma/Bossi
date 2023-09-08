package com.example.bossi.controller.manager;

import com.example.bossi.dto.manager.WaitingUserListResponse;
import com.example.bossi.entity.WaitingList;
import com.example.bossi.dto.manager.EnteringStoreSaveRequest;
import com.example.bossi.service.manager.ManagerService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "ManagerController", description = "권한 관리 관련 api 입니다.")
@RequestMapping("/api/v1/manager")
public class ManagerController {

    private final ManagerService managerService;

    @GetMapping("/waitingList")
    public ResponseEntity<List<WaitingUserListResponse>> waitingUserList(){
        log.info("입점 대기자 라스트 출력");

        return managerService.waitingUserList();
    }

    @PostMapping("/store/status")
    public ResponseEntity<String> saveStoreStatus(@RequestBody EnteringStoreSaveRequest dto){
        log.info("입점 대기 결과 저장");

        return managerService.saveStoreStatus(dto);
    }
}
