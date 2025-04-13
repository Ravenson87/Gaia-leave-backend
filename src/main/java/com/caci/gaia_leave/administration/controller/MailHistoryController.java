package com.caci.gaia_leave.administration.controller;

import com.caci.gaia_leave.administration.model.request.MailHistory;
import com.caci.gaia_leave.administration.model.response.MailHistoryResponse;
import com.caci.gaia_leave.administration.service.MailHistoryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Validated
@RequestMapping("/api/v1/mail-history")
@RequiredArgsConstructor
public class MailHistoryController {

    private final MailHistoryService mailHistoryService;

    @PostMapping("/create")
    public void createMailHistory(
            @Valid
            @NotEmpty(message = "Model cannot be empty or null")
            MailHistory model
    ) {
        mailHistoryService.create(model);
    }

    @GetMapping("/read")
    public ResponseEntity<List<MailHistory>> read() {
        return mailHistoryService.read();
    }

    @GetMapping("/read-by-id")
    public ResponseEntity<MailHistory> readById(
            @Valid
            @NotNull(message = "Id cannot be empty")
            Integer id
    ) {
        return mailHistoryService.readById(id);
    }

    @GetMapping("/read-by-addresses")
    public ResponseEntity<List<MailHistoryResponse>> readByAddress(
            @Valid
            @NotEmpty(message = "Addresses cannot be empty")
            String addresses
    ) {
        return mailHistoryService.readByAddresses(addresses);
    }

    @GetMapping("/read-by-messages")
    public ResponseEntity<List<MailHistoryResponse>> readByMessage(
            @Valid
            @NotEmpty(message = "Message cannot be empty")
            String message
    ) {
        return mailHistoryService.readByMessage(message);
    }
}
