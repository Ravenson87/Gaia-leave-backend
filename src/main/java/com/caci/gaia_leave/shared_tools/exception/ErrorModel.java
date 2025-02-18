package com.caci.gaia_leave.shared_tools.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.USE_DEFAULTS)
public class ErrorModel {
    private Date timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;
}
