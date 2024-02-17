package kz.sdu.edu.berkutapp.repository;

import kz.sdu.edu.berkutapp.model.PinVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PinVerificationRepository extends JpaRepository<PinVerification, String> {

}
