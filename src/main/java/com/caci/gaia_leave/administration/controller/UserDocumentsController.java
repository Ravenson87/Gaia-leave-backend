package com.caci.gaia_leave.administration.controller;

import com.caci.gaia_leave.administration.model.request.UserDocuments;
import com.caci.gaia_leave.administration.model.response.UserDocumentsResponse;
import com.caci.gaia_leave.administration.service.UserDocumentsService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@Validated
@RequestMapping("/api/v1/user-documents")
@RequiredArgsConstructor
public class UserDocumentsController {

    private final UserDocumentsService userDocumentsService;

    @PostMapping("/create")
    public ResponseEntity<UserDocuments> create(
            @Valid
            @RequestBody
            @NotNull(message = "Model can not be null")
            UserDocuments model,
            @Valid
            @RequestParam("file")
            @NotNull(message = "File must be provided")
            MultipartFile file
    ) {
        return userDocumentsService.create(model, file);
    }

    @GetMapping("/read")
    public ResponseEntity<List<UserDocumentsResponse>> read() {

        return userDocumentsService.read();
    }

    @GetMapping("/read-by-id")
    public ResponseEntity<UserDocumentsResponse> readById(
            @Valid
            @RequestParam("id")
            @NotNull(message = "Id can not be null")
            @Min(value = 1, message = "Id can not be less than zero")
            Integer id
    ) {
        return userDocumentsService.readById(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(
            @Valid
            @PathVariable("id")
            @NotNull(message = "Id can not be null")
            @Min(value = 1, message = "Id can not be less than zero")
            Integer id,
            @RequestBody
            @NotNull(message = "Model can not be null")
            UserDocuments model
    ) {
        return userDocumentsService.update(id, model);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<HttpStatus> deleteById(
            @Valid
            @RequestParam("id")
            @NotNull(message = "Id can not be null")
            @Min(value = 1, message = "Id can not be less than zero")
            Integer id
    ) {
        return userDocumentsService.delete(id);
    }

//    @PostMapping("/upload-document")
//    public ResponseEntity<String> uploadDocument(
//            @Valid
//            @RequestParam("file")
//            @NotNull
//            MultipartFile file,
//            @RequestParam("user_id")
//            @NotNull
//            @Min(value = 1, message = "User id can not be less than zero")
//            Integer userId,
//            @RequestParam("document_id")
//            @NotNull
//            @Min(value = 1, message = "Document id can not be less than zero")
//            Integer documentId
//
//    ){
//        return userDocumentsService.uploadDocument(file, userId, documentId);
//    }
}
