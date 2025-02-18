package com.caci.gaia_leave.administration.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Table(name = "user")
public class UserResponse implements Serializable {

    @Id
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @JsonProperty("id")
    @Column(name = "id")
    private Integer id;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @JsonProperty("role_id")
    @Column(name = "role_id")
    private Integer roleId;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @JsonProperty("job_position_id")
    @Column(name = "job_position_id")
    private Integer jobPositionId;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("first_name")
    @Column(name = "first_name")
    private String firstName;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("last_name")
    @Column(name = "last_name")
    private String lastName;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("email")
    @Column(name = "email")
    private String email;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("username")
    @Column(name = "username")
    private String username;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("password")
    @Column(name = "password")
    private String password;

    @JsonFormat(shape = JsonFormat.Shape.BOOLEAN)
    @JsonProperty("status")
    @Column(name = "status")
    private Boolean status;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("created_by")
    @Column(name = "created_by")
    private String createdBy;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("last_modified_by")
    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("created_date")
    @Column(name = "created_date")
    private Date createdDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("last_modified_date")
    @Column(name = "last_modified_date")
    private Date lastModifiedDate;
}
