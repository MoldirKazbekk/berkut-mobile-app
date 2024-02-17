package kz.sdu.edu.berkutapp.service;

import kz.sdu.edu.berkutapp.model.AppUser;
import kz.sdu.edu.berkutapp.model.Child;
import kz.sdu.edu.berkutapp.model.Parent;
import kz.sdu.edu.berkutapp.model.dto.UserDTO;
import kz.sdu.edu.berkutapp.model.dto.UserTypeEnum;
import kz.sdu.edu.berkutapp.phone_verifier.VerificationService;
import kz.sdu.edu.berkutapp.repository.AppUserRepository;
import kz.sdu.edu.berkutapp.repository.ChildRepository;
import kz.sdu.edu.berkutapp.repository.ParentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final AppUserRepository appUserRepository;

    private final ParentRepository parentRepository;

    private final ChildRepository childRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Long register(byte[] image, String username, String phoneNumber, UserTypeEnum userTypeEnum, String password) {
        AppUser appUser = new AppUser();
        appUser.setUsername(username);
        appUser.setPhoneNumber(phoneNumber);
        appUser.setImage(image);
        appUser.setPassword(bCryptPasswordEncoder.encode(password));
        appUserRepository.save(appUser);
        switch (userTypeEnum) {
            case CHILD -> {
                Child child = new Child();
                child.setAppUser(appUser);
                childRepository.save(child);
            }
            case PARENT -> {
                Parent parent = new Parent();
                parent.setAppUser(appUser);
                parentRepository.save(parent);
            }
        }
        return appUser.getId();
    }
}
