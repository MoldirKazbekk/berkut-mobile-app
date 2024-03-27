package kz.sdu.edu.berkutapp.service;

import com.google.api.client.http.InputStreamContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import jakarta.transaction.Transactional;
import kz.sdu.edu.berkutapp.model.AppUser;
import kz.sdu.edu.berkutapp.model.dto.UserDTO;
import kz.sdu.edu.berkutapp.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collections;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final AppUserRepository appUserRepository;

    @Value("${google-drive.folder-id}")
    private String folderId;

    private final Drive googleDriveService;

    public byte[] getImage(Long userId) throws IOException {
        AppUser appUser = appUserRepository.findById(userId).orElseThrow();
        String imageId = appUser.getImageId();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        googleDriveService.files().get(imageId)
                .executeMediaAndDownloadTo(outputStream);
        return outputStream.toByteArray();
    }

    @Transactional
    public boolean updateUserData(Long id, MultipartFile image, String newUsername) throws IOException {
        log.info("Setting new image for user by id {}", id);
        AppUser appUser = appUserRepository.findById(id).orElseThrow();
        File fileMetadata = new File();
        fileMetadata.setParents(Collections.singletonList(folderId));
        fileMetadata.setName(appUser.getUsername());

        InputStreamContent mediaContent = new InputStreamContent("image/jpeg", image.getInputStream());
        if( appUser.getImageId() != null){
            googleDriveService.files().delete(appUser.getImageId()).execute();
        }

        File Drivefile = googleDriveService.files().create(fileMetadata, mediaContent)
                .setFields("id")
                .execute();
        appUser.setImageId(Drivefile.getId());

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

    public void deleteUser(Long userId) {
        appUserRepository.deleteById(userId);
    }
}
