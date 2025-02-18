package com.caci.gaia_leave.administration.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@JsonInclude(JsonInclude.Include.USE_DEFAULTS)
@Table(name = "endpoint")
public class EndpointResponse implements Serializable {

    @Id
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("id")
    @Column(name = "id")
    private String id;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("method")
    @Column(name = "method")
    private String method;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("service")
    @Column(name = "service")
    private String service;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("controller")
    @Column(name = "controller")
    private String controller;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("controller_alias")
    @Column(name = "controller_alias")
    private String controllerAlias;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("action")
    @Column(name = "action")
    private String action;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("enpoint")
    @Column(name = "endpoint")
    private String endpoint;
}
