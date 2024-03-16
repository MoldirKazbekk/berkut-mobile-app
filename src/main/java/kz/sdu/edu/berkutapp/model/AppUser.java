package kz.sdu.edu.berkutapp.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import kz.sdu.edu.berkutapp.model.dto.UserType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "app_user")
@NoArgsConstructor
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //the value of the primary key is automatically generated.
    private Long id;

    @Column
    private String username;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "image_id")
    private String imageId;

    @Column
    @Enumerated(EnumType.STRING)
    private UserType role;

    @OneToMany(mappedBy = "child", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<ChildLocation> childLocations;

    @OneToMany(mappedBy = "child", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<HotlineNumber> hotlineNumbers;

    @OneToMany(mappedBy = "parent", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<SavedLocation> savedLocations;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "user_relationship",
            joinColumns = @JoinColumn(name = "parent_id"),
            inverseJoinColumns = @JoinColumn(name = "child_id"))
    private Set<AppUser> children = new HashSet<>();

    public void addSavedLocation(SavedLocation savedLocation) {
        savedLocation.setParent(this);
        this.getSavedLocations().add(savedLocation);
    }

    public void addHotlineNumber(HotlineNumber hotlineNumber) {
        hotlineNumber.setChild(this);
        this.getHotlineNumbers().add(hotlineNumber);
    }
    public void addChildLocation(ChildLocation childLocation) {
        childLocation.setChild(this);
        this.getChildLocations().add(childLocation);
    }
}