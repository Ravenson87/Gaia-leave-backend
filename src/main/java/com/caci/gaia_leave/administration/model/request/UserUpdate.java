package com.caci.gaia_leave.administration.model.request;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
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
@Entity
@JsonInclude(JsonInclude.Include.USE_DEFAULTS)
@Table(name = "user")
public class UserUpdate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "id")
    private Integer id;

    @NotNull(message = "role_id cannot be empty")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @JsonProperty("role_id")
    @Column(name = "role_id")
    private Integer roleId;

    @NotNull(message = "job_position_id cannot be empty")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @JsonProperty("job_position_id")
    @Column(name = "job_position_id")
    private Integer jobPositionId;

    @NotEmpty(message = "first_name cannot be empty or null")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("first_name")
    @Column(name = "first_name")
    private String firstName;

    @NotEmpty(message = "last_name cannot be empty or null")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("last_name")
    @Column(name = "last_name")
    private String lastName;

    @NotEmpty(message = "email cannot be empty or null")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("email")
    @Column(name = "email")
    @Email(message = "Invalid email format")
    private String email;

    @NotEmpty(message = "username cannot be empty or null")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("username")
    @Column(name = "username")
    private String username;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Column(name = "password")
    @Size(min = 8, message = "Incorrect password")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&]).{8,}$",
            message = "Incorrect password"
    )
    private String password;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Europe/Belgrade")
    @JsonProperty("date_of_birth")
    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("phone")
    @Column(name = "phone")
    private String phone;

    @NotNull(message = "status cannot be empty")
    @JsonFormat(shape = JsonFormat.Shape.BOOLEAN)
    @JsonProperty("status")
    @Column(name = "status")
    private Boolean status;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("profile_image")
    @Column(name = "profile_image")
    private String profileImage;
}
