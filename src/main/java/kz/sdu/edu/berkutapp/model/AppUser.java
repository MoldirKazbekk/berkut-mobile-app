package kz.sdu.edu.berkutapp.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
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

import java.util.HashSet;
import java.util.Set;

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

    @Column(name = "image_id")
    private String imageId;

    @Column
    @Enumerated(EnumType.STRING)
    private UserType role;

    @OneToMany(mappedBy = "child", fetch = FetchType.EAGER)
    private Set<ChildLocation> childLocations = new HashSet<>();

    @OneToMany(mappedBy = "child", cascade = CascadeType.ALL)
    private Set<HotlineNumber> hotlineNumbers = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "user_relationship",
            joinColumns = @JoinColumn(name = "parent_id"),
            inverseJoinColumns = @JoinColumn(name = "child_id"))
    private Set<AppUser> children = new HashSet<>();
}