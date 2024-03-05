package kz.sdu.edu.berkutapp.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.util.Collections;
import com.google.api.client.http.InputStreamContent;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
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
    private String folderId="1evQ8SrfFK4b3h_5O47tjwzfa4H5k5rE4";
    private  GoogleDriveService driveService;
    public byte[] getImage(String imageId) throws GeneralSecurityException, IOException {
        driveService =new GoogleDriveService();
        OutputStream outputStream = new ByteArrayOutputStream();

        driveService.getService().files().get(imageId)
                .executeMediaAndDownloadTo(outputStream);
        return ((ByteArrayOutputStream) outputStream).toByteArray();
    }

    @Transactional
    public boolean updateUserData(Long id, MultipartFile image, String newUsername) throws IOException, GeneralSecurityException {
        log.info("Setting new image for user by id {}", id);
        AppUser appUser = appUserRepository.findById(id).orElseThrow();
        String userImageId = appUser.getImage();
        driveService = new GoogleDriveService();

        File fileMetadata = new File();
        fileMetadata.setParents(Collections.singletonList(folderId));
        fileMetadata.setName(appUser.getUsername());

        InputStreamContent mediaContent = new InputStreamContent("image/jpeg", image.getInputStream());
        driveService.getService().files().delete(userImageId).execute();


        File Drivefile = driveService.getService().files().create(fileMetadata, mediaContent)
                .setFields("id")
                .execute();
        appUser.setImage(Drivefile.getId());


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
