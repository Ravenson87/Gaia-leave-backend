package com.caci.gaia_leave.administration.service;

import com.caci.gaia_leave.administration.repository.request.EndpointRepository;
import com.caci.gaia_leave.administration.repository.response.EndpointResponseRepository;
import com.caci.gaia_leave.shared_tools.configuration.AppProperties;
import com.caci.gaia_leave.shared_tools.helper.AllHelpers;
import com.caci.gaia_leave.shared_tools.model.Mapping;
import com.caci.gaia_leave.shared_tools.model.MappingServiceWrapper;
import com.caci.gaia_leave.shared_tools.service.MappingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class EndpointService {

    private final AppProperties appProperties;
    private final MappingService mappingService;
    private final EndpointResponseRepository endpointResponseRepository;


    public void populate(){
        mappingService.getActuator(appProperties.getMsApplicationName());
        List<String> services = new ArrayList<>();
        List<Mapping> mappings = new ArrayList<>();

        MappingServiceWrapper mappingServiceWrapper = mappingService.getActuator(appProperties.getMsApplicationName());
        if(mappingServiceWrapper != null){
        services.addAll(mappingServiceWrapper.getServices());
        mappings.addAll(mappingServiceWrapper.getMappings());
        }

        if(services.isEmpty()){
            return;
        }
        System.out.println("01");

        deleteDiffEndpoints(mappings);

    }
    public void deleteDiffEndpoints(List<Mapping> mappings) {
        System.out.println("02");
        Set<String> servicesEndpoints = new HashSet<>();
        List<String> usedEndpoints = new ArrayList<>();


    }


}
