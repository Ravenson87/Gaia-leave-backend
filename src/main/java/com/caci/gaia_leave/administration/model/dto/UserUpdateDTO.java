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
public class UserUpdateDTO {

    @NotNull(message = "Id cannot be null")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @JsonProperty("id")
    private Integer id;

    @NotNull(message = "role_id cannot be null")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @JsonProperty("role_id")
    private Integer roleId;

    @NotNull(message = "job_position_id cannot be empty")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @JsonProperty("job_position_id")
    private Integer jobPositionId;

    @NotEmpty(message = "first_name cannot be empty or null")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("first_name")
    private String firstName;

    @NotEmpty(message = "last_name cannot be empty or null")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("last_name")
    private String lastName;

    @NotEmpty(message = "email cannot be empty or null")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("email")
    @Email(message = "Invalid email format")
    private String email;

    @NotEmpty(message = "username cannot be empty or null")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("username")
    private String username;

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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Europe/Belgrade")
    @JsonProperty("personal_holiday")
    private Date personalHoliday;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("phone")
    private String phone;

    @NotNull(message = "status cannot be empty")
    @JsonFormat(shape = JsonFormat.Shape.BOOLEAN)
    @JsonProperty("status")
    private Boolean status;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("profile_image")
    private String profileImage;


}
