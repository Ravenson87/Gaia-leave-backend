package com.caci.gaia_leave.shared_tools.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.USE_DEFAULTS)
public class Mapping {


    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("method")
    private String method;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("service")
    private String service;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("controller")
    private String controller;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("hashController")
    private String hashController;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("path")
    private String path;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("hashPath")
    private String hashPath;


}
