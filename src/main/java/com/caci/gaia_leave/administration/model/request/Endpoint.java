package com.caci.gaia_leave.administration.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@JsonInclude(JsonInclude.Include.USE_DEFAULTS)
@Table(name = "endpoint")
public class Endpoint implements Serializable {

    @Id
    @NotEmpty(message = "id cannot be empty")
    @NotNull(message = "id cannot be null")
    @Size(min = 1, max = 255, message = "id must be between 1 and 255")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("id")
    @Column(name = "id", nullable = false, unique = true)
    private String id;

    @NotBlank(message = "method cannot be empty")
    @NotEmpty(message = "method cannot be empty")
    @Size(min = 1, max = 10, message = "method must be between 1 and 10")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("method")
    @Column(name = "method")
    private String method;

    @NotEmpty(message = "service cannot be empty")
    @NotNull(message = "service cannot be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("service")
    @Column(name = "service")
    private String service;

    @NotEmpty(message = "controller cannot be empty")
    @NotNull(message = "controller cannot be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("controller")
    @Column(name = "controller")
    private String controller;

    @NotEmpty(message = "controller_alias cannot be empty")
    @NotNull(message = "controller_alias cannot be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("controller_alias")
    @Column(name = "controller_alias")
    private String controllerAlias;

    @NotEmpty(message = "action cannot be empty")
    @NotNull(message = "action cannot be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("action")
    @Column(name = "action")
    private String action;

    @NotEmpty(message = "endpoint cannot be empty")
    @NotNull(message = "endpoint cannot be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("endpoint")
    @Column(name = "endpoint")
    private String endpoint;
}
