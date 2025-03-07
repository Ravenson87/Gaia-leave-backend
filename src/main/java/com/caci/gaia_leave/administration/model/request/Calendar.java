package com.caci.gaia_leave.administration.model.request;

import com.caci.gaia_leave.shared_tools.model.Auditable;
import com.caci.gaia_leave.shared_tools.model.WorkingDayType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@JsonInclude(JsonInclude.Include.USE_DEFAULTS)
@Table(name = "calendar")
public class Calendar implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "id")
    private Integer id;

    @NotNull(message = "date cannot be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonProperty("date")
    @Column(name = "date")
    private Date date;

    @NotEmpty(message = "days cannot be empty")
    @NotNull(message = "days cannot be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("days")
    @Column(name = "days")
    private String days;

    //Expected problems, look for Enums
    @NotNull(message = "type cannot be empty")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Enumerated(EnumType.STRING)
    @JsonProperty("type")
    @Column(name = "type")
    private WorkingDayType type;
}
