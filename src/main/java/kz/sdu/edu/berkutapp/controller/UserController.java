package kz.sdu.edu.berkutapp.controller;

import kz.sdu.edu.berkutapp.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Slf4j
public class UserController {
    private final AppUserRepository appUserRepository;
    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getAppUserById(@PathVariable("id") Long id){
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", MediaType.IMAGE_JPEG_VALUE);
        return new ResponseEntity<>(appUserRepository.findById(id).get().getImage(), headers, HttpStatus.OK);
    }
}
