package com.caci.gaia_leave.administration.service;

import com.caci.gaia_leave.administration.model.response.UserResponse;
import com.caci.gaia_leave.administration.repository.response.UserResponseRepository;
import com.caci.gaia_leave.shared_tools.exception.CustomException;
import com.caci.gaia_leave.shared_tools.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorizationService {

    private final UserResponseRepository userResponseRepository;
    private final JwtService jwtService;

    public String login(String user, String password) {
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

        System.out.println("refreshToken: " + refreshToken);

        return null;
    }
}
