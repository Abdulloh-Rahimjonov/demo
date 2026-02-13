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

    private String capacity;
    private String first_use;
    private String cycle;
    private String model;
    private String serial;
    private String model_name;
    private String memory_capacity;
    private String available;
}
