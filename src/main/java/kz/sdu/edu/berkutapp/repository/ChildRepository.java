package kz.sdu.edu.berkutapp.repository;

import kz.sdu.edu.berkutapp.model.Child;
import kz.sdu.edu.berkutapp.model.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChildRepository extends JpaRepository<Child, Long> {
    Optional<Child> findChildByAppUserId(Long id);
}
