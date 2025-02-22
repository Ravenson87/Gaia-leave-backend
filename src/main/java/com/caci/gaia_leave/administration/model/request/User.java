package com.caci.gaia_leave.administration.model.request;

import com.caci.gaia_leave.shared_tools.model.Auditable;
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

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@JsonInclude(JsonInclude.Include.USE_DEFAULTS)
@Table(name = "user")
public class User extends Auditable implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "id")
    private Integer id;

    @NotEmpty(message = "role_id cannot be empty")
    @NotNull(message = "role_id cannot be empty")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @JsonProperty("role_id")
    @Column(name = "role_id")
    private Integer roleId;

    @NotEmpty(message = "job_position_id cannot be empty")
    @NotNull(message = "job_position_id cannot be empty")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @JsonProperty("job_position_id")
    @Column(name = "job_position_id")
    private Integer jobPositionId;

    @NotEmpty(message = "first_name cannot be empty")
    @NotNull(message = "first_name cannot be empty")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("first_name")
    @Column(name = "first_name")
    private String firstName;

    @NotEmpty(message = "last_name cannot be empty")
    @NotNull(message = "last_name cannot be empty")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("last_name")
    @Column(name = "last_name")
    private String lastName;

    @NotEmpty(message = "email cannot be empty")
    @NotNull(message = "email cannot be empty")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("email")
    @Column(name = "email")
    private String email;

    @NotEmpty(message = "username cannot be empty")
    @NotNull(message = "username cannot be empty")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("username")
    @Column(name = "username")
    private String username;

    @NotEmpty(message = "password cannot be empty")
    @NotNull(message = "password cannot be empty")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("password")
    @Column(name = "password")
    private String password;

    @NotEmpty(message = "status cannot be empty")
    @NotNull(message = "status cannot be empty")
    @JsonFormat(shape = JsonFormat.Shape.BOOLEAN)
    @JsonProperty("status")
    @Column(name = "status")
    private Boolean status;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("refresh_token")
    @Column(name = "refresh_token")
    private String refreshToken;
}
