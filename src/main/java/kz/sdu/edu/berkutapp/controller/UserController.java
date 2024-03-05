package kz.sdu.edu.berkutapp.controller;

import java.nio.file.Files;
import java.io.*;
import com.google.api.client.http.InputStreamContent;
import com.google.api.services.drive.model.File;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import kz.sdu.edu.berkutapp.model.AppUser;
import kz.sdu.edu.berkutapp.model.dto.UserDTO;
import kz.sdu.edu.berkutapp.repository.AppUserRepository;
import kz.sdu.edu.berkutapp.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.GeneralSecurityException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{id}")
@Slf4j
public class UserController {
    private final UserService userService;
    private final AppUserRepository appUserRepository;
    @GetMapping
    public UserDTO getUserInfo(@PathVariable("id") Long id) {
        return userService.getUserInfo(id);
    }

    @GetMapping("/profile-photo")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "no profile image for specified user")})
    public ResponseEntity<byte[]> getAppUserPhoto(@PathVariable("id") Long id) throws GeneralSecurityException, IOException {

        AppUser appUser = appUserRepository.findById(id).orElseThrow();
        String imageId = appUser.getImage();

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", MediaType.IMAGE_JPEG_VALUE);
        return new ResponseEntity(userService.getImage(imageId), headers, HttpStatus.OK);
    }

    @PutMapping("setImage")
    public boolean setPhoto(@NotNull @RequestParam(value = "image", required = false) MultipartFile image,
                            @RequestParam(name = "username", required = false) String newUsername, @PathVariable("id") Long id) throws IOException, GeneralSecurityException, GeneralSecurityException {
        return userService.updateUserData(id, image, newUsername);
    }
}
