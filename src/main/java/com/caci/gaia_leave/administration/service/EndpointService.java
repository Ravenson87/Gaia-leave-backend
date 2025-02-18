package com.caci.gaia_leave.administration.service;

import com.caci.gaia_leave.shared_tools.configuration.AppProperties;
import com.caci.gaia_leave.shared_tools.model.Mapping;
import com.caci.gaia_leave.shared_tools.service.MappingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EndpointService {

    private final AppProperties appProperties;
    private final MappingService mappingService;


    public void populate(){
        mappingService.getActuator(appProperties.getMsApplicationName());
        List<String> services = new ArrayList<>();
        List<Mapping> mappings = new ArrayList<>();




    }

}
