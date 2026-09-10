package lk.ijse.helaOsuWedaGedara.repository;

import lk.ijse.helaOsuWedaGedara.entity.Appointment;
import lk.ijse.helaOsuWedaGedara.enumiration.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment,Long> {
    List<Appointment> findByPatient_PatientId(Long patientId);
    List<Appointment> findByClinicSession_SessionId(Long sessionId);
    List<Appointment> findByPatient_PatientIdAndStatus(Long patientId, AppointmentStatus status);
    Integer countByClinicSession_SessionId(Long sessionId);
}
