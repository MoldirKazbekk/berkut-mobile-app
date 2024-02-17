package kz.sdu.edu.berkutapp.controller;

import io.swagger.v3.oas.annotations.media.Schema;
import kz.sdu.edu.berkutapp.model.dto.UserTypeEnum;
import kz.sdu.edu.berkutapp.phone_verifier.VerificationService;
import kz.sdu.edu.berkutapp.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Slf4j
public class AuthController {
    private final AuthService authService;

    private final VerificationService verificationService;

    @PostMapping("/register")
    public Long register(@RequestParam("username") String username,
                         @RequestParam("phone_number") String phoneNumber,
                         @NotNull @RequestParam("image") MultipartFile image,
                         @RequestParam("user_type") String userType,
                         @RequestParam("password") String password) throws IOException {
        return authService.register(image.getBytes(), username, phoneNumber, UserTypeEnum.valueOf(userType), password);
    }

    @PostMapping("/generate")
    public void generate(@RequestParam("phone_number") @Schema(description = "phone number without +7") String phoneNumber) {
        verificationService.generateOTP(phoneNumber);
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verify(@RequestParam("code") String code,
                                    @RequestParam("phone_number") String phoneNumber,
                                    @RequestParam("username") String username) {
        String jwt = verificationService.verifyUserOTP(code, phoneNumber, username);
        log.info("generated token: {}", jwt);
        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, jwt).build();
    }
}
