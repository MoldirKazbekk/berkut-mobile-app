package kz.sdu.edu.berkutapp.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String id;
        String jwt;
        String phoneNumber;
        String role;
        if (authorizationHeader != null) {
            try {
                jwt = authorizationHeader.substring(7);
                id = jwtUtil.extractId(jwt);
                phoneNumber = jwtUtil.extractPhoneNumber(jwt);
                role = jwtUtil.extractRole(jwt);
                log.info("role of user {}: {}", phoneNumber, role);
                if (id != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    if (jwtUtil.validateToken(jwt, id)) {
                        SecurityContextHolder.getContext().setAuthentication(
                                new UsernamePasswordAuthenticationToken(id, phoneNumber,
                                        AuthorityUtils.createAuthorityList("ROLE_USER", role)));
                    }
                }
            } catch (ExpiredJwtException e) {
                response.setHeader("expired", e.getMessage());
                response.sendError(401);
                return;
            } catch (JwtException e) {
                log.error("jwt error: {}", e.getMessage());
            }
        }
        filterChain.doFilter(request, response);
    }
}
