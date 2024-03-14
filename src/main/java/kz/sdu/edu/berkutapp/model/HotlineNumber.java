package kz.sdu.edu.berkutapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import kz.sdu.edu.berkutapp.model.dto.NumberDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "hotline_number")
@NoArgsConstructor
public class HotlineNumber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column
    private String name;

    @ManyToOne
    @JoinColumn(name = "child_id", nullable = false)
    private AppUser child;

    public HotlineNumber(NumberDTO numberDTO){
        this.phoneNumber = numberDTO.getPhoneNumber();
        this.name = numberDTO.getName();
    }
}
