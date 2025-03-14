package com.caci.gaia_leave.administration.service;

import com.caci.gaia_leave.administration.model.request.Endpoint;
import com.caci.gaia_leave.administration.model.response.EndpointResponse;
import com.caci.gaia_leave.administration.repository.request.EndpointRepository;
import com.caci.gaia_leave.administration.repository.response.EndpointResponseRepository;
import com.caci.gaia_leave.shared_tools.configuration.AppProperties;
import com.caci.gaia_leave.shared_tools.exception.CustomException;
import com.caci.gaia_leave.shared_tools.helper.AllHelpers;
import com.caci.gaia_leave.shared_tools.model.Mapping;
import com.caci.gaia_leave.shared_tools.model.MappingServiceWrapper;
import com.caci.gaia_leave.shared_tools.service.MappingService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class EndpointService {

    private final AppProperties appProperties;
    private final MappingService mappingService;
    private final EndpointResponseRepository endpointResponseRepository;
    private final EndpointRepository endpointRepository;

    private final Map<String, String> actionMap = Map.of(
            "GET", "view",
            "POST", "create",
            "PUT", "update",
            "DELETE", "delete",
            "PATCH", "update"
    );

    @Transactional(rollbackOn = {Exception.class, CustomException.class})
    public void populate() {
        mappingService.getActuator(appProperties.getMsApplicationName());
        List<String> services = new ArrayList<>();
        List<Mapping> mappings = new ArrayList<>();

        MappingServiceWrapper mappingServiceWrapper = mappingService.getActuator(appProperties.getMsApplicationName());
        if (mappingServiceWrapper != null) {
            services.addAll(mappingServiceWrapper.getServices());
            mappings.addAll(mappingServiceWrapper.getMappings());
        }

        if (services.isEmpty()) {
            return;
        }
        deleteDiffEndpoints(mappings);
        List<Endpoint> allEndpoints = addEndpoint(mappings);
        if (allEndpoints == null || allEndpoints.isEmpty()) {
            return;
        }
        try {
            endpointRepository.saveAll(allEndpoints);
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }

    }

    public List<Endpoint> addEndpoint(List<Mapping> mappings) {
        List<Endpoint> endpoints = new ArrayList<>();
        for (Mapping mapping : mappings) {
            Endpoint endpoint = new Endpoint();
            endpoint.setId(mapping.getHashPath());
            endpoint.setMethod(mapping.getMethod());
            endpoint.setService(mapping.getService());
            endpoint.setController(mapping.getController());
            endpoint.setControllerAlias(mapping.getController().replace("Controller", ""));
            endpoint.setAction(actionMap.getOrDefault(mapping.getMethod(), "n/a"));
            endpoint.setEndpoint(mapping.getPath());
            endpoints.add(endpoint);
        }

        return endpoints;
    }

    public void deleteDiffEndpoints(List<Mapping> mappings) {

        Set<String> usedEndpoints = mappings.stream().map(Mapping::getHashPath).collect(Collectors.toSet());
        List<String> existedEndpoints = new ArrayList<>();

        endpointResponseRepository.findAll().forEach(endpointResponse -> {
            existedEndpoints.add(endpointResponse.getId());
        });

        List<String> diffEndpoints = existedEndpoints.stream().filter(existedEndpoint -> !usedEndpoints.contains(existedEndpoint)).toList();
        if (!diffEndpoints.isEmpty()) {
            try {
                endpointRepository.deleteAllById(diffEndpoints);
            } catch (Exception e) {
                throw new CustomException(e.getMessage());
            }
        }
    }

    public ResponseEntity<List<EndpointResponse>> read() {
    List<EndpointResponse> result = AllHelpers.listConverter(endpointResponseRepository.findAll());
        return result.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok().body(result);
    }
}
