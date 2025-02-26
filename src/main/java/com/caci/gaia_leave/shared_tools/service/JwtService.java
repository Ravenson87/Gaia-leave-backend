package com.caci.gaia_leave.shared_tools.service;

import com.caci.gaia_leave.administration.model.response.UserResponse;
import com.caci.gaia_leave.shared_tools.configuration.AppProperties;
import com.caci.gaia_leave.shared_tools.exception.BadToken;
import com.caci.gaia_leave.shared_tools.exception.TokenExpired;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;


import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import static com.caci.gaia_leave.shared_tools.component.AppConst.EXPIRATION_TIME;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtService {

    private static final String HEADER_STRING = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";
    public static final String SUPER_ADMIN = "super_admin";
    private final AppProperties appProperties;



    public String generateToken(UserResponse model) {
        Date now = new Date();
        Date expiryDate = DateUtils.addHours(now, EXPIRATION_TIME);

        SecretKey secretKey = Keys.hmacShaKeyFor(appProperties.getJwtSecret().getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .header().add("typ", "JWT")
                .and()
                .claims(createClaims(model))
                .subject("gaia")
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(secretKey)
                .compact();
    }

    public String generateRefreshToken() {
        Instant now = Instant.now();
        Long unixTime = now.getEpochSecond();
        return DigestUtils.md5Hex(UUID.randomUUID() + "-" + unixTime);
    }

    private Map<String, Object> createClaims(UserResponse model) {
        return Map.of(
                "id", model.getId(),
                "role_id", model.getRoleId(),
                "role", model.getRole().getName(),
                "job_position_id", model.getJobPositionId(),
                "first_name", model.getFirstName(),
                "last_name", model.getLastName(),
                "email", model.getEmail()

        );
    }

    public Claims jwsToken(HttpServletRequest request) {
        SecretKey secretKey = Keys.hmacShaKeyFor(appProperties.getJwtSecret().getBytes());
        Claims jws;
        try {
            jws = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(getBearer(request))
                    .getPayload();
        } catch (ExpiredJwtException e) {
            throw new TokenExpired(e.getMessage());
        } catch (JwtException | IllegalArgumentException ex) {
            throw new BadToken(ex.getMessage());
        }
        return jws;
    }


    private String getBearer(HttpServletRequest request) {
        String authTokenHeader = request.getHeader(HEADER_STRING);
        if (authTokenHeader == null || authTokenHeader.trim().isEmpty()) {
            throw new BadToken("Bearer token missing");
        }
        return authTokenHeader.replace(TOKEN_PREFIX, "");
    }

    public String getFullName(HttpServletRequest request) {
        return getValue(request, "first_name") + " " +  getValue(request, "last_name");

    }

    public Integer getRoleId(HttpServletRequest request) {
        return Integer.parseInt(getValue(request,"role_id"));

    }

    private String getValue(HttpServletRequest request, String key) {
        return jwsToken(request).get(key).toString();
    }
    public boolean superAdmin(HttpServletRequest request) {
        return getValue(request, "role").equals(SUPER_ADMIN);
    }


}
