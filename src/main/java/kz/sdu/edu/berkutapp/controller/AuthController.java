package kz.sdu.edu.berkutapp.controller;

import kz.sdu.edu.berkutapp.model.dto.UserTypeEnum;
import kz.sdu.edu.berkutapp.phone_verifier.VerificationService;
import kz.sdu.edu.berkutapp.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
                         @RequestParam("image") MultipartFile image,
                         @RequestParam("user_type") String userType) throws IOException {
        return authService.register(image.getBytes(), username, phoneNumber, UserTypeEnum.valueOf(userType));
    }

    @PostMapping("/generate")
    public void generate(@RequestParam("phone_number") String phoneNumber) {
        verificationService.generateOTP(phoneNumber);
    }

    @PostMapping("/verify")
    public ResponseEntity<String> verify(@RequestParam("code") String code,
                                         @RequestParam("phone_number") String phoneNumber) {
        return ResponseEntity.ok(verificationService.verifyUserOTP(code, phoneNumber));
    }
}
