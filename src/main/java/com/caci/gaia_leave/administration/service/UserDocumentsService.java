package com.caci.gaia_leave.administration.service;

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

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDocumentsService {

    private final UserDocumentsRepository userDocumentsRepository;
    private final UserDocumentsResponseRepository userDocumentsResponseRepository;
    private final UserRepository userRepository;
    private final DocumentHandler documentHandler;

    /**
     * Create UserDocuments in database
     *
     * @param userId Integer
     * @param file MultipartFile
     * @return ResponseEntity<UserDocuments>
     */
    public ResponseEntity<UserDocuments> create(Integer userId, MultipartFile file) {
        if(!userRepository.existsById(userId)){
            throw new CustomException("User with id `" + userId + "` does not exist.");
        }

        // Get file path for document
        String filePath = documentHandler.storeDocument("user_document", file, AppConst.DOCUMENT_TYPE);

        // Set user document, save it in database and check is it saved
        try{
            UserDocuments userDocuments = new UserDocuments();
            userDocuments.setUserId(userId);
            userDocuments.setDocumentPath(filePath);
            UserDocuments save = userDocumentsRepository.save(userDocuments);
            Optional<UserDocuments> result = userDocumentsRepository.findById(save.getId());
            return result
                    .map(response -> ResponseEntity.status(HttpStatus.CREATED).body(response))
                    .orElseGet(() -> ResponseEntity.noContent().build());

        }catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
    }

    /**
     * Read UserDocuments from database
     *
     * @return ResponseEntity<List<UserDocumentsResponse>>
     */
    public ResponseEntity<List<UserDocumentsResponse>> read(){
        List<UserDocumentsResponse> result = AllHelpers.listConverter(userDocumentsResponseRepository.findAll());
        return result.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok().body(result);
    }

    /**
     * Read UserDocuments by id from database
     *
     * @param id Integer
     * @return ResponseEntity<UserDocumentsResponse>
     */
    public ResponseEntity<UserDocumentsResponse> readById(Integer id){
        Optional<UserDocumentsResponse> result = userDocumentsResponseRepository.findById(id);
        return result
                .map(response -> ResponseEntity.ok().body(response))
                .orElseGet(() -> ResponseEntity.noContent().build());

    }

    /**
     * Update UserDocuments in database
     *
     * @param id Integer
     * @param file MultipartFile
     * @return ResponseEntity<String>
     */
    public ResponseEntity<String> update(Integer id, MultipartFile file){
        if(!userDocumentsResponseRepository.existsById(id)){
            throw new CustomException("Document does not exist.");
        }


        try{
            // Find path of file that has to be updated
            UserDocuments model = userDocumentsRepository.findById(id).get();
            String pathToDeleteString = model.getDocumentPath();

            // Delete file that is updated
            Path pathToDelete = Paths.get(pathToDeleteString);
            Files.deleteIfExists(pathToDelete);

            // Save file and find path where file is stored
            String filePath = documentHandler.storeDocument("user_document", file, AppConst.DOCUMENT_TYPE);

            // Save file path in database
            model.setId(id);
            model.setDocumentPath(filePath);
            userDocumentsRepository.save(model);
            return ResponseEntity.ok().body("Successfully update");
        }catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
    }

    /**
     * Delete UserDocuments by id from database
     *
     * @param id Integer
     * @return ResponseEntity<HttpStatus>
     */
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

//    public ResponseEntity<String> uploadDocument(MultipartFile file, Integer userId) {
//        if (!userRepository.existsById(userId)) {
//            throw new CustomException("User with id `" + userId + "` does not exist.");
//        }
//
//        String filePath = documentHandler.storeDocument("user_document", file, AppConst.DOCUMENT_TYPE);
//
//
//        try{
//
//
//        }catch (Exception e) {
//            throw new CustomException(e.getMessage());
//        }
//        return ResponseEntity.ok().body("Document successfully uploaded");
//    }

}
