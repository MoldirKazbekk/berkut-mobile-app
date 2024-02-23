package kz.sdu.edu.berkutapp.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

@Data
public class VerificationRequest implements Serializable {
    @NotBlank
    private String code;

    @JsonProperty("phone_number")
    @NotBlank
    private String phoneNumber;

    @JsonProperty
    private String username;

    private UserType role;
}
