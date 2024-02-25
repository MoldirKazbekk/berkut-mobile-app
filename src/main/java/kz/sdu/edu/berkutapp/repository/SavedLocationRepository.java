package kz.sdu.edu.berkutapp.repository;

import kz.sdu.edu.berkutapp.model.SavedLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SavedLocationRepository extends JpaRepository<SavedLocation, Long> {
}
