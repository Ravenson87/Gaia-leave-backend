package com.caci.gaia_leave.shared_tools.component;

import com.caci.gaia_leave.shared_tools.configuration.AppProperties;
import com.caci.gaia_leave.shared_tools.exception.CustomException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import static com.caci.gaia_leave.shared_tools.helper.AllHelpers.checkOs;


@Component
@RequiredArgsConstructor
public class InitializationManager {

    private final AppProperties appProperties;

    /**
     * Create folder where images should be stored
     */
    // Annotation PostConstruct serve to invoke method when application start
    @PostConstruct
    public void createImageFolder() {
        //Create path for directory
        String dir = appProperties.getUploadImagePath();
        Path path = Paths.get(dir);
        try {
            // Create directory on given path
            Files.createDirectories(path);

            // Check OS and if it is linux, give it standard linux permissions
            if (Objects.requireNonNull(checkOs()).equalsIgnoreCase("linux")) {
                ProcessBuilder processBuilder = new ProcessBuilder();
                processBuilder.command("bash", "-c", "sudo chmod 777 " + dir);
                processBuilder.start();
            }
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
    }
}
