package com.caci.gaia_leave.shared_tools.component;

import com.caci.gaia_leave.shared_tools.configuration.AppProperties;
import com.caci.gaia_leave.shared_tools.exception.CustomException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Component
@RequiredArgsConstructor
public class InitializationManager {

    private final AppProperties appProperties;

    @PostConstruct
    public void createImageFolder(){
    String dir = appProperties.getUploadImagePath();
    Path path = Paths.get(dir);
    try{
    Files.createDirectories(path);
    }catch(Exception e){
        throw new CustomException(e.getMessage());
    }
    }
}
