package com.caci.gaia_leave.administration.service;

import com.caci.gaia_leave.shared_tools.configuration.AppProperties;
import com.caci.gaia_leave.shared_tools.exception.CustomException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;

@Service
@RequiredArgsConstructor
public class MailService {
    private final AppProperties appProperties;
    private final JavaMailSender mailSender;

    public void sendEmail(String sendTo, String subject, String body, MultipartFile[] files) {
        String prepareAddresses = sendTo.trim().replaceAll("\\s+", "");
        boolean check = files != null;
        FileSystemResource fsr = null;

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, check);
            helper.setFrom(appProperties.getSendMailFrom());
            helper.setTo(InternetAddress.parse(prepareAddresses));
            helper.setSubject(subject);
            helper.setText(body, true);
            if (check) {
                for (MultipartFile file : files) {
                    String fileName = file.getOriginalFilename();
                    fsr = new FileSystemResource(fileConverter(file));
                    helper.addAttachment(fileName, fsr);
                }
            }
            mailSender.send(message);

            if (fsr != null) {
                try {
                    if (fsr.isFile()) Files.delete(fsr.getFile().toPath());
                } catch (Exception e) {
                    throw new CustomException("File in attachment is required");
                }
            }

        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
    }

    private File fileConverter(MultipartFile file) {
        File tempFile = new File(file.getOriginalFilename());
        try {
            FileOutputStream fos = new FileOutputStream(tempFile);
            fos.write(file.getBytes());
            fos.close();
            return tempFile;
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
    }

}
