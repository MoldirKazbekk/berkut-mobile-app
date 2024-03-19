package kz.sdu.edu.berkutapp.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import kz.sdu.edu.berkutapp.model.HotlineNumber;
import kz.sdu.edu.berkutapp.model.SavedLocation;
import lombok.Data;

@Data
public class SavedLocationDTO {

    private Double latitude;

    private Double longitude;

    @Schema(description = "place name")
    private String name;

    @Schema(defaultValue = "25")
    private Integer radius;
    public SavedLocationDTO(SavedLocation savedLocation) {
        this.name = savedLocation.getName();
        this.latitude = savedLocation.getLatitude();
        this.longitude = savedLocation.getLongitude();
        this.radius= savedLocation.getRadius();

    }

}
