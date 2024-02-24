package kz.sdu.edu.berkutapp.model.dto;

import kz.sdu.edu.berkutapp.model.AppUser;
import lombok.Data;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;

@Data
public class UserDTO extends RepresentationModel<UserDTO> {
    private String username;

    private UserType role;

    private String phoneNumber;

    private Long id;

    public UserDTO(AppUser appUser) {
        Link link = Link.of("/users/" + appUser.getId() + "/profile-photo", "profile_photo");
        add(link);
        username = appUser.getUsername();
        id = appUser.getId();
        phoneNumber = appUser.getPhoneNumber();
    }
}
