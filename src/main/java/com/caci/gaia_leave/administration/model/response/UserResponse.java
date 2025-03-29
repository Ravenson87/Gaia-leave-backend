package com.caci.gaia_leave.administration.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "password")
    private String password;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Europe/Belgrade")
    @JsonProperty("date_of_birth")
    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("phone")
    @Column(name = "phone")
    private String phone;

    @JsonFormat(shape = JsonFormat.Shape.BOOLEAN)
    @JsonProperty("verified")
    @Column(name = "verified")
    private Boolean verified;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("profile_image")
    @Column(name = "profile_image")
    private String profileImage;

    @JsonFormat(shape = JsonFormat.Shape.BOOLEAN)
    @JsonProperty("status")
    @Column(name = "status")
    private Boolean status;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("refresh_token")
    @Column(name = "refresh_token")
    private String refreshToken;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("refresh_token_expire_time")
    @Column(name = "refresh_token_expire_time")
    private Date refreshTokenExpireTime;

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

    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", referencedColumnName = "id",
            insertable = false, updatable = false)
    private RoleResponse role;

    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "user_id", referencedColumnName = "id")
    @Column(insertable = false,
            updatable = false)
    private List<OvertimeHoursResponse> overtimeHours;

    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "user_id", referencedColumnName = "id")
    @Column(insertable = false,
            updatable = false)
    private List<UserUsedFreeDaysResponse> userUsedFreeDays;

    //Moguci problemi -zasto je ovde "name=id" a "referencedColumnName=user_id", a ne obratno kao u @OneToMany relaciji
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id", referencedColumnName = "user_id",
            insertable = false, updatable = false)
    private UserTotalAttendanceResponse userTotalAttendance;

    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id",
            insertable = false, updatable = false)
    private List<UserDocumentsResponse> userDocuments;

}
