package com.example.demo.models.repo;

import com.example.demo.models.entity.RequestLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

public interface RequestLogRepository extends JpaRepository<RequestLog, Long> {

    @Transactional
    void deleteByCreatedAtBefore(LocalDateTime time);
}