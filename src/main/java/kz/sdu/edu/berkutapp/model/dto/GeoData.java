package kz.sdu.edu.berkutapp.model.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class GeoData implements Serializable {

    private Long userId;

    private String latitude;

    private String longitude;

    private Long timestamp;

    private String timezone;

    private String username;

    private Integer battery;
}
