package kz.sdu.edu.berkutapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import kz.sdu.edu.berkutapp.model.dto.TokenDTO;
import kz.sdu.edu.berkutapp.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Slf4j
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "Generate new access token by provided refresh and expired access tokens")
    @PutMapping("/refresh")
    public ResponseEntity<?> refreshAccessToken(@RequestBody TokenDTO tokenDTO, @RequestHeader(HttpHeaders.AUTHORIZATION) String jwt) {
        var newJwt = authService.refreshJWT(tokenDTO.getRefreshToken(), jwt);
        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, newJwt).build();
    }
}
