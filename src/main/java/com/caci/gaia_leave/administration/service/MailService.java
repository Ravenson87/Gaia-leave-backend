package com.caci.gaia_leave.administration.service;

import com.caci.gaia_leave.administration.model.request.MailHistory;
import com.caci.gaia_leave.shared_tools.configuration.AppProperties;
import com.caci.gaia_leave.shared_tools.exception.CustomException;
import com.caci.gaia_leave.shared_tools.helper.AllHelpers;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.Base64;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class MailService {
    private final AppProperties appProperties;
    private final JavaMailSender mailSender;
    private final MailHistoryService mailHistoryService;

    /**
     * @param sendTo  String
     * @param subject String
     * @param body    String
     * @param files   Multipart files
     */
    public void sendEmail(String sendTo, String subject, String body, MultipartFile[] files) {
        // Prepare email address
        String prepareAddresses = sendTo.trim().replaceAll("\\s+", "");
        // Check is file null
        boolean check = files != null;

        FileSystemResource fsr = null;

        // Create and prepare email to be sent
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
            createMailHistory(prepareAddresses, body);

            // Check if there is a saved file and delete file if it is already sent
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

    /**
     * Create MailHistory in database
     *
     * @param prepareAddresses String
     * @param body             String
     */
    private void createMailHistory(String prepareAddresses, String body) {
        MailHistory model = new MailHistory();
        LocalDate date = LocalDate.now();
        Date convertedDate = AllHelpers.convertToDateViaInstant(date);

        model.setAddresses(prepareAddresses);
        try {
            Base64.getDecoder().decode(body);
            model.setMessage(body);
        } catch (Exception e) {
            String encodedBody = Base64.getEncoder().encodeToString(body.getBytes());
            model.setMessage(encodedBody);
        }
        model.setCreatedDate(convertedDate);
        mailHistoryService.create(model);
    }
}
