package kz.sdu.edu.berkutapp.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SavedLocationDTO {

    private Long parentId;

    private String latitude;

    private String longitude;

    @Schema(description = "place name")
    private String name;

    @Schema(defaultValue = "25")
    private Integer radius;

}
