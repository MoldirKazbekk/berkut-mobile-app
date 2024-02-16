package kz.sdu.edu.berkutapp.repository;

import kz.sdu.edu.berkutapp.model.Child;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChildRepository extends JpaRepository<Child, Long> {
}
