package com.caci.gaia_leave.administration.controller;

import com.caci.gaia_leave.administration.service.MailService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Validated
@RequestMapping("/api/v1/mail")
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;

    @PostMapping("/send")
    public void sendMail(
            @Valid
            @NotEmpty(message = "Sender cannot be empty or null")
            @RequestParam("to")
            String to,
            @NotEmpty(message = "Subject cannot be empty or null")
            @RequestParam("subject")
            String subject,
            @NotEmpty(message = "Body cannot be empty or null")
            @RequestParam("body")
            String body,
            @RequestParam(value = "file", required = false)
            MultipartFile[] file
    ) {
        mailService.sendEmail(to, subject, body, file);
    }
}
