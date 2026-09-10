package lk.ijse.helaOsuWedaGedara.repository;

import lk.ijse.helaOsuWedaGedara.entity.ClinicSession;
import lk.ijse.helaOsuWedaGedara.enumiration.SessionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ClinicSessionRepository extends JpaRepository<ClinicSession,Long> {
    List<ClinicSession> findByDoctor_DocIdAndSessionDate(Long docId, LocalDate sessionDate);
    List<ClinicSession> findBySessionDateAndSessionStatus(LocalDate sessionDate, SessionStatus status);
}
