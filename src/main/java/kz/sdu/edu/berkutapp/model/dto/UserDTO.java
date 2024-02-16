package kz.sdu.edu.berkutapp.model.dto;

import lombok.Data;

@Data
public class UserDTO {
    private String username;

    private UserTypeEnum userTypeEnum;

    private String phoneNumber;

    private byte[] image;

}
