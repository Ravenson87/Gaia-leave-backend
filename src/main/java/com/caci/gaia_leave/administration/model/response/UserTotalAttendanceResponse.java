package com.caci.gaia_leave.administration.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
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
@Table(name = "user_total_attendance")
public class UserTotalAttendanceResponse implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotNull(message = "user_id cannot be empty")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @JsonProperty("user_id")
    @Column(name = "user_id")
    private Integer userId;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @JsonProperty("total_free_days")
    @Column(name = "total_free_days")
    private Integer totalFreeDays;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @JsonProperty("total_working_hours")
    @Column(name = "total_working_hours")
    private Integer totalWorkingHours;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @JsonProperty("overtime_hours_sum")
    @Column(name = "overtime_hours_sum")
    private Integer overtimeHoursSum;

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
