package kz.sdu.edu.berkutapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import kz.sdu.edu.berkutapp.model.dto.TokenDTO;
import kz.sdu.edu.berkutapp.model.dto.VerificationRequest;
import kz.sdu.edu.berkutapp.phone_verifier.VerificationService;
import kz.sdu.edu.berkutapp.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/otc")
@Slf4j
public class OTCController {

    private final VerificationService verificationService;

    private final AuthService authService;

    @PostMapping("/generate")
    @Operation(summary = "Generate and send the pin-code for any specified number")
    public void generate(@RequestParam("phone_number") @Schema(description = "phone number without +7") String phoneNumber) {
        verificationService.generateOTP(phoneNumber);
    }

    @PostMapping("/sign-in")
    @Operation(summary = "If phone number is successfully verified, signIn the user with specified data")
    @ApiResponses(value = {@ApiResponse(responseCode = "404", description = "User with phone number already registered"),
            @ApiResponse(responseCode = "400", description = "Pin code is not correct or has expired")})
    public ResponseEntity<TokenDTO> verifyAndRegister(@RequestBody VerificationRequest verificationRequest) {
        boolean isVerifiedUser = verificationService.verifyUserOTP(verificationRequest);
        if (isVerifiedUser) {
            var tokenDTO = authService.signIn(verificationRequest);
            log.info("generated token: {}", tokenDTO);
            return ResponseEntity.ok().body(tokenDTO);
        }
        return ResponseEntity.ok().build();
    }
}
