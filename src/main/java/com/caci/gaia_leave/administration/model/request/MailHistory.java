package com.caci.gaia_leave.administration.model.request;

import com.caci.gaia_leave.shared_tools.model.Auditable;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
@Table(name = "mail_history")
public class MailHistory extends Auditable implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "id")
    private Integer id;

    @NotEmpty(message = "Sender must be provided")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("sender")
    @Column(name = "sender")
    private String sender;

    @NotEmpty(message = "send_to must be provided")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("send_to")
    @Column(name = "send_to")
    private String sendTo;

    @NotEmpty(message = "Subject must be provided")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("subject")
    @Column(name = "subject")
    private String subject;

    @NotEmpty(message = "Sender must be provided")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("service_name")
    @Column(name = "service_name")
    private String serviceName;

    @NotEmpty(message = "Endpoint must be provided")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("endpoint")
    @Column(name = "endpoint")
    private String endpoint;
}
