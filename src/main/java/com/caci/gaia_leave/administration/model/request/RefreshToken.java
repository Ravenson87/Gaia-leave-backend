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
public class RefreshToken extends Auditable implements Serializable {

    @Id
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @JsonProperty("id")
    @Column(name = "id")
    private Integer id;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("refresh_token")
    @Column(name = "refresh_token")
    private String refreshToken;
}
