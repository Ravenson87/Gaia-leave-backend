package com.caci.gaia_leave.administration.model.request;

import com.caci.gaia_leave.shared_tools.model.Auditable;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
@Table(name = "job_position")
public class JobPosition extends Auditable implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "id")
    private Integer id;

    @NotBlank(message = "title must be provided")
    @NotNull(message = "title must be provided")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("title")
    @Column(name = "title")
    private String title;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("description")
    @Column(name = "description")
    private String description;
}
