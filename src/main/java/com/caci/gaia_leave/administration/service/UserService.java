package com.caci.gaia_leave.administration.service;

import com.caci.gaia_leave.administration.model.request.User;
import com.caci.gaia_leave.administration.model.response.UserResponse;
import com.caci.gaia_leave.administration.repository.request.UserRepository;
import com.caci.gaia_leave.administration.repository.response.UserResponseRepository;
import com.caci.gaia_leave.shared_tools.component.AppConst;
import com.caci.gaia_leave.shared_tools.component.ImageHandler;
import com.caci.gaia_leave.shared_tools.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.util.*;

import static com.caci.gaia_leave.shared_tools.component.AppConst.PROFILE_IMG_MAX_SIZE;
import static com.caci.gaia_leave.shared_tools.helper.AllHelpers.listConverter;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserResponseRepository userResponseRepository;
    private final ImageHandler imageHandler;


    public ResponseEntity<String> create(User model) {

        if (userResponseRepository.existsByUsername(model.getUsername())) {
            throw new CustomException("Username already taken");
        }

        if (userResponseRepository.existsByEmail(model.getEmail())) {
            throw new CustomException("Email already taken");
        }

        try {
            String password = BCrypt.hashpw(model.getPassword(), BCrypt.gensalt());
            model.setPassword(password);
            userRepository.save(model);
            return ResponseEntity.status(HttpStatus.CREATED).body("Successfully created");
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }

    }

    public ResponseEntity<List<UserResponse>> read() {
        List<UserResponse> result = listConverter(userResponseRepository.findByIdGreaterThan(1));
        return result.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok().body(result);
    }

    public ResponseEntity<UserResponse> readById(Integer id) {

        if (id == 1) {
            throw new CustomException("Does not exist");
        }

        Optional<UserResponse> result = userResponseRepository.findById(id);

        return result
                .map(response -> ResponseEntity.ok().body(response))
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    public ResponseEntity<String> update(Integer id, User model) {
        if (!userRepository.existsById(id)) {
            throw new CustomException("User with id `" + id + "` does not exist.");
        }

        Optional<UserResponse> uniqueUsername = userResponseRepository.findByUsername(model.getUsername());
        if (uniqueUsername.isPresent() && !uniqueUsername.get().getId().equals(id)) {
            throw new CustomException("Username `" + model.getUsername() + " already exists.");
        }
        Optional<UserResponse> uniqueEmail = userResponseRepository.findByEmail(model.getEmail());
        if (uniqueEmail.isPresent() && !uniqueEmail.get().getId().equals(id)) {
            throw new CustomException("Email `" + model.getEmail() + " already exists.");
        }
        try {
            model.setId(id);
            model.setPassword(BCrypt.hashpw(model.getPassword(), BCrypt.gensalt()));
            userRepository.save(model);
            return ResponseEntity.ok().body("Successfully updated");
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
    }

    public ResponseEntity<HttpStatus> delete(Integer id) {
        if (!userRepository.existsById(id)) {
            throw new CustomException("User with id `" + id + "` does not exist.");
        }
        try {
            userRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
    }

    public ResponseEntity<String> updatePassword(Integer id, String oldPassword, String newPassword) {
        if (!userRepository.existsById(id)) {
            throw new CustomException("User with id `" + id + "` does not exist.");
        }
        User user = userRepository.findById(id).get();
        boolean checkPassword = BCrypt.checkpw(oldPassword, user.getPassword());
        if (!checkPassword) {
            throw new CustomException("Wrong old password.");
        }

        try {
            user.setPassword(BCrypt.hashpw(newPassword, BCrypt.gensalt()));
            userRepository.save(user);
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
        return ResponseEntity.ok().body("Successfully updated password");
    }

    public ResponseEntity<String> updateStatus(Integer id, Boolean status) {
        if (!userRepository.existsById(id)) {
            throw new CustomException("User with id `" + id + "` does not exist.");
        }
        User user = userRepository.findById(id).get();
        try {
            user.setStatus(status);
            userRepository.save(user);
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
        return ResponseEntity.ok().body("Successfully updated status");
    }

    public ResponseEntity<String> uploadImage(MultipartFile file, Integer userId) {

        Optional<User> imageUser = userRepository.findById(userId);
        if (imageUser.isEmpty()) {
            throw new CustomException("User with id `" + userId + "` does not exist.");
        }

        String filePath = imageHandler.storeImage("profile_image", file, AppConst.IMAGE_TYPE);

        try {
            imageUser.get().setProfileImage(filePath);
            userRepository.save(imageUser.get());
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }


        return ResponseEntity.ok().body("Image uploaded");

    }




}
