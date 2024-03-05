package kz.sdu.edu.berkutapp.model.dto;

import kz.sdu.edu.berkutapp.model.AppUser;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class QRInfo implements Serializable {

    private int status = 0;

    private UserDTO userInfo;

    public QRInfo(AppUser appUser) {
        userInfo = new UserDTO(appUser);
        status = 1;
    }
}
