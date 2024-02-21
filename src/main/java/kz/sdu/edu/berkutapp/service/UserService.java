package kz.sdu.edu.berkutapp.service;

import jakarta.transaction.Transactional;
import kz.sdu.edu.berkutapp.model.AppUser;
import kz.sdu.edu.berkutapp.model.Child;
import kz.sdu.edu.berkutapp.model.dto.ChildDTO;
import kz.sdu.edu.berkutapp.model.dto.ParentDTO;
import kz.sdu.edu.berkutapp.model.dto.UserDTO;
import kz.sdu.edu.berkutapp.model.dto.UserTypeEnum;
import kz.sdu.edu.berkutapp.repository.AppUserRepository;
import kz.sdu.edu.berkutapp.repository.ChildRepository;
import kz.sdu.edu.berkutapp.repository.ParentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final AppUserRepository appUserRepository;

    private final ParentRepository parentRepository;

    private final ChildRepository childRepository;

    public byte[] getImage() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        Long id = (Long) auth.getPrincipal();
        return appUserRepository.findById(id).orElseThrow().getImage();
    }

    @Transactional
    public boolean setImage(byte[] image) {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        String id = auth.getPrincipal().toString();
//        String phoneNumber = auth.getDetails().toString();
        log.info("Setting new image for user by id {}", id);
        AppUser appUser = appUserRepository.findById(Long.valueOf(id)).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found " + id));
        appUser.setImage(image);
        appUserRepository.save(appUser);
        return true;
    }

    public UserDTO getUserInfo(Long id) {
        var user = parentRepository.findParentByAppUserId(id);
        if (user.isPresent()) {
            ParentDTO parentDTO = new ParentDTO(user.get().getAppUser());
            parentDTO.setUserTypeEnum(UserTypeEnum.PARENT);
            if (!user.get().getChildren().isEmpty()) {
                user.get().getChildren().forEach(parentDTO::addChild);
            }
            return parentDTO;
        } else {
            Child child = childRepository.findChildByAppUserId(id).orElseThrow();
            ChildDTO childDTO = new ChildDTO(child.getAppUser());
            childDTO.setUserTypeEnum(UserTypeEnum.CHILD);
            childDTO.setParentDTO(child.getParent());
            return childDTO;
        }
    }

    public boolean userWithPhoneNumberExists(String phoneNumber) {
        return appUserRepository.findByPhoneNumber(phoneNumber).isPresent();
    }
}
