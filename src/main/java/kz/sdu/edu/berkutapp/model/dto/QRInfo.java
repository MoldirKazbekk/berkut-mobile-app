package kz.sdu.edu.berkutapp.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import kz.sdu.edu.berkutapp.model.AppUser;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class QRInfo implements Serializable {

    private int status = 0;

    @JsonProperty("parent_info")
    private UserDTO parentInfo;

    public QRInfo(AppUser appUser) {
        if (appUser != null) {
            parentInfo = new UserDTO(appUser);
            status = 1;
        }
    }
}
