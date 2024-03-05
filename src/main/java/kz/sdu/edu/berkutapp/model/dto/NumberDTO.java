package kz.sdu.edu.berkutapp.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import kz.sdu.edu.berkutapp.model.HotlineNumber;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NumberDTO implements Serializable {

    @NotBlank
    private String phoneNumber;

    @NotBlank
    @Schema(description = "name of owner of the phone number")
    private String name;

    public NumberDTO(HotlineNumber hotlineNumber) {
        this.name = hotlineNumber.getName();
        this.phoneNumber = hotlineNumber.getPhoneNumber();
    }
}
