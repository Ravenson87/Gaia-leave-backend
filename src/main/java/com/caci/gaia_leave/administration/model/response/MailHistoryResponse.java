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
    @JsonProperty("sender")
    @Column(name = "sender")
    private String sender;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("send_to")
    @Column(name = "send_to")
    private String sendTo;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("subject")
    @Column(name = "subject")
    private String subject;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("service_name")
    @Column(name = "service_name")
    private String serviceName;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("endpoint")
    @Column(name = "endpoint")
    private String endpoint;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("created_by")
    @Column(name = "created_by")
    private String createdBy;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("created_date")
    @Column(name = "created_date")
    private Date createdDate;
}
