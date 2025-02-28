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
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@JsonInclude(JsonInclude.Include.USE_DEFAULTS)
@Table(name = "role")
public class RoleResponse implements Serializable {

    @Id
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @JsonProperty("id")
    @Column(name = "id")
    private Integer id;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("name")
    @Column(name = "name")
    private String name;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("description")
    @Column(name = "description")
    private String description;

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

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "role_id", referencedColumnName = "id")
    @Column(insertable = false,
            updatable = false)
    private List<RoleEndpointResponse> roleEndpoints;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "role_id", referencedColumnName = "id")
    @Column(insertable = false,
            updatable = false)
    private List<RoleMenuResponse> roleMenus;


}
