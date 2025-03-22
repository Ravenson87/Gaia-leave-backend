package com.caci.gaia_leave.shared_tools.component;

import com.caci.gaia_leave.shared_tools.configuration.AppProperties;
import com.caci.gaia_leave.shared_tools.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class DocumentHandler {

    private final AppProperties appProperties;

    public String storeDocument(String prefix, MultipartFile file, String[] allowedExtensions) {
        String originalFileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String fileExtension = StringUtils.getFilenameExtension(originalFileName);
        String newFileName =
                prefix + "_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + "." + fileExtension;
        List<String> allowedExtensionsList = Arrays.asList(allowedExtensions);
        if(!allowedExtensionsList.contains(fileExtension)){
            throw new CustomException("Invalid image extension");
        }

        try {

            Path filePath = Paths.get(appProperties.getUploadImagePath()).resolve(newFileName).normalize();
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            if (Objects.requireNonNull(checkOs()).equalsIgnoreCase("linux")) {
                ProcessBuilder processBuilder = new ProcessBuilder();
                processBuilder.command("bash", "-c", "sudo chmod 777 " + filePath);
                processBuilder.start();
            }

        } catch (Exception e) {
            throw new CustomException(e.getMessage());

        }

        return appProperties.getDomainName() + appProperties.getImageStorageGaiaLeave() + newFileName;
    }

    private String checkOs() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            return "windows";
        } else if (os.contains("nux") || os.contains("nix")) {
            return "linux";
        } else {
            return null;
        }
    }
}
