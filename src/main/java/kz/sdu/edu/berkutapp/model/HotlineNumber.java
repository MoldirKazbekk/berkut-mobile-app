package kz.sdu.edu.berkutapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import kz.sdu.edu.berkutapp.model.dto.NumberDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "hotline_number")
@AllArgsConstructor
@NoArgsConstructor
public class HotlineNumber {
    @Id
    private Long id;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser child;

    public HotlineNumber(NumberDTO numberDTO){
        this.phoneNumber = numberDTO.getPhoneNumber();
        this.name = numberDTO.getName();
    }
}
