package com.caci.gaia_leave.administration.service;

import com.caci.gaia_leave.administration.model.dto.UserUpdateDTO;
import com.caci.gaia_leave.administration.model.request.User;
import com.caci.gaia_leave.administration.model.response.UserResponse;
import com.caci.gaia_leave.administration.repository.request.UserRepository;
import com.caci.gaia_leave.administration.repository.response.UserResponseRepository;
import com.caci.gaia_leave.shared_tools.component.AppConst;
import com.caci.gaia_leave.shared_tools.component.ImageHandler;
import com.caci.gaia_leave.shared_tools.configuration.AppProperties;
import com.caci.gaia_leave.shared_tools.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.caci.gaia_leave.shared_tools.component.AppConst.LINK_EXPIRATION_TIME;
import static com.caci.gaia_leave.shared_tools.helper.AllHelpers.getOrthodoxEasterDate;
import static com.caci.gaia_leave.shared_tools.helper.AllHelpers.listConverter;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserResponseRepository userResponseRepository;
    private final ImageHandler imageHandler;
    private final ResourceLoader resourceLoader;
    private final MailService mailService;
    private final AppProperties appProperties;
    SSEService sseService;

    /**
     * Create User in database
     *
     * @param model User
     * @return ResponseEntity<String>
     */

    @Transactional
    public ResponseEntity<String> create(User model) {

        if (userResponseRepository.existsByUsername(model.getUsername())) {
            throw new CustomException("Username already taken");
        }

        if (userResponseRepository.existsByEmail(model.getEmail())) {
            throw new CustomException("Email already taken");
        }

        try {
            Date now = new Date();
            // Set expire date for registration link
            Date newExpiryDate = DateUtils.addDays(now, LINK_EXPIRATION_TIME);
            // Set unique hash for user
            String hash = DigestUtils.md5Hex(model.getUsername() + model.getEmail());
            model.setHash(hash);
            model.setLinkExpired(newExpiryDate);
            // Set template for registration email and sending registration email
            // TODO pitaj sta tacno radimo sa ovim registration_email.html
            String template = readFileAsString("registration_email.html");
            template = template.replace("{{userName}}", model.getUsername())
                    .replace("{{firstName}}", model.getFirstName())
                    .replace("{{lastName}}", model.getLastName())
                    .replace("{{registrationForm}}", "https://freedays.softmetrixgroup.com/#/registartion?h=" + hash);
            userRepository.save(model);
            mailService.sendEmail(model.getEmail(), "Activate your GaiaLeave account", template, null);
            return ResponseEntity.status(HttpStatus.CREATED).body("Successfully created");
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }

    }

    /**
     * Read user from database
     *
     * @return ResponseEntity<List<UserResponse>>
     */
    public ResponseEntity<List<UserResponse>> read() {
        List<UserResponse> result = listConverter(userResponseRepository.findByIdGreaterThan(1));
        return result.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok().body(result);
    }

    /**
     * Read User by id from database
     *
     * @param id Integer
     * @return ResponseEntity<UserResponse>
     */
    public ResponseEntity<UserResponse> readById(Integer id) {

        if (id == 1) {
            throw new CustomException("Does not exist");
        }

        Optional<UserResponse> result = userResponseRepository.findById(id);

        return result
                .map(response -> ResponseEntity.ok().body(response))
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    /**
     * Update User in database
     *
     * @param model UserUpdateDTO
     * @return ResponseEntity<String>
     */
    public ResponseEntity<String> update(UserUpdateDTO model) {

        if (!userRepository.existsById(model.getId())) {
            throw new CustomException("User with id `" + model.getId() + "` does not exist.");
        }

        Optional<UserResponse> uniqueUsername = userResponseRepository.findByUsername(model.getUsername());
        if (uniqueUsername.isPresent() && !uniqueUsername.get().getId().equals(model.getId())) {
            throw new CustomException("Username `" + model.getUsername() + " already exists.");
        }
        Optional<UserResponse> uniqueEmail = userResponseRepository.findByEmail(model.getEmail());
        if (uniqueEmail.isPresent() && !uniqueEmail.get().getId().equals(model.getId())) {
            throw new CustomException("Email `" + model.getEmail() + " already exists.");
        }
        try {
            User userModel = userRepository.findById(model.getId()).get();
            userModel.setId(model.getId());
            userModel.setRoleId(model.getRoleId());
            userModel.setJobPositionId(model.getJobPositionId());
            userModel.setFirstName(model.getFirstName());
            userModel.setLastName(model.getLastName());
            userModel.setEmail(model.getEmail());
            userModel.setUsername(model.getUsername());
            userModel.setStatus(model.getStatus());
            userModel.setDateOfBirth(model.getDateOfBirth());
            userModel.setPhone(model.getPhone());
            userModel.setProfileImage(model.getProfileImage());
            userModel.setHash(DigestUtils.md5Hex(model.getUsername() + model.getEmail()));

            userRepository.save(userModel);
            return ResponseEntity.ok().body("Successfully updated");
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
    }

    /**
     * Update user password in database
     *
     * @param id Integer
     * @param oldPassword Integer
     * @param newPassword Integer
     * @return ResponseEntity<String>
     */
    public ResponseEntity<String> updatePassword(Integer id, String oldPassword, String newPassword) {
        if (!userRepository.existsById(id)) {
            throw new CustomException("User with id `" + id + "` does not exist.");
        }
        User user = userRepository.findById(id).get();

        // Check old password
        boolean checkPassword = BCrypt.checkpw(oldPassword, user.getPassword());
        if (!checkPassword) {
            throw new CustomException("Wrong old password.");
        }

        // Set new password
        try {
            user.setPassword(BCrypt.hashpw(newPassword, BCrypt.gensalt()));
            userRepository.save(user);
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
        return ResponseEntity.ok().body("Successfully updated password");
    }

    /**
     * Update user status in database
     *
     * @param id Integer
     * @param status Boolean
     * @return ResponseEntity<String>
     */
    public ResponseEntity<String> updateStatus(Integer id, Boolean status) {
        if (!userRepository.existsById(id)) {
            throw new CustomException("User with id `" + id + "` does not exist.");
        }

        if (appProperties.getUserId().equals(id)) {
            throw new CustomException("User cannot change the status of their own account.");
        }

        User user = userRepository.findById(id).get();
        try {
            user.setStatus(status);
            userRepository.save(user);
            if (!status) {
                sseService.updateStatus(String.valueOf(id));
            }

        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
        return ResponseEntity.ok().body("Successfully updated status");
    }

    /**
     * Upload user Image in database
     *
     * @param file MultipartFile
     * @param userId Integer
     * @return Integer
     */
    public ResponseEntity<String> uploadImage(MultipartFile file, Integer userId) {

        Optional<User> imageUser = userRepository.findById(userId);
        if (imageUser.isEmpty()) {
            throw new CustomException("User with id `" + userId + "` does not exist.");
        }

        // Save image and find image path
        String filePath = imageHandler.storeImage("profile_image", file, AppConst.IMAGE_TYPE);

        try {
            // Save image path in database
            imageUser.get().setProfileImage(filePath);
            userRepository.save(imageUser.get());
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }


        return ResponseEntity.ok().body(filePath);

    }

    /**
     * Check if link for registration is expired or not
     *
     * @param hash String
     * @return ResponseEntity<Boolean>
     */
    public ResponseEntity<Boolean> checkLinkExpired(String hash) {
        Optional<UserResponse> data = userResponseRepository.findByHash(hash);

        if (data.isEmpty()) {
            throw new CustomException("User does not exist.");
        }
        if(data.get().getVerified()){
            return ResponseEntity.ok(false);
        }
        Date linkedExpired = data.get().getLinkExpired();
        Date now = new Date();

        return ResponseEntity.ok().body(linkedExpired.after(now));
    }

    /**
     * Delete User by id from database
     *
     * @param id Integer
     * @return ResponseEntity<HttpStatus>
     */
    public ResponseEntity<HttpStatus> delete(Integer id) {
        if (!userRepository.existsById(id)) {
            throw new CustomException("User with id `" + id + "` does not exist.");
        }

        if (appProperties.getUserId().equals(id)) {
            throw new CustomException("User cannot delete their own account.");
        }

        try {
            userRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
    }

    /**
     * Read file as string
     *
     * @param fileName String
     * @return String
     */
    private String readFileAsString(String fileName) {
        // TODO Pitaj sta tacno radi ovaj Resource i kakav je to interfejs-objekat
        Resource resource = resourceLoader.getResource("classpath:static/" + fileName);

        // Transform file into string (char array)
        try (InputStream stream = resource.getInputStream()) {
            return new String(stream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
    }
}
