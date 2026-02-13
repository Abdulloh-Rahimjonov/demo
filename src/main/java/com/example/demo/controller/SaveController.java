package com.example.demo.controller;

import com.example.demo.models.dto.EidReqDTO;
import com.example.demo.models.dto.EidResDTO;
import com.example.demo.models.entity.Mail;
import com.example.demo.models.repo.MailRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
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
        Optional<Mail> byId = mailRepository.findById(dto.readerId());
        if (byId.isPresent()) {
            Mail mail = getMail(dto, byId.get());
            mailRepository.save(mail);
        } else {
            Mail mail = getMail(dto, new Mail());
            mail.setId(dto.readerId());
            mailRepository.save(mail);
        }
        return ResponseEntity.ok().build();
    }

    private static @NonNull Mail getMail(EidReqDTO dto, Mail mail) {
        mail.setEid(dto.eid());
        mail.setImei(dto.imei());
        mail.setImei2(dto.imei2());
        mail.setCapacity(dto.capacity());
        mail.setCycle(dto.cycle());
        mail.setAvailable(dto.available());
        mail.setModel(dto.model());
        mail.setFirst_use(dto.first_use());
        mail.setMemory_capacity(dto.memory_capacity());
        mail.setModel_name(dto.model_name());
        mail.setSerial(dto.serial());
        return mail;
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getMail(@PathVariable String id) {
        Optional<Mail> byId = mailRepository.findById(id);

        if (byId.isPresent()) {
            Mail mail = byId.get();
            return ResponseEntity.ok(new EidResDTO(mail.getId(), mail.getEid(),
                    mail.getImei(), mail.getImei2(), mail.getCapacity(),
                    mail.getFirst_use(), mail.getCycle(), mail.getModel(),
                    mail.getSerial(), mail.getModel_name(), mail.getMemory_capacity(),
                    mail.getAvailable()));
        } else {
            Mail mail = new Mail();
            mail.setId(id);
            mailRepository.save(mail);
        }
        return ResponseEntity.ok().build();
    }
}
