package kz.sdu.edu.berkutapp.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
public class GeoData implements Serializable {

    @Schema(description = "user id of child")
    private Long userId;

    private String latitude;

    private String longitude;

    @Schema(description = "timestamp in UTC")
    private String timestamp;

    private String timezone;

    @Schema(description = "username of child")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String username;

    private Integer battery;

}
