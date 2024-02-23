package kz.sdu.edu.berkutapp.service;

import jakarta.transaction.Transactional;
import kz.sdu.edu.berkutapp.model.AppUser;
import kz.sdu.edu.berkutapp.model.dto.UserDTO;
import kz.sdu.edu.berkutapp.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final AppUserRepository appUserRepository;


    public byte[] getImage(Long id) {
        return appUserRepository.findById(id).orElseThrow().getImage();
    }

    @Transactional
    public boolean updateUserData(Long id, byte[] image, String newUsername) {
        log.info("Setting new image for user by id {}", id);
        AppUser appUser = appUserRepository.findById(id).orElseThrow();
        appUser.setImage(image);
        if (newUsername != null) {
            appUser.setUsername(newUsername);
        }
        appUserRepository.save(appUser);
        return true;
    }

    public UserDTO getUserInfo(Long id) {
        var user = appUserRepository.findById(id).orElseThrow();
        return new UserDTO(user);
    }

}
