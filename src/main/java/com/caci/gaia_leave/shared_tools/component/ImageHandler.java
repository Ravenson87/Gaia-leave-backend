package com.caci.gaia_leave.shared_tools.component;

import com.caci.gaia_leave.shared_tools.configuration.AppProperties;
import com.caci.gaia_leave.shared_tools.exception.CustomException;
import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.caci.gaia_leave.shared_tools.component.AppConst.PROFILE_IMG_MAX_SIZE;
import static com.caci.gaia_leave.shared_tools.helper.AllHelpers.checkOs;

@Component
@RequiredArgsConstructor
public class ImageHandler {

    private final AppProperties appProperties;

    public String storeImage(String prefix, MultipartFile file, String[] allowedExtensions) {
        String originalFileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String fileExtension = StringUtils.getFilenameExtension(originalFileName);
        List<String> allowedExtensionsList = Arrays.asList(allowedExtensions);

        if(!allowedExtensionsList.contains(fileExtension)){
            throw new CustomException("Invalid image extension");
        }

        String newFileName =
                prefix + "_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + "." + fileExtension;
        try {
            Path filePath = Paths.get(appProperties.getUploadImagePath()).resolve(newFileName).normalize();

            // Check the image dimensions and resize if necessary.
            BufferedImage image = ImageIO.read(file.getInputStream());
            int width = image.getWidth();
            int height = image.getHeight();


            if (width > PROFILE_IMG_MAX_SIZE || height > PROFILE_IMG_MAX_SIZE) {
                Thumbnails.Builder<BufferedImage> thumbnailBuilder = Thumbnails.of(image)
                        .size(PROFILE_IMG_MAX_SIZE, PROFILE_IMG_MAX_SIZE)
                        .outputFormat(fileExtension)
                        .keepAspectRatio(true);

                // Saving the resized image.
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                thumbnailBuilder.toOutputStream(baos);
                InputStream resizedImageInputStream = new ByteArrayInputStream(baos.toByteArray());
                Files.copy(resizedImageInputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            } else {
                // Saving the original image if resizing is not necessary.
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            }

            if (Objects.requireNonNull(checkOs()).equalsIgnoreCase("linux")) {
                ProcessBuilder processBuilder = new ProcessBuilder();
                processBuilder.command("bash", "-c", "sudo chmod 777 " + filePath);
                processBuilder.start();
            }
            System.out.println(appProperties.getDomainName() + appProperties.getImageStorageGaiaLeave() + newFileName);
            return appProperties.getDomainName() + appProperties.getImageStorageGaiaLeave() + newFileName;
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
    }


}
