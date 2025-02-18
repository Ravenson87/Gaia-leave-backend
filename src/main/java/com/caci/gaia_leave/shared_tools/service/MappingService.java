package com.caci.gaia_leave.shared_tools.service;

import com.caci.gaia_leave.shared_tools.configuration.AppProperties;
import com.caci.gaia_leave.shared_tools.exception.CustomException;
import com.caci.gaia_leave.shared_tools.model.Mapping;
import com.caci.gaia_leave.shared_tools.model.MappingServiceWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class MappingService {
    private final AppProperties appProperties;
    private final RestTemplate restTemplate;


    public MappingServiceWrapper getActuator(String appName) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.setBasicAuth(appProperties.getGlobalUser(), appProperties.getGlobalPassword());

        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> res = restTemplate.exchange("http://localhost:8080/actuator/mappings", HttpMethod.GET, entity, String.class);

        if (res.getStatusCode() != HttpStatus.OK || !res.hasBody() || res.getBody() == null) {
            return null;
        }

        getMapping(res.getBody(), appName);


        return null;
    }

    public MappingServiceWrapper getMapping(String body, String appName) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode;

        try {
            rootNode = mapper.readTree(body);

        } catch (JsonProcessingException e) {
            throw new CustomException(e.getMessage());
        }

        if (rootNode == null) {
            return null;
        }

        JsonNode mappingNode = rootNode
                .path("contexts")
                .path(appName)
                .path("mappings")
                .path("dispatcherServlets")
                .path("dispatcherServlet");

        if (mappingNode == null) {
            return null;
        }

        MappingServiceWrapper mappingServiceWrapper = new MappingServiceWrapper();
        List<Mapping> mappings = new ArrayList<>();
        List<String> services = new ArrayList<>();

        for (JsonNode mapping : mappingNode) {
            JsonNode mappingDetails = mapping.path("details");

            if (mappingDetails == null) {
                continue;
            }
            JsonNode handlerMethod = mappingDetails.path("handlerMethod");
            String className = handlerMethod.path("className").asText();


            JsonNode requestMappingConditions = mappingDetails.path("requestMappingConditions");
            JsonNode methodName = requestMappingConditions.path("methods");
            JsonNode patternName = requestMappingConditions.path("patterns");

            //TODO Pattern name nam ne stampa, ali nam zato stampa className... videti zasto!!!
            if(handlerMethod.isEmpty() || methodName.isEmpty() || patternName.isEmpty()) {
                continue;
            }

            if(patternName.asText().contains("actuator")) {
                continue;
            }
            System.out.println("handlerMethod: " + handlerMethod.asText());
            System.out.println("methodName: " + methodName.asText());
            System.out.println("patternName: " + patternName.asText());


        }


        return null;
    }

}
