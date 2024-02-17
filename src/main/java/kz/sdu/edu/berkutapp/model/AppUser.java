package kz.sdu.edu.berkutapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import kz.sdu.edu.berkutapp.model.dto.ChildDTO;
import lombok.Data;
import org.hibernate.annotations.Type;

@Data
@Entity
@Table(schema = "berkut", name = "app_user")
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //the value of the primary key is automatically generated.
    private Long id;

    @Column
    private String username;

    @Column
    private String password;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "image", columnDefinition = "bytea")
    private byte[] image;
}
