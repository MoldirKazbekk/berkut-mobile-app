package kz.sdu.edu.berkutapp.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import kz.sdu.edu.berkutapp.model.dto.UserDTO;
import kz.sdu.edu.berkutapp.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
public class UserController {

    private final UserService userService;

    @GetMapping
    public UserDTO getUserInfo(@PathVariable("id") Long id) {
        return userService.getUserInfo(id);
    }

    @GetMapping("/profile-photo")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "no profile image for specified user")})
    public ResponseEntity<byte[]> getAppUserPhoto(@PathVariable("id") Long id) throws IOException {
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_JPEG_VALUE).body(userService.getImage(id));
    }

    @PutMapping("setImage")
    public boolean updateUserData(@NotNull @RequestParam(value = "image", required = false) MultipartFile image,
                                  @RequestParam(name = "username", required = false) String newUsername,
                                  @PathVariable("id") Long id) throws IOException {
        return userService.updateUserData(id, image, newUsername);
    }

    @DeleteMapping
    public void deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
    }
}


