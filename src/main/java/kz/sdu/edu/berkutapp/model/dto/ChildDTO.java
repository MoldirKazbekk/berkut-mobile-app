package kz.sdu.edu.berkutapp.model.dto;

import kz.sdu.edu.berkutapp.model.AppUser;
import kz.sdu.edu.berkutapp.model.Parent;
import lombok.Getter;

@Getter
public class ChildDTO extends UserDTO {
    private ParentDTO parentDTO;

    public ChildDTO(AppUser appUser) {
        super(appUser);
    }

    public void setParentDTO(Parent parent) {
        parentDTO = new ParentDTO(parent.getAppUser());
        parentDTO.setPhoneNumber(parent.getAppUser().getPhoneNumber());
        parentDTO.setUsername(parent.getAppUser().getPhoneNumber());
        parentDTO.setUserTypeEnum(UserTypeEnum.PARENT);
    }
}
