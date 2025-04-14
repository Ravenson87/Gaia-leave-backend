package com.caci.gaia_leave.administration.model.response;

import com.caci.gaia_leave.shared_tools.model.WorkingDayType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
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
public class CalendarResponse implements Serializable {

    @Id
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @JsonProperty("id")
    @Column(name = "id")
    private Integer id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Europe/Belgrade")
    @JsonProperty("date")
    @Column(name = "date")
    private Date date;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("days")
    @Column(name = "days")
    private String days;

    @NotEmpty(message = "type cannot be empty or null")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("type")
    @Column(name = "type")
    private String type;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("description")
    @Column(name = "description")
    private String description;
}
