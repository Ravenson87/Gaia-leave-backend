package com.caci.gaia_leave.shared_tools.component;

import com.caci.gaia_leave.shared_tools.configuration.AppProperties;
import com.caci.gaia_leave.shared_tools.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.caci.gaia_leave.shared_tools.helper.AllHelpers.checkOs;

@Component
@RequiredArgsConstructor
public class DocumentHandler {

    private final AppProperties appProperties;

    //TODO Check why document have wrong path when we save it

    /**
     * Method store document on server, and save path in table in database
     *
     * @param prefix            String
     * @param file              MultipartFile
     * @param allowedExtensions String[]
     * @return String
     */
    public String storeDocument(String prefix, MultipartFile file, String[] allowedExtensions) {

        // Get original file name, then take extension from it and create new, optimised file name
        String originalFileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String fileExtension = StringUtils.getFilenameExtension(originalFileName);
        String newFileName =
                prefix + "_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + "." + fileExtension;
        // Check if file extension is in allowed extension list
        List<String> allowedExtensionsList = Arrays.asList(allowedExtensions);
        if (!allowedExtensionsList.contains(fileExtension)) {
            throw new CustomException("Invalid document extension");
        }

        // Find path where to store document, then copy/store or replace document in folder
        try {
            Path filePath = Paths.get(appProperties.getUploadImagePath()).resolve(newFileName).normalize();
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // Check OS and if it is linux, give it standard linux permissions
            if (Objects.requireNonNull(checkOs()).equalsIgnoreCase("linux")) {
                ProcessBuilder processBuilder = new ProcessBuilder();
                processBuilder.command("bash", "-c", "sudo chmod 777 " + filePath);
                processBuilder.start();
            }

        } catch (Exception e) {
            throw new CustomException(e.getMessage());

        }
        // Method return full path of stored document with domain name, storage path and file name
        // Method getImageStorageGaiaLeave() is used on documents because we store documents on same place as images
        return appProperties.getDomainName() + appProperties.getImageStorageGaiaLeave() + newFileName;
    }


}
