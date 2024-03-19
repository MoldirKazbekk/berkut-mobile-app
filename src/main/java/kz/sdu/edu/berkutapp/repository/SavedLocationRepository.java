package kz.sdu.edu.berkutapp.repository;

import kz.sdu.edu.berkutapp.model.SavedLocation;
import kz.sdu.edu.berkutapp.model.dto.SavedLocationDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SavedLocationRepository extends JpaRepository<SavedLocation, Long> {

    List<SavedLocation> findByParentId(Long parentId);
}
