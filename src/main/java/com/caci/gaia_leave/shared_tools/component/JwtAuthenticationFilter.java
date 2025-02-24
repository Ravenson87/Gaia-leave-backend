package com.caci.gaia_leave.shared_tools.component;

import com.caci.gaia_leave.shared_tools.configuration.AppProperties;
import com.caci.gaia_leave.shared_tools.exception.CustomException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final AppProperties appProperties;

    SecretKey secretKey = Keys.hmacShaKeyFor(appProperties.getJwtSecret().getBytes(StandardCharsets.UTF_8));


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

//        String jwt = getJwtFromRequest(request);
//
//        if (jwt != null) {
//            try {
//                Claims claims = Jwts.parser()
//                        .verifyWith(secretKey)
//                        .parseClaimsJws(jwt)
//                        .getBody();
//
//                // Na osnovu klaima, možete postaviti autentifikaciju
//                Authentication authentication = new UsernamePasswordAuthenticationToken(
//                        claims.getSubject(),  // Korisničko ime iz klaima
//                        null, // Nema potrebe za lozinkom
//                        List.of()  // Autoriteti (npr. ROLE_USER)
//                );
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//            } catch (SignatureException | ExpiredJwtException e) {
//                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired JWT token");
//                return;
////            }
//        }
//
//        filterChain.doFilter(request, response);

    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);  // Uzimamo samo token bez "Bearer "
        }
        return null;
    }


}
