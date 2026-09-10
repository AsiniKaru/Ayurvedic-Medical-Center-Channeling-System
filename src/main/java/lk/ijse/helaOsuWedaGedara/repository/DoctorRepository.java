package lk.ijse.helaOsuWedaGedara.repository;

import lk.ijse.helaOsuWedaGedara.entity.Doctor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends CrudRepository<Doctor,Long> {
    List<Doctor> findBySpecialization_SpecializationId(Long specializationId);
    Optional<Doctor> findByUser_UserId(Long userId);

}
