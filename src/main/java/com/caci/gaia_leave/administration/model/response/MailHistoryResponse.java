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

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@JsonInclude(JsonInclude.Include.USE_DEFAULTS)
@Table(name = "mail_history")
public class MailHistoryResponse implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("addresses")
    @Column(name = "addresses")
    private String addresses;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("message")
    @Column(name = "message")
    private String message;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("created_date")
    @Column(name = "created_date")
    private Date createdDate;
}
