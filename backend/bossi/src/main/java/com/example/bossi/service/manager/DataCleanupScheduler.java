package com.example.bossi.service.manager;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DataCleanupScheduler {
    private final ManagerService managerService;

    @Scheduled(cron = "0 0 0 * * *") // 매일 12시에 실행됨.
    public void cleanupExpireDate() {
        managerService.call();
    }
}

