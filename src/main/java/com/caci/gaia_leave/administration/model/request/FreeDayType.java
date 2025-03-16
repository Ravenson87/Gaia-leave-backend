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
@Table(name = "free_day_type")
public class FreeDayType extends Auditable implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "id")
    private Integer id;

    @NotBlank(message = "type must be provided")
    @NotNull(message = "type must be provided")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("type")
    @Column(name = "type")
    private String type;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("description")
    @Column(name = "description")
    private String description;
}
