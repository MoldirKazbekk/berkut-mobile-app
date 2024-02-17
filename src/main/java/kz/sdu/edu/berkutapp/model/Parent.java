package kz.sdu.edu.berkutapp.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "parent", schema = "berkut")
public class Parent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(orphanRemoval = true, cascade = CascadeType.REMOVE)
    private AppUser appUser;
    //orphanRemoval=true -  if a Parent entity is disassociated from an AppUser, the AppUser entity will be removed (considered an orphan) from the database.

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "parent")
    private List<Child> children = new ArrayList<>();
    //orphanRemoval=true -  if a Child is removed from the children list, it will be deleted from the database.
}
