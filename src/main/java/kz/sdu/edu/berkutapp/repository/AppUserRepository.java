package kz.sdu.edu.berkutapp.repository;

import kz.sdu.edu.berkutapp.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByPhoneNumber(String phoneNumber);
}
