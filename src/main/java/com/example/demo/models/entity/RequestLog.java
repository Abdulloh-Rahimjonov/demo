package com.example.demo.models.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "request_log")
public class RequestLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 10)
    private String action; // SAVE / GET

    @Column(nullable = false, length = 200)
    private String path;

    @Column(length = 100)
    private String readerId;

    @Column(columnDefinition = "text")
    private String requestBody;

    @Column
    private Integer responseStatus;

    @Column(columnDefinition = "text")
    private String responseBody;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }
}