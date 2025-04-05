package com.caci.gaia_leave.administration.model.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class FreeDaysBookingDTO {

    @NotNull(message = "calendar_id cannot be empty")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @JsonProperty("calendar_id")
    @Column(name = "calendar_id")
    private Integer calendarId;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("description")
    @Column(name = "description")
    private String description;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @JsonProperty("status")
    @Column(name = "status")
    private Integer status;
}
