package com.caci.gaia_leave.administration.controller;

import com.caci.gaia_leave.administration.model.response.EndpointResponse;
import com.caci.gaia_leave.administration.service.EndpointService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Validated
@RestController
@RequestMapping("/api/v1/endpoint")
@RequiredArgsConstructor
public class EndpointController {
    private final EndpointService endpointService;

    @GetMapping("/endpoints")
    public void readEndpoints() {
        endpointService.populate();
    }

    @GetMapping("/read")
    public ResponseEntity<List<EndpointResponse>> read(){
        return endpointService.read();
    }
}
