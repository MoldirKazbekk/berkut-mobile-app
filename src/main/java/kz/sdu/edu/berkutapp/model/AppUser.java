package kz.sdu.edu.berkutapp.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PostLoad;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import kz.sdu.edu.berkutapp.model.dto.ChildDTO;
import kz.sdu.edu.berkutapp.model.dto.UserTypeEnum;
import lombok.Data;
import org.hibernate.annotations.Type;

@Data
@Entity
@Table(name = "app_user")
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //the value of the primary key is automatically generated.
    private Long id;

    @Column
    private String username;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "image", columnDefinition = "bytea")
    private byte[] image;

    @Transient
    private UserTypeEnum userTypeEnum;

    @OneToOne(mappedBy = "appUser", cascade = CascadeType.ALL)
    private Parent parent;

    @OneToOne(mappedBy = "appUser", cascade = CascadeType.ALL)
    private Child child;

    @PostLoad
    private void onPostLoad() {
        if (parent != null) {
            userTypeEnum = UserTypeEnum.PARENT;
        } else if (child != null) {
            userTypeEnum = UserTypeEnum.CHILD;
        }
    }
}
