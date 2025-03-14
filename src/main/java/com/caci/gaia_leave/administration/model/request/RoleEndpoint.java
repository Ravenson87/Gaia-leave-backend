package com.caci.gaia_leave.administration.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "role_endpoint")
public class RoleEndpoint implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "id")
    private Integer id;

    @NotNull(message = "role_id must be provided")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @JsonProperty("role_id")
    @Column(name = "role_id")
    private Integer roleId;

    @NotNull(message = "endpoint_id must be provided")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("endpoint_id")
    @Column(name = "endpoint_id")
    private String endpointId;
}
