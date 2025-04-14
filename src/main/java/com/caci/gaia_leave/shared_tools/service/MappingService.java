package com.caci.gaia_leave.shared_tools.service;

import com.caci.gaia_leave.shared_tools.configuration.AppProperties;
import com.caci.gaia_leave.shared_tools.exception.CustomException;
import com.caci.gaia_leave.shared_tools.model.Mapping;
import com.caci.gaia_leave.shared_tools.model.MappingServiceWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MappingService {
    private final AppProperties appProperties;
    private final RestTemplate restTemplate;

    @Value("${spring.profiles.active}")
    private String activeProfile;
    @Value("${server.port}")
    private String serverPort;

    public MappingServiceWrapper getActuator(String appName) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.setBasicAuth(appProperties.getGlobalUser(), appProperties.getGlobalPassword());

        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> res = restTemplate.exchange(currentUrl() +"/actuator/mappings", HttpMethod.GET, entity, String.class);

        if (res.getStatusCode() != HttpStatus.OK || !res.hasBody() || res.getBody() == null) {
            return null;
        }

        return getMapping(res.getBody(), appName);
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

        MappingServiceWrapper mappingServiceWrapper = new MappingServiceWrapper();
        List<Mapping> mappings = new LinkedList<>();
        List<String> servicesList = new LinkedList<>();

        if (mappingNode.isMissingNode() || !mappingNode.isArray()) {
            mappingServiceWrapper.setMappings(mappings);
            mappingServiceWrapper.setServices(servicesList);
            return mappingServiceWrapper;
        }

        for (JsonNode mapping : mappingNode) {
            JsonNode mappingDetails = mapping.path("details");

            if (mappingDetails.isMissingNode() || mappingDetails.isNull()) {
                continue;
            }

            JsonNode handlerMethod = mappingDetails.path("handlerMethod");
            JsonNode requestMappingConditions = mappingDetails.path("requestMappingConditions");
            JsonNode methodNameNod = requestMappingConditions.path("methods");
            JsonNode patternsNode = requestMappingConditions.path("patterns");

            JsonNode classNameNode = handlerMethod.path("className");
            if (classNameNode.isMissingNode() || classNameNode.isNull()) {
                continue;
            }

            String classNameText = classNameNode.asText();

            String controllerName = classNameText.substring(classNameText.lastIndexOf(".") + 1);
            String[] serviceNameArray = classNameText.split("\\.");

            if (serviceNameArray.length < 4) {
                continue;
            }

            if (methodNameNod.isEmpty() || patternsNode.isEmpty()) {
                continue;
            }

            String serviceName = serviceNameArray[3];
            String methodName = methodNameNod.get(0).asText();

            for (JsonNode pattern : patternsNode) {
                String patternText = pattern.asText();
                if (patternText.contains("actuator") || Arrays.asList(appProperties.getExcludedPackets()).contains(serviceName)) {
                    continue;
                }
                String hashPath = DigestUtils.md5Hex(appName + "_" + controllerName + "_" + pattern.asText());
                String hashController = DigestUtils.md5Hex(appName + "_" + controllerName);

                Mapping newMapping = new Mapping();
                newMapping.setMethod(methodName);
                newMapping.setService(serviceName);
                newMapping.setController(controllerName);
                newMapping.setHashController(hashController);
                newMapping.setPath(pattern.asText());
                newMapping.setHashPath(hashPath);
                mappings.add(newMapping);
                if (!servicesList.contains(serviceName)) {
                    servicesList.add(serviceName);
                }

            }

        }
        mappingServiceWrapper.setMappings(mappings);
        mappingServiceWrapper.setServices(servicesList);
        return mappingServiceWrapper;
    }


    public String endpoint(HttpServletRequest request) {
        return (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
    }

    /**
     * Get current url.
     *
     * @return String
     */
    private String currentUrl() {
        String local = "http://localhost:" + serverPort, remote = local + "/" + appProperties.getMsApplicationName();
        return activeProfile.equals("local") ? local : remote;
    }
}
