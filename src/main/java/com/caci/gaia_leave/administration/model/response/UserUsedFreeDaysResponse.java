package com.caci.gaia_leave.administration.model.response;

import com.caci.gaia_leave.administration.model.request.Calendar;
import com.caci.gaia_leave.administration.model.request.FreeDayType;
import com.fasterxml.jackson.annotation.JsonFormat;
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
@Table(name = "user_used_free_days")
public class UserUsedFreeDaysResponse implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotNull(message = "user_id cannot be empty")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @JsonProperty("user_id")
    @Column(name = "user_id")
    private Integer userId;

    @NotNull(message = "calendar_id cannot be empty")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @JsonProperty("calendar_id")
    @Column(name = "calendar_id")
    private Integer calendarId;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @JsonProperty("free_day_type_id")
    @Column(name = "free_day_type_id")
    private Integer freeDayTypeId;

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
    @JoinColumn(name = "calendar_id", referencedColumnName = "id",
            insertable = false, updatable = false)
    private Calendar calendar;

    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "free_day_type_id", referencedColumnName = "id",
            insertable = false, updatable = false)
    private FreeDayType freeDayType;
}
