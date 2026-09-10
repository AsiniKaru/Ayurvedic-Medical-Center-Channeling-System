package lk.ijse.helaOsuWedaGedara.repository;

import lk.ijse.helaOsuWedaGedara.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient,Long> {
    Optional<Patient> findByUser_UserId(Long userId);
    Optional<Patient> findByPhoneNumber(String phoneNumber);
}
