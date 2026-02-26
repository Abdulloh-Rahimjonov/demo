package com.example.demo.controller;

import com.example.demo.models.dto.*;
import com.example.demo.models.entity.*;
import com.example.demo.models.repo.*;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tools.jackson.databind.ObjectMapper;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SaveController {

    private final MailRepository mailRepository;
    private final RequestLogRepository requestLogRepository;
    private final ObjectMapper objectMapper;

    @PostMapping("/save")
    public ResponseEntity<?> saveImei(@RequestBody EidReqDTO dto) {

        ResponseEntity<?> resp;

        if (dto.readerId() == null || dto.readerId().trim().isEmpty()) {
            resp = ResponseEntity.badRequest().body("id is required");
            logRequestResponse("SAVE", "/api/save", null, dto, resp);
            return resp;
        }

        Optional<Mail> byId = mailRepository.findById(dto.readerId());
        if (byId.isPresent()) {
            Mail mail = getMail(dto, byId.get());
            mailRepository.save(mail);
        } else {
            Mail mail = getMail(dto, new Mail());
            mail.setId(dto.readerId());
            mailRepository.save(mail);
        }

        resp = ResponseEntity.ok().build();
        logRequestResponse("SAVE", "/api/save", dto.readerId(), dto, resp);
        return resp;
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

        ResponseEntity<?> resp;

        if (id == null) {
            resp = ResponseEntity.badRequest().body("id is required");
            logRequestResponse("GET", "/api/get/null", null, null, resp);
            return resp;
        }

        Optional<Mail> byId = mailRepository.findById(id);
        if (byId.isPresent()) {
            Mail mail = byId.get();
            resp = ResponseEntity.ok(new EidResDTO(mail.getId(), mail.getEid(),
                    mail.getImei(), mail.getImei2(), mail.getCapacity(),
                    mail.getFirst_use(), mail.getCycle(), mail.getModel(),
                    mail.getSerial(), mail.getModel_name(), mail.getMemory_capacity(),
                    mail.getAvailable()));
            logRequestResponse("GET", "/api/get/" + id, id, null, resp);
            return resp;
        } else {
            Mail mail = new Mail();
            mail.setId(id);
            mailRepository.save(mail);
            resp = ResponseEntity.ok().build();
            logRequestResponse("GET", "/api/get/" + id, id, null, resp);
            return resp;
        }
    }

    private void logRequestResponse(String action, String path, String readerId, Object reqBody, ResponseEntity<?> resp) {
        try {
            RequestLog log = new RequestLog();
            log.setAction(action);
            log.setPath(path);
            log.setReaderId(readerId);

            log.setRequestBody(reqBody == null ? null : objectMapper.writeValueAsString(reqBody));

            if (resp != null) {
                log.setResponseStatus(resp.getStatusCode().value());
                Object body = resp.getBody();
                log.setResponseBody(body == null ? null : objectMapper.writeValueAsString(body));
            }

            requestLogRepository.save(log);
        } catch (Exception ignored) {}
    }
}
