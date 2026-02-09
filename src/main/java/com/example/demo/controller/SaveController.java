package com.example.demo.controller;

import com.example.demo.models.dto.EidReqDTO;
import com.example.demo.models.dto.EidResDTO;
import com.example.demo.models.entity.Mail;
import com.example.demo.models.repo.MailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SaveController {

    private final MailRepository mailRepository;

    @PostMapping("/save")
    public ResponseEntity<?> saveImei(@RequestBody EidReqDTO dto) {
        Optional<Mail> byId = mailRepository.findById(dto.id());
        if (byId.isPresent()) {
            Mail mail = byId.get();
            mail.setEid(dto.eid());
            mail.setImei(dto.imei());
            mail.setImei2(dto.imei2());
            mailRepository.save(mail);
        } else {
            Mail mail = new Mail();
            mail.setId(dto.id());
            mail.setEid(dto.eid());
            mail.setImei(dto.imei());
            mail.setImei2(dto.imei2());
            mailRepository.save(mail);
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getMail(@PathVariable String id) {
        Optional<Mail> byId = mailRepository.findById(id);

        if (byId.isPresent()) {
            Mail mail = byId.get();
            return ResponseEntity.ok(new EidResDTO(mail.getId(), mail.getEid(), mail.getImei(), mail.getImei2()));
        } else {
            Mail mail = new Mail();
            mail.setId(id);
            mailRepository.save(mail);
        }
        return ResponseEntity.ok().build();
    }
}
