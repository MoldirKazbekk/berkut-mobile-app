package kz.sdu.edu.berkutapp.model;

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

    @Column(name = "image", columnDefinition = "bytea")
    private String image;

    @Column
    @Enumerated(EnumType.STRING)
    private UserType role;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_relationship", schema = "public",
            joinColumns = @JoinColumn(name = "parent_id"),
            inverseJoinColumns = @JoinColumn(name = "child_id"))
    private Set<AppUser> children = new HashSet<>();

    @ManyToMany(mappedBy = "children", fetch = FetchType.EAGER)
    Set<AppUser> parents = new HashSet<>();
}
