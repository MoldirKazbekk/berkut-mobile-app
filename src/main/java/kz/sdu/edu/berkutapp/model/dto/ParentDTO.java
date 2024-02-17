package kz.sdu.edu.berkutapp.model.dto;

import kz.sdu.edu.berkutapp.model.AppUser;
import kz.sdu.edu.berkutapp.model.Child;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ParentDTO extends UserDTO {
    private List<ChildDTO> children = new ArrayList<>();

    public ParentDTO(AppUser appUser) {
        super(appUser);
    }

    public void addChild(Child child) {
        ChildDTO childDTO = new ChildDTO(child.getAppUser());
        childDTO.setUsername(childDTO.getUsername());
        childDTO.setPhoneNumber(child.getAppUser().getPhoneNumber());
        childDTO.setUserTypeEnum(UserTypeEnum.CHILD);
        children.add(childDTO);
    }
}
