package com.caci.gaia_leave.administration.service;

import com.caci.gaia_leave.administration.model.request.RefreshToken;
import com.caci.gaia_leave.administration.model.response.UserResponse;
import com.caci.gaia_leave.administration.repository.request.RefreshTokenRepository;
import com.caci.gaia_leave.administration.repository.response.UserResponseRepository;
import com.caci.gaia_leave.shared_tools.exception.CustomException;
import com.caci.gaia_leave.shared_tools.model.AuthorizationTokenDTO;
import com.caci.gaia_leave.shared_tools.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorizationService {

    private final UserResponseRepository userResponseRepository;
    private final JwtService jwtService;
    private final RefreshTokenRepository refreshTokenRepository;

    public ResponseEntity<AuthorizationTokenDTO> login(String user, String password) {
        System.out.println("login: " + user + ", " + password);
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

        try {
            RefreshToken refreshTokenDTO = new RefreshToken();
            refreshTokenDTO.setId(res.get().getId());
            refreshTokenDTO.setRefreshToken(refreshToken);
            refreshTokenRepository.save(refreshTokenDTO);
        } catch (CustomException e) {
            throw new CustomException(e.getMessage());
        }

        return ResponseEntity.ok().body(authorizationTokenDTO);
    }
}
