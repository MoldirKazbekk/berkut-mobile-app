package kz.sdu.edu.berkutapp.repository;

import kz.sdu.edu.berkutapp.model.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParentRepository extends JpaRepository<Parent, Long> {
    Optional<Parent> findParentByAppUserId(Long id);

}
