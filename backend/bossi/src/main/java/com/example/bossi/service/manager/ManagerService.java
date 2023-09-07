package com.example.bossi.service.manager;

import com.example.bossi.dto.manager.WaitingUserListResponse;
import com.example.bossi.entity.WaitingList;
import com.example.bossi.entity.WaitingListStatus;
import com.example.bossi.dto.manager.EnteringStoreSaveRequest;
import com.example.bossi.exception.AppException;
import com.example.bossi.exception.ErrorCode;
import com.example.bossi.repository.manager.WaitingListRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ManagerService {

    private final WaitingListRepository waitingListRepository;

    public ResponseEntity<List<WaitingUserListResponse>> waitingUserList() {
        List<WaitingUserListResponse> waitingUserList = waitingListRepository.findWaitingListByStatus(WaitingListStatus.WAIT).stream()
                .map(w -> new WaitingUserListResponse(w.getId(), w.getEmail(), w.getSendEmail()))
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(waitingUserList);
    }

    @Transactional
    public ResponseEntity<String> saveStoreStatus(EnteringStoreSaveRequest dto) {
        WaitingList findUser = waitingListRepository.findById(dto.getId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUNT, "사용자가 존재하지않는다."));

        if(dto.isEnteringStatus()) findUser.updateUserWaitingUser(WaitingListStatus.ALLOW);
        else findUser.updateUserWaitingUser(WaitingListStatus.REFUSAL);

        return ResponseEntity.ok("입점 심사 결과 변경 완료");
    }

    @Transactional
    public void call() {
        LocalDateTime now = LocalDateTime.now();
        waitingListRepository.deleteByExpirationTimeBefore(now);
    }
}
