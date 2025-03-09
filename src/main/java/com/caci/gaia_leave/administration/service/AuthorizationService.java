package com.caci.gaia_leave.administration.service;

import com.caci.gaia_leave.administration.model.request.RefreshToken;
import com.caci.gaia_leave.administration.model.response.UserResponse;
import com.caci.gaia_leave.administration.repository.request.RefreshTokenRepository;
import com.caci.gaia_leave.administration.repository.response.UserResponseRepository;
import com.caci.gaia_leave.shared_tools.exception.CustomException;
import com.caci.gaia_leave.shared_tools.model.AuthorizationTokenDTO;
import com.caci.gaia_leave.shared_tools.service.JwtService;
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
    private final JwtService jwtService;
    private final RefreshTokenRepository refreshTokenRepository;

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
}
