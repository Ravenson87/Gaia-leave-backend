package com.caci.gaia_leave.shared_tools.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.USE_DEFAULTS)
public class MappingServiceWrapper {

    @JsonProperty("services")
    public List<String> services = new LinkedList<>();

    @JsonProperty("endpoints")
    public List<Mapping> mappings = new LinkedList<>();
}
