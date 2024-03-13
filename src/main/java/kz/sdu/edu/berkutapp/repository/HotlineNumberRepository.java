package kz.sdu.edu.berkutapp.repository;

import kz.sdu.edu.berkutapp.model.HotlineNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotlineNumberRepository extends JpaRepository<HotlineNumber, Long> {
    void deleteByPhoneNumberAndNameAndChild_Id(String phoneNumber, String name, Long childId);
    List<HotlineNumber> findNumbersByChildId(Long childId);
}
