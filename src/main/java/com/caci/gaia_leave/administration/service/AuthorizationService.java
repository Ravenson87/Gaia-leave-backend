package com.caci.gaia_leave.administration.service;

import com.caci.gaia_leave.administration.model.request.*;
import com.caci.gaia_leave.administration.model.response.CalendarResponse;
import com.caci.gaia_leave.administration.model.response.UserResponse;
import com.caci.gaia_leave.administration.repository.request.*;
import com.caci.gaia_leave.administration.repository.response.UserResponseRepository;
import com.caci.gaia_leave.shared_tools.exception.CustomException;
import com.caci.gaia_leave.shared_tools.model.AuthorizationTokenDTO;
import com.caci.gaia_leave.shared_tools.service.JwtService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

import static com.caci.gaia_leave.shared_tools.component.AppConst.REFRESH_TOKEN_EXPIRATION_TIME;

@Service
@RequiredArgsConstructor
public class AuthorizationService {

    private final UserResponseRepository userResponseRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final FreeDayTypeRepository freeDayTypeRepository;
    private final UserUsedFreeDaysRepository userUsedFreeDaysRepository;
    private final CalendarRepository calendarRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public ResponseEntity<AuthorizationTokenDTO> login(String user, String password) {
        Optional<UserResponse> res = userResponseRepository.findByUsernameAndStatusIsTrueOrEmailAndStatusIsTrue(user, user);

        if (res.isEmpty()) {
            throw new CustomException("Wrong credentials.");
        }

        boolean checkPassword = BCrypt.checkpw(password, res.get().getPassword());

        if (!checkPassword) {
            throw new CustomException("Wrong credentials.");
        }

        String token = jwtService.generateToken(res.get());
        String refreshToken = jwtService.generateRefreshToken();

        AuthorizationTokenDTO authorizationTokenDTO = new AuthorizationTokenDTO();
        authorizationTokenDTO.setToken(token);
        authorizationTokenDTO.setRefreshToken(refreshToken);

        Date now = new Date();
        Date newExpiryDate = DateUtils.addHours(now, REFRESH_TOKEN_EXPIRATION_TIME);

        try {
            RefreshToken refreshTokenDTO = new RefreshToken();
            refreshTokenDTO.setId(res.get().getId());
            refreshTokenDTO.setRefreshToken(refreshToken);
            refreshTokenDTO.setRefreshTokenExpireTime(newExpiryDate);
            refreshTokenRepository.save(refreshTokenDTO);
        } catch (CustomException e) {
            throw new CustomException(e.getMessage());
        }


        return ResponseEntity.ok().body(authorizationTokenDTO);
    }

    /**
     * 1. Dobijamo Token i RefreshToken
     * 2. Ulazni paramtera nam je refreshTokne
     * 3. Pronalazimo User-a uz pomoc refreshToken-a
     * 4. Proveravamo da li postoji user sa tim refreshToken-om i statusom koji je true.
     * 5. Ako ne postoji user ili ako mu status nije true, vracamo gresku
     * 6. Proveravamo da li je token istekao
     * 7. Ako jeste, vracamo gresku
     * 8. Ako nije, onda generisemo novi token i refreshToken
     * 9. Vracamo novi token i refreshToken iz AuthorisationTokenDTO
     *
     * @param refreshToken
     * @return
     */
    public ResponseEntity<AuthorizationTokenDTO> refreshToken(String refreshToken) {
        Optional<UserResponse> res = userResponseRepository.findByRefreshTokenAndStatusIsTrue(refreshToken);

        if (res.isEmpty()) {
            throw new CustomException("Wrong refresh token.");
        }
        Date expiryDate = res.get().getRefreshTokenExpireTime();
        Date now = new Date();
        Date newExpiryDate = DateUtils.addHours(now, REFRESH_TOKEN_EXPIRATION_TIME);

        if (expiryDate.before(now)) {
            throw new CustomException("Refresh token expired.");
        }
        String token = jwtService.generateToken(res.get());
        String newRefreshToken = jwtService.generateRefreshToken();

        AuthorizationTokenDTO authorizationTokenDTO = new AuthorizationTokenDTO();
        authorizationTokenDTO.setToken(token);
        authorizationTokenDTO.setRefreshToken(newRefreshToken);

        try {
            RefreshToken refreshTokenDTO = new RefreshToken();
            refreshTokenDTO.setId(res.get().getId());
            refreshTokenDTO.setRefreshToken(refreshToken);
            refreshTokenDTO.setRefreshTokenExpireTime(newExpiryDate);
            refreshTokenRepository.save(refreshTokenDTO);
        } catch (CustomException e) {

            throw new CustomException(e.getMessage());
        }

        return ResponseEntity.ok().body(authorizationTokenDTO);
    }

    @Transactional
    public ResponseEntity<String> validateUser(Integer userId, String password, Date dateOfBirth, String phone, Date religiousHoliday, String holidayDescription) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new CustomException("User not found.");
        }

        if (user.get().getVerified()) {
            throw new CustomException("User is already verified.");
        }

        try {
            updateUserForValidation(user, password, dateOfBirth, phone);
            if (religiousHoliday != null) {
                createPersonalHoliday(userId, religiousHoliday, holidayDescription);
            }
            return ResponseEntity.ok().body("Successfully updated");
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
    }

    private void updateUserForValidation(Optional<User> user, String password, Date dateOfBirth, String phone) {
        user.get().setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
        user.get().setDateOfBirth(dateOfBirth);
        user.get().setPhone(phone);
        user.get().setVerified(true);
        user.get().setStatus(true);
        userRepository.save(user.get());
    }

    private void createPersonalHoliday(Integer userId, Date religiousHoliday, String holidayDescription) {
        Integer freeDayTypeId;
        Optional<Calendar> findDate = calendarRepository.findByDate(religiousHoliday);
        Optional<FreeDayType> freeDayType = freeDayTypeRepository.findByDescriptionIgnoreCase(holidayDescription);

        if (findDate.isEmpty()) {
            throw new CustomException("Calendar date not found for religious holiday.");
        }

        try {
            if (freeDayType.isEmpty()) {
                freeDayTypeId = crateFreeDayType(holidayDescription);
            } else {
                freeDayTypeId = freeDayType.get().getId();
            }
            UserUsedFreeDays model = UserUsedFreeDaysModel(userId, freeDayTypeId, findDate.get().getId());
            userUsedFreeDaysRepository.save(model);
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
    }

    private Integer crateFreeDayType(String holidayDescription) {
        try {
            FreeDayType freeDayTypeDTO = new FreeDayType();
            freeDayTypeDTO.setType("personal_holiday");
            freeDayTypeDTO.setDescription(holidayDescription);
            FreeDayType save = freeDayTypeRepository.save(freeDayTypeDTO);
            return save.getId();
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
    }

    private UserUsedFreeDays UserUsedFreeDaysModel(Integer userId, Integer freeDayTypeId, Integer calendarId) {
        UserUsedFreeDays model = new UserUsedFreeDays();
        model.setUserId(userId);
        model.setFreeDayTypeId(freeDayTypeId);
        model.setCalendarId(calendarId);
        return model;
    }
}
