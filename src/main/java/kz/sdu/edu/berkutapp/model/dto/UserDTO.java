package kz.sdu.edu.berkutapp.model.dto;

import kz.sdu.edu.berkutapp.model.AppUser;
import lombok.Data;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;

@Data
public class UserDTO extends RepresentationModel<UserDTO> {
    private String username;

    private UserTypeEnum userTypeEnum;

    private String phoneNumber;

    public UserDTO(AppUser appUser) {
        Link link = Link.of("/users/profile-photo/" + appUser.getId(), "profile_photo");
        add(link);
        username = appUser.getUsername();
        phoneNumber = appUser.getPhoneNumber();
    }
}
