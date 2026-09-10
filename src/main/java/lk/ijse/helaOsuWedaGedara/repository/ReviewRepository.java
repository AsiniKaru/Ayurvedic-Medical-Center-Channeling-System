package lk.ijse.helaOsuWedaGedara.repository;

import lk.ijse.helaOsuWedaGedara.entity.Review;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends CrudRepository<Review,Long> {
    Optional<Review> findByAppointment_AppointmentId(Long appointmentId);
    List<Review> findByAppointment_ClinicSession_Doctor_DocId(Long docId);
}
