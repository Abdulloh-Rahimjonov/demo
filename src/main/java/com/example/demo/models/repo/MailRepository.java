package com.example.demo.models.repo;

import com.example.demo.models.entity.Mail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MailRepository extends JpaRepository<Mail, String> {
}