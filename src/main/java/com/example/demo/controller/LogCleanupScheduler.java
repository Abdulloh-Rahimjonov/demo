package com.example.demo.controller;

import com.example.demo.models.repo.RequestLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class LogCleanupScheduler {

    private final RequestLogRepository repo;

    @Scheduled(cron = "0 0 */4 * * *")
    public void cleanLogs() {
        LocalDateTime fiveDaysAgo = LocalDateTime.now().minusDays(5);
        repo.deleteByCreatedAtBefore(fiveDaysAgo);
    }
}