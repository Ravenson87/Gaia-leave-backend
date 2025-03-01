package com.caci.gaia_leave.administration.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@JsonInclude(JsonInclude.Include.USE_DEFAULTS)
@Table(name = "leave_days")
public class LeaveDays {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "id")
    private Integer id;

    @NotEmpty(message = "name cannot be empty")
    @NotBlank(message = "name cannot be empty")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("type")
    @Column(name = "type")
    private String type;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonProperty("date")
    @Column(name = "date")
    private Date date;

    @NotNull(message = "year cannot be null")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @JsonProperty("year")
    @Column(name = "year")
    private Integer year;
}
