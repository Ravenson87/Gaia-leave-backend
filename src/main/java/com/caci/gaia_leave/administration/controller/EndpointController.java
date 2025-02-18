package com.caci.gaia_leave.administration.controller;

import com.caci.gaia_leave.administration.service.EndpointService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
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
}
