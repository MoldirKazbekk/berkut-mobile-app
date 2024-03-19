package kz.sdu.edu.berkutapp.model;

import jakarta.persistence.*;
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

    @Column
    private String name;
    @Column
    private Double longitude;
    @Column
    private Double latitude;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private AppUser parent;

    @Column
    private Integer radius;

    public SavedLocation(SavedLocationDTO savedLocationDTO) {
        this.radius = savedLocationDTO.getRadius();
        this.longitude = Double.valueOf(savedLocationDTO.getLongitude());
        this.latitude = Double.valueOf(savedLocationDTO.getLatitude());
        this.name = savedLocationDTO.getName();
    }
    public String toString(){
        return this.name+" " +
                this.longitude+" "+
                this.latitude+" "+
                this.radius;
    }

}