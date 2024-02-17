package kz.sdu.edu.berkutapp.service;

import kz.sdu.edu.berkutapp.model.Child;
import kz.sdu.edu.berkutapp.model.dto.ChildDTO;
import kz.sdu.edu.berkutapp.model.dto.ParentDTO;
import kz.sdu.edu.berkutapp.model.dto.UserDTO;
import kz.sdu.edu.berkutapp.model.dto.UserTypeEnum;
import kz.sdu.edu.berkutapp.repository.AppUserRepository;
import kz.sdu.edu.berkutapp.repository.ChildRepository;
import kz.sdu.edu.berkutapp.repository.ParentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final AppUserRepository appUserRepository;

    private final ParentRepository parentRepository;

    private final ChildRepository childRepository;

    public byte[] getPhotoByUserId(Long id) {
        return appUserRepository.findById(id).orElseThrow().getImage();
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
}
