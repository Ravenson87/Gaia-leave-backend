package com.caci.gaia_leave.administration.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@JsonInclude(JsonInclude.Include.USE_DEFAULTS)
@Table(name = "user_documents")
public class UserDocuments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @JsonProperty("id")
    @Column(name = "id")
    private Integer id;

    @NotNull(message = "user_id cannot be empty")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @JsonProperty("user_id")
    @Column(name = "user_id")
    private Integer userId;

    @NotEmpty(message = "document_path cannot be empty")
    @NotNull(message = "document_path cannot be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("document_path")
    @Column(name = "document_path")
    private String documentPath;
}
