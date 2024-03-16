package kz.sdu.edu.berkutapp.repository;

import kz.sdu.edu.berkutapp.model.AppUser;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByPhoneNumber(String phoneNumber);

    @Query(nativeQuery = true, value = "select * from app_user join public.user_relationship ur " +
            " on app_user.id = ur.parent_id\n" +
            "where ur.child_id = :childId")
    List<AppUser> getParentsByChildId(@Param("childId") Long childId);

    void deleteById(@NotNull Long id);
}
