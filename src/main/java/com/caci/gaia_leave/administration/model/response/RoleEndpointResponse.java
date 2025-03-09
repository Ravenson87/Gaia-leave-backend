package com.caci.gaia_leave.administration.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@JsonInclude(JsonInclude.Include.USE_DEFAULTS)
@Table(name = "role_endpoint")
public class RoleEndpointResponse {

    @Id
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @JsonProperty("id")
    @Column(name = "id")
    private Integer id;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @JsonProperty("role_id")
    @Column(name = "role_id")
    private Integer roleId;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("endpoint_id")
    @Column(name = "endpoint_id")
    private String endpointId;

    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "endpoint_id", referencedColumnName = "id"
            , insertable = false, updatable = false)
    private EndpointResponse endpoint;

}


