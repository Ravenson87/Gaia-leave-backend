package com.caci.gaia_leave.administration.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserValidation {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("hash")
    private String hash;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Size(min = 8, message = "Incorrect password")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&]).{8,}$",
            message = "Incorrect password"
    )
    private String password;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Europe/Belgrade")
    @JsonProperty("date_of_birth")
    private Date dateOfBirth;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("phone")
    private String phone;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Europe/Belgrade")
    @JsonProperty("personal_holiday")
    private Date personalHoliday;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("holiday_description")
    private String holidayDescription;


}
