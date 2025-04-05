package com.caci.gaia_leave.administration.model.request;

import com.caci.gaia_leave.administration.model.response.CalendarResponse;
import com.caci.gaia_leave.shared_tools.model.Auditable;
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

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@JsonInclude(JsonInclude.Include.USE_DEFAULTS)
@Table(name = "free_days_booking")
public class FreeDaysBooking extends Auditable implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
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

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("description")
    @Column(name = "description")
    private String description;

    @NotNull(message = "status cannot be empty")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @JsonProperty("status")
    @Column(name = "status")
    private Integer status;

    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id",
            insertable = false, updatable = false)
    private User user;

    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "calendar_id", referencedColumnName = "id",
            insertable = false, updatable = false)
    private Calendar calendar;

}
