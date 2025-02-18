package com.caci.gaia_leave.shared_tools.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.OffsetDateTime;

@Getter(AccessLevel.PROTECTED)
@Setter(AccessLevel.PROTECTED)
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Auditable {

    @JsonIgnore
    @CreatedBy
    @Column(name = "created_by", nullable = false, updatable = false)
    private String createdBy;

    @JsonIgnore
    @LastModifiedBy
    @Column(name = "last_modified_by", nullable = false)
    private String lastModifiedBy;

    @JsonIgnore
    @CreatedDate
    @Column(name = "created_date", nullable = false, updatable = false)
    private OffsetDateTime createdDate;

    @JsonIgnore
    @LastModifiedDate
    @Column(name = "last_modified_date", nullable = false)
    private OffsetDateTime lastModifiedDate;
}