package kz.sdu.edu.berkutapp.service;

import kz.sdu.edu.berkutapp.model.AppUser;
import kz.sdu.edu.berkutapp.model.Child;
import kz.sdu.edu.berkutapp.model.Parent;
import kz.sdu.edu.berkutapp.model.RefreshToken;
import kz.sdu.edu.berkutapp.model.dto.TokenDTO;
import kz.sdu.edu.berkutapp.model.dto.VerificationRequest;
import kz.sdu.edu.berkutapp.repository.AppUserRepository;
import kz.sdu.edu.berkutapp.repository.ChildRepository;
import kz.sdu.edu.berkutapp.repository.ParentRepository;
import kz.sdu.edu.berkutapp.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final AppUserRepository appUserRepository;

    private final ParentRepository parentRepository;

    private final ChildRepository childRepository;

    private final JwtUtil jwtUtil;

    private final RefreshTokenService refreshTokenService;

    public TokenDTO signIn(VerificationRequest verificationRequest) {
        AppUser appUser = appUserRepository.findByPhoneNumber(verificationRequest.getPhoneNumber()).orElseGet(() -> createAppUser(verificationRequest));
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
        appUser.setUserTypeEnum(verificationRequest.getUserTypeEnum());
        switch (verificationRequest.getUserTypeEnum()) {
            case CHILD -> {
                Child child = new Child();
                child.setAppUser(appUser);
                appUser.setChild(child);
            }
            case PARENT -> {
                Parent parent = new Parent();
                parent.setAppUser(appUser);
                appUser.setParent(parent);
            }
        }
        appUserRepository.save(appUser);
        return appUser;
    }

    public String refreshJWT(String refreshToken, String expiredJWT) {
        RefreshToken rt = refreshTokenService.findByToken(refreshToken);
        refreshTokenService.verifyExpiration(rt);
        if (jwtUtil.extractId(expiredJWT).equals(rt.getAppUser().getId().toString())) {
            String newJWT = jwtUtil.generateToken(rt.getAppUser());
            log.info("new jwt for user {} - {}", rt.getAppUser(), newJWT);
            return newJWT;
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                "Access and refresh tokens don't belong to single user");
    }
}
