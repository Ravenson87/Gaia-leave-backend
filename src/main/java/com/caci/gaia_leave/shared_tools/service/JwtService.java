package com.caci.gaia_leave.shared_tools.service;

import com.caci.gaia_leave.administration.model.response.UserResponse;
import com.caci.gaia_leave.shared_tools.configuration.AppProperties;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;


import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import static com.caci.gaia_leave.shared_tools.component.AppConst.EXPIRATION_TIME;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtService {

    private final AppProperties appProperties;


    public String generateToken(UserResponse model) {
        Date now = new Date();
        Date expiryDate = DateUtils.addHours(now, EXPIRATION_TIME);

        SecretKey secretKey = Keys.hmacShaKeyFor(appProperties.getJwtSecret().getBytes(StandardCharsets.UTF_8));

        return Jwts
                .builder()
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
                "job_position_id", model.getJobPositionId(),
                "first_name", model.getFirstName(),
                "last_name", model.getLastName(),
                "email", model.getEmail()
        );
    }
}
