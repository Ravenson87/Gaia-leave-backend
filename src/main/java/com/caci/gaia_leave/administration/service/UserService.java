package com.caci.gaia_leave.administration.service;

import com.caci.gaia_leave.administration.model.request.User;
import com.caci.gaia_leave.administration.model.response.UserResponse;
import com.caci.gaia_leave.administration.repository.request.UserRepository;
import com.caci.gaia_leave.administration.repository.response.UserResponseRepository;
import com.caci.gaia_leave.shared_tools.component.AppConst;
import com.caci.gaia_leave.shared_tools.component.ImageHandler;
import com.caci.gaia_leave.shared_tools.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.flywaydb.core.internal.resource.classpath.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.caci.gaia_leave.shared_tools.component.AppConst.LINK_EXPIRATION_TIME;
import static com.caci.gaia_leave.shared_tools.component.AppConst.REFRESH_TOKEN_EXPIRATION_TIME;
import static com.caci.gaia_leave.shared_tools.helper.AllHelpers.listConverter;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserResponseRepository userResponseRepository;
    private final ImageHandler imageHandler;
    private final ResourceLoader resourceLoader;
    private final MailService mailService;

    public ResponseEntity<String> create(User model) {

        if (userResponseRepository.existsByUsername(model.getUsername())) {
            throw new CustomException("Username already taken");
        }

        if (userResponseRepository.existsByEmail(model.getEmail())) {
            throw new CustomException("Email already taken");
        }

        try {
            Date now = new Date();
            Date newExpiryDate = DateUtils.addDays(now, LINK_EXPIRATION_TIME);
            String hash = DigestUtils.md5Hex(model.getUsername() + model.getEmail());
            model.setHash(hash);
            model.setLinkExpired(newExpiryDate);
            String template = readFileAsString("registration_email.html");
            System.out.println("Pre" + model.getUsername() + " " + model.getFirstName());
            template = template.replace("{{userName}}", model.getUsername())
                    .replace("{{firstName}}", model.getFirstName())
                    .replace("{{lastName}}", model.getLastName())
                    .replace("{{registrationForm}}", "https://gaia.softmetrixgroup.com/#/registartion?h=" + hash);
            userRepository.save(model);
            mailService.sendEmail(model.getEmail(), "Activate your GaiaLeave account", template, null);
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

    public ResponseEntity<String> update(Integer id, Integer roleId, Integer jobPositionId,
                                         String firstName, String lastName, String email, String username, Boolean status) {

        if (!userRepository.existsById(id)) {
            throw new CustomException("User with id `" + id + "` does not exist.");
        }

        Optional<UserResponse> uniqueUsername = userResponseRepository.findByUsername(username);
        if (uniqueUsername.isPresent() && !uniqueUsername.get().getId().equals(id)) {
            throw new CustomException("Username `" + username + " already exists.");
        }
        Optional<UserResponse> uniqueEmail = userResponseRepository.findByEmail(email);
        if (uniqueEmail.isPresent() && !uniqueEmail.get().getId().equals(id)) {
            throw new CustomException("Email `" + email + " already exists.");
        }
        try {
            User model = userRepository.findById(id).get();
            model.setId(id);
            model.setRoleId(roleId);
            model.setJobPositionId(jobPositionId);
            model.setFirstName(firstName);
            model.setLastName(lastName);
            model.setEmail(email);
            model.setUsername(username);
            model.setStatus(status);
            userRepository.save(model);
            return ResponseEntity.ok().body("Successfully updated");
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

    public ResponseEntity<Boolean> checkLinkExpired(String hash) {
        Optional<UserResponse> data = userResponseRepository.findByHash(hash);

        if (data.isEmpty()) {
            throw new CustomException("User does not exist.");
        }
        Date linkedExpired = data.get().getLinkExpired();
        Date now = new Date();

        return ResponseEntity.ok().body(linkedExpired.after(now));
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

    private String readFileAsString(String fileName) {
        Resource resource = resourceLoader.getResource("classpath:static/" + fileName);

        try (InputStream stream = resource.getInputStream()) {
            return new String(stream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
    }
}
