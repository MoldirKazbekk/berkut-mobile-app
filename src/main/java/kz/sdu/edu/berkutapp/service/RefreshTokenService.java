package kz.sdu.edu.berkutapp.service;

import kz.sdu.edu.berkutapp.model.AppUser;
import kz.sdu.edu.berkutapp.model.RefreshToken;
import kz.sdu.edu.berkutapp.repository.AppUserRepository;
import kz.sdu.edu.berkutapp.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Ref;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken createRefreshToken(AppUser appUser) {
        RefreshToken refreshToken = RefreshToken.builder()
                .appUser(appUser)
                .token(UUID.randomUUID().toString())
                .expireDate(LocalDateTime.now().plusMinutes(1)) // set expiry of refresh token to 10 minutes - you can configure it application.properties file
                .build();
        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken getOrCreateRefreshToken(AppUser appUser) {
        return refreshTokenRepository.findByAppUser(appUser).orElseGet(() -> createRefreshToken(appUser));
    }

    public RefreshToken findByToken(String refreshToken) {
        return refreshTokenRepository.findByToken(refreshToken).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid refresh token"));
    }

    public void verifyExpiration(RefreshToken token) {
        if (token.getExpireDate().isBefore(LocalDateTime.now())) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException(token.getToken() + " Refresh token is expired. Please make a new login..!");
        }

    }

}
