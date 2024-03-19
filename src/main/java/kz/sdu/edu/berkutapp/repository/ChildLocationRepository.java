package kz.sdu.edu.berkutapp.repository;

import kz.sdu.edu.berkutapp.model.AppUser;
import kz.sdu.edu.berkutapp.model.ChildLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChildLocationRepository extends JpaRepository<ChildLocation, Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM child_location WHERE child_id = :childId ORDER BY time DESC LIMIT 1")
    ChildLocation findByTimeDesc(@Param("childId") Long childId);

}
