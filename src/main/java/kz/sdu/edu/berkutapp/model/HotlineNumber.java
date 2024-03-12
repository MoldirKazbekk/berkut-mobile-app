package kz.sdu.edu.berkutapp.model;

import jakarta.persistence.*;
import kz.sdu.edu.berkutapp.model.dto.NumberDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data

@Table(name = "hotline_number")
@AllArgsConstructor
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
    @JoinColumn(name = "child_id")
    private AppUser child;

    public HotlineNumber(NumberDTO numberDTO){
        this.phoneNumber = numberDTO.getPhoneNumber();
        this.name = numberDTO.getName();
    }
}
