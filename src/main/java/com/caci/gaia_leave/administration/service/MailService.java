package com.caci.gaia_leave.administration.service;

import com.caci.gaia_leave.shared_tools.configuration.AppProperties;
import com.caci.gaia_leave.shared_tools.exception.CustomException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class MailService {
    private final AppProperties appProperties;
    private final JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String body, MultipartFile[] files) {

        if (to == null || to.isEmpty()) {
            throw new IllegalArgumentException("Sender cannot be null or empty");
        }

        if (subject == null || subject.isEmpty()) {
            throw new IllegalArgumentException("Subject cannot be null or empty");
        }

        if (body == null || body.isEmpty()) {
            throw new IllegalArgumentException("Body cannot be null or empty");
        }

        boolean check = files != null;

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, check);
            helper.setFrom(appProperties.getSendMailFrom());
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true);
            if (check) {
                for (MultipartFile file : files) {
                    String fileName = file.getOriginalFilename();
                    helper.addAttachment(fileName, file);
                }
            }
            mailSender.send(message);
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
    }
}
