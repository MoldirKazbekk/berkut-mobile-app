package kz.sdu.edu.berkutapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import kz.sdu.edu.berkutapp.model.dto.SavedLocationDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "saved_location")
public class SavedLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Double longitude;

    private Double latitude;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private AppUser parent;

    private Integer radius;

    public SavedLocation(SavedLocationDTO savedLocationDTO) {
        this.radius = savedLocationDTO.getRadius();
        this.longitude = Double.valueOf(savedLocationDTO.getLongitude());
        this.latitude = Double.valueOf(savedLocationDTO.getLatitude());
        this.name = savedLocationDTO.getName();
    }
}
