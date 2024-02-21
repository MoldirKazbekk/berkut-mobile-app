package kz.sdu.edu.berkutapp.controller;

import kz.sdu.edu.berkutapp.model.dto.UserDTO;
import kz.sdu.edu.berkutapp.model.dto.UserTypeEnum;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Slf4j
public class UserController {
    private final UserService userService;

    @GetMapping("/profile-photo/{id}")
    public ResponseEntity<byte[]> getAppUserPhoto(@PathVariable("id") Long id) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", MediaType.IMAGE_JPEG_VALUE);
        return new ResponseEntity<>(userService.getImage(), headers, HttpStatus.OK);
    }

    @PutMapping
    public boolean setPhoto(@NotNull @RequestParam("image") MultipartFile image) throws IOException {
        return userService.setImage(image.getBytes());
    }

    @GetMapping("/{id}")
    public UserDTO getUserInfo(@PathVariable("id") Long id) {
        return userService.getUserInfo(id);
    }
}
