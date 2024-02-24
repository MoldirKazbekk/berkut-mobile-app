package kz.sdu.edu.berkutapp.repository;

import kz.sdu.edu.berkutapp.model.ChildLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChildLocationRepository extends JpaRepository<ChildLocation, Long> {
}
