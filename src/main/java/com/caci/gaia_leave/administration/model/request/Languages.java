package com.caci.gaia_leave.administration.model.request;

import com.caci.gaia_leave.shared_tools.model.Auditable;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.USE_DEFAULTS)
@Table(name = "languages")
public class Languages extends Auditable implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "id")
    private Integer id;

    @NotEmpty(message = "name cannot be empty")
    @NotBlank(message = "name cannot be empty")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("name")
    @Column(name = "name")
    private String name;

    @NotEmpty(message = "code cannot be empty")
    @NotBlank(message = "code cannot be empty")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("code")
    @Column(name = "code")
    private String code;
}
