package com.caci.gaia_leave.shared_tools.token;

import com.caci.gaia_leave.shared_tools.configuration.AppProperties;
//import com.fenix.troter_admin.tools.exception.BadToken;
//import com.fenix.troter_admin.tools.exception.TokenExpired;
//import com.fenix.troter_admin.troter_admin_api.model.response.UserResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
//import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

//import static com.fenix.troter_admin.tools.component.AppConst.*;

@Service
@RequiredArgsConstructor
public class JwtServiceZoran {

//    private final AppProperties appProperties;
//
//    /**
//     * Generates a JWT token for the given user.
//     * <p>
//     * The token includes claims based on the user information and is signed using a secret key.
//     * The token's expiration is set based on the current time plus a predefined expiration period.
//     * </p>
//     *
//     * @param model a {@code UserResponse} containing the user's details.
//     * @return a signed JWT token as a {@code String}.
//     */
//    public String generateToken(UserResponse model) {
//        Date now = new Date();
//        Date expire = DateUtils.addHours(now, EXPIRATION_TIME);
//
//        SecretKey key = Keys.hmacShaKeyFor(appProperties.getJwtSecret().getBytes(StandardCharsets.UTF_8));
//        return Jwts.builder()
//                .header().add(HEADER_STRING_2, HEADER_VALUE_2)
//                .and()
//                .claims(createClaim(model))
//                .subject(TOKEN_SUBJECT)
//                .issuedAt(now)
//                .expiration(expire)
//                .signWith(key)
//                .compact();
//    }
//
//    /**
//     * Generates a refresh token.
//     * <p>
//     * The refresh token is created by combining a random UUID with the current timestamp,
//     * then hashing the result using MD5.
//     * </p>
//     *
//     * @return a newly generated refresh token as a {@code String}.
//     */
//    public String generateRefreshToken() {
//        Instant instant = Instant.now();
//        long unixTimeInMillis = instant.toEpochMilli();
//        return DigestUtils.md5Hex(UUID.randomUUID() + "-" + unixTimeInMillis);
//    }
//
//    /**
//     * Parses and validates the JWT token from the HTTP request.
//     * <p>
//     * This method extracts the token from the request's Authorization header,
//     * verifies it using the secret key, and returns the token's claims.
//     * If the token has expired or is invalid, an appropriate exception is thrown.
//     * </p>
//     *
//     * @param request the {@code HttpServletRequest} containing the JWT.
//     * @return the {@code Claims} extracted from the token.
//     * @throws TokenExpired if the token has expired.
//     * @throws BadToken     if the token is invalid or missing.
//     */
//    public Claims jwsToken(HttpServletRequest request) {
//        SecretKey secretKey = Keys.hmacShaKeyFor(appProperties.getJwtSecret().getBytes());
//        Claims jws;
//        try {
//            jws = Jwts.parser()
//                    .verifyWith(secretKey)
//                    .build()
//                    .parseSignedClaims(getBearer(request))
//                    .getPayload();
//        } catch (ExpiredJwtException e) {
//            throw new TokenExpired(e.getMessage());
//        } catch (JwtException | IllegalArgumentException ex) {
//            throw new BadToken(ex.getMessage());
//        }
//        return jws;
//    }
//
//    /**
//     * Determines whether the user making the request is a super admin.
//     * <p>
//     * The method checks the "role" claim in the JWT token to determine if it matches the super admin role.
//     * </p>
//     *
//     * @param request the {@code HttpServletRequest} containing the JWT.
//     * @return {@code true} if the user's role is super admin; {@code false} otherwise.
//     */
//    public boolean getSuperAdmin(HttpServletRequest request) {
//        return getValue(request, "role").toString().equals(SUPER_ADMIN);
//    }
//
//    /**
//     * Retrieves the user's full name from the JWT token.
//     * <p>
//     * The full name is constructed by concatenating the "first_name" and "last_name" claims.
//     * </p>
//     *
//     * @param request the {@code HttpServletRequest} containing the JWT.
//     * @return the full name of the user as a {@code String}.
//     */
//    public String getFullName(HttpServletRequest request) {
//        return getValue(request, "first_name").toString() + " " + getValue(request, "last_name");
//    }
//
//    /**
//     * Retrieves the user's role ID from the JWT token.
//     * <p>
//     * The role ID is extracted from the "role_id" claim in the token.
//     * </p>
//     *
//     * @param request the {@code HttpServletRequest} containing the JWT.
//     * @return the role ID as an {@code Integer}.
//     */
//    public Integer getRoleId(HttpServletRequest request) {
//        return Integer.valueOf(getValue(request, "role_id").toString());
//    }
//
//    /**
//     * Retrieves a specific claim value from the JWT token.
//     * <p>
//     * This is a helper method that extracts the value associated with the given key from the token's claims.
//     * </p>
//     *
//     * @param request the {@code HttpServletRequest} containing the JWT.
//     * @param key     the claim key to retrieve.
//     * @return the value of the specified claim as an {@code Object}.
//     */
//    private Object getValue(HttpServletRequest request, String key) {
//        return jwsToken(request).get(key);
//    }
//
//    /**
//     * Extracts the Bearer token from the HTTP request's Authorization header.
//     * <p>
//     * If the Authorization header is missing or empty, a {@code BadToken} exception is thrown.
//     * </p>
//     *
//     * @param request the {@code HttpServletRequest} containing the JWT.
//     * @return the JWT token as a {@code String} without the Bearer prefix.
//     */
//    private String getBearer(HttpServletRequest request) {
//        String authTokenHeader = request.getHeader(HEADER_STRING);
//        if (authTokenHeader == null || authTokenHeader.trim().isEmpty()) {
//            throw new BadToken("Bearer token missing");
//        }
//        return authTokenHeader.replace(TOKEN_PREFIX, "");
//    }
//
//    /**
//     * Creates a map of claims from the given user details.
//     * <p>
//     * The returned map contains the user's id, first name, last name, email, role id, and role.
//     * This map is used as the payload of the JWT.
//     * </p>
//     *
//     * @param model the {@code UserResponse} containing user details.
//     * @return an immutable {@code Map} of claims for the JWT.
//     */
//    private Map<String, Object> createClaim(UserResponse model) {
//        return Map.of(
//                "id", model.getId(),
//                "first_name", model.getFirstName(),
//                "last_name", model.getLastName(),
//                "email", model.getEmail(),
//                "role_id", model.getRole().getId(),
//                "role", model.getRole().getName()
//        );
//    }
}