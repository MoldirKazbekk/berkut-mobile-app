package kz.sdu.edu.berkutapp.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import kz.sdu.edu.berkutapp.model.dto.UserDTO;
import kz.sdu.edu.berkutapp.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{id}")
@Slf4j
// delete id + username
public class UserController {
    private final UserService userService;

    @GetMapping
    public UserDTO getUserInfo(@PathVariable("id") Long id) {
        return userService.getUserInfo(id);
    }

    @GetMapping("/profile-photo")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "no profile image for specified user")})
    public ResponseEntity<byte[]> getAppUserPhoto(@PathVariable("id") Long id) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", MediaType.IMAGE_JPEG_VALUE);
        byte[] image = userService.getImage(id);
        if (image == null) {
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(userService.getImage(id), headers, HttpStatus.OK);
    }

    @PutMapping
    public boolean setPhoto(@NotNull @RequestParam(value = "image", required = false) MultipartFile image,
                            @RequestParam(name = "username", required = false) String newUsername, @PathVariable("id") Long id) throws IOException {
        return userService.updateUserData(id, image.getBytes(), newUsername);
    }
}
