package kz.sdu.edu.berkutapp.service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import com.google.api.client.http.InputStreamContent;
import com.google.api.services.drive.model.File;
import org.springframework.web.multipart.MultipartFile;
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
    public boolean updateUserData(Long id, MultipartFile image, String newUsername) throws IOException, GeneralSecurityException {
        log.info("Setting new image for user by id {}", id);
        AppUser appUser = appUserRepository.findById(id).orElseThrow();
        GoogleDriveService driveService = new GoogleDriveService();

        File fileMetadata = new File();
        fileMetadata.setName(appUser.getId().toString());
        fileMetadata.setParents(Collections.singletonList("1evQ8SrfFK4b3h_5O47tjwzfa4H5k5rE4"));
        fileMetadata.setName(appUser.getId().toString());
        InputStreamContent mediaContent = new InputStreamContent("image/jpeg", image.getInputStream());
        File file = driveService.getService().files().create(fileMetadata, mediaContent)
                .setFields("id")
                .execute();
        log.info("File ID: " + file.getId());
        appUser.setImage(image.getBytes());

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
