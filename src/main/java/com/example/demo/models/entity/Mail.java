package com.example.demo.models.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@ToString
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Mail {

    @Id
    private String id;

    private String eid;

    private String imei;
    private String imei2;
}
