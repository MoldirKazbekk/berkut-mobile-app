package kz.sdu.edu.berkutapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import kz.sdu.edu.berkutapp.model.dto.GeoData;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Entity
@Data
@NoArgsConstructor
@Table(name = "child_location")
public class ChildLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "child_id", nullable = false)
    private AppUser child;

    @Column
    private LocalDateTime time;

    @Column
    private Double longitude;

    @Column
    private Double latitude;

    public ChildLocation(GeoData geoData) {
        this.longitude = Double.valueOf(geoData.getLongitude());
        this.latitude = Double.valueOf(geoData.getLatitude());
        this.time = convertTimestampToDateTime(geoData.getTimestamp(),geoData.getTimezone());
    }

    private static LocalDateTime convertTimestampToDateTime(String timestamp, String timeZone) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        return LocalDateTime.parse(timestamp, formatter);

    }
}