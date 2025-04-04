package com.caci.gaia_leave.administration.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
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
@Table(name = "free_days_booking")
public class FreeDaysBookingResponse implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @JsonProperty("user_id")
    @Column(name = "user_id")
    private Integer userId;

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
