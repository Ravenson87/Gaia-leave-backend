package com.caci.gaia_leave.shared_tools.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.USE_DEFAULTS)
public class AuthorizationTokenDTO {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("token")
    private String token;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("refreshToken")
    private String refreshToken;

}
