package com.caci.gaia_leave.administration.service;

import com.caci.gaia_leave.administration.model.request.User;
import com.caci.gaia_leave.administration.model.request.UserDocuments;
import com.caci.gaia_leave.administration.model.response.UserDocumentsResponse;
import com.caci.gaia_leave.administration.repository.request.UserDocumentsRepository;
import com.caci.gaia_leave.administration.repository.request.UserRepository;
import com.caci.gaia_leave.administration.repository.response.UserDocumentsResponseRepository;
import com.caci.gaia_leave.shared_tools.component.AppConst;
import com.caci.gaia_leave.shared_tools.component.DocumentHandler;
import com.caci.gaia_leave.shared_tools.exception.CustomException;
import com.caci.gaia_leave.shared_tools.helper.AllHelpers;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDocumentsService {

    private final UserDocumentsRepository userDocumentsRepository;
    private final UserDocumentsResponseRepository userDocumentsResponseRepository;
    private final UserRepository userRepository;
    private final DocumentHandler documentHandler;

    public ResponseEntity<UserDocuments> create(UserDocuments userDocuments) {
        if(!userRepository.existsById(userDocuments.getId())) {
            throw new CustomException("User with id `" + userDocuments.getId() + "` does not exist.");
        }
        try{
            UserDocuments save = userDocumentsRepository.save(userDocuments);
            Optional<UserDocuments> result = userDocumentsRepository.findById(save.getId());
            return result
                    .map(response -> ResponseEntity.status(HttpStatus.CREATED).body(response))
                    .orElseGet(() -> ResponseEntity.noContent().build());

        }catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
    }

    public ResponseEntity<List<UserDocumentsResponse>> read(){
        List<UserDocumentsResponse> result = AllHelpers.listConverter(userDocumentsResponseRepository.findAll());
        return result.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok().body(result);
    }

    public ResponseEntity<UserDocumentsResponse> readById(Integer id){
        Optional<UserDocumentsResponse> result = userDocumentsResponseRepository.findById(id);
        return result
                .map(response -> ResponseEntity.ok().body(response))
                .orElseGet(() -> ResponseEntity.noContent().build());

    }

    public ResponseEntity<String> update(Integer id, UserDocuments model){
        if(!userDocumentsResponseRepository.existsById(id)){
            throw new CustomException("Document does not exist.");
        }
        if(!userRepository.existsById(model.getId())) {
            throw new CustomException("User with id `" + model.getId() + "` does not exist.");
        }
        try{
            model.setId(id);
            userDocumentsRepository.save(model);
            return ResponseEntity.ok().body("Successfully update");
        }catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
    }

    public ResponseEntity<HttpStatus> delete(Integer id){
        if(!userDocumentsResponseRepository.existsById(id)){
            throw new CustomException("Document does not exist.");
        }
        try {
            userDocumentsRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }

    }

    public ResponseEntity<String> uploadDocument(MultipartFile file, Integer userId, Integer documentId) {
        if (!userRepository.existsById(userId)) {
            throw new CustomException("User with id `" + userId + "` does not exist.");
        }

        String filePath = documentHandler.storeDocument("user_document", file, AppConst.DOCUMENT_TYPE);

       Optional<UserDocuments> result = userDocumentsRepository.findById(documentId);
       if (result.isEmpty()) {
           throw new CustomException("Document does not exist.");
       }
        try{
        result.get().setId(documentId);
        result.get().setDocumentPath(filePath);
        userDocumentsRepository.save(result.get());
        }catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
        return ResponseEntity.ok().body("Document successfully uploaded");
    }

}
