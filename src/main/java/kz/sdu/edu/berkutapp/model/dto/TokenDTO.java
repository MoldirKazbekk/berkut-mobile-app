package kz.sdu.edu.berkutapp.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class TokenDTO implements Serializable {
    @JsonProperty("refresh_token")
    private String refreshToken;

    @JsonProperty
    private String jwt;

    @JsonProperty
    private Long id;
}
