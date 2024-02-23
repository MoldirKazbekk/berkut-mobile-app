package kz.sdu.edu.berkutapp.service;

import kz.sdu.edu.berkutapp.model.AppUser;
import kz.sdu.edu.berkutapp.model.RefreshToken;
import kz.sdu.edu.berkutapp.model.dto.TokenDTO;
import kz.sdu.edu.berkutapp.model.dto.VerificationRequest;
import kz.sdu.edu.berkutapp.repository.AppUserRepository;
import kz.sdu.edu.berkutapp.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final AppUserRepository appUserRepository;


    private final JwtUtil jwtUtil;

    private final RefreshTokenService refreshTokenService;

    public TokenDTO signIn(VerificationRequest verificationRequest) {
        AppUser appUser = appUserRepository.findByPhoneNumber(verificationRequest.getPhoneNumber())
                .orElseGet(() -> createAppUser(verificationRequest));
        appUserRepository.save(appUser);
        String jwt = jwtUtil.generateToken(appUser);
        var refreshToken = refreshTokenService.getOrCreateRefreshToken(appUser);
        return TokenDTO.builder()
                .refreshToken(refreshToken.getToken())
                .jwt(jwt)
                .build();
    }

    private AppUser createAppUser(VerificationRequest verificationRequest) {
        AppUser appUser = new AppUser();
        appUser.setUsername(verificationRequest.getUsername());
        appUser.setPhoneNumber(verificationRequest.getPhoneNumber());
        appUser.setRole(verificationRequest.getRole());
        return appUser;
    }

    public String refreshJWT(String refreshToken) {
        RefreshToken rt = refreshTokenService.findByToken(refreshToken);
        refreshTokenService.verifyExpiration(rt);
        String newJWT = jwtUtil.generateToken(rt.getAppUser());
        log.info("new jwt for user {} - {}", rt.getAppUser(), newJWT);
        return newJWT;
    }
}
