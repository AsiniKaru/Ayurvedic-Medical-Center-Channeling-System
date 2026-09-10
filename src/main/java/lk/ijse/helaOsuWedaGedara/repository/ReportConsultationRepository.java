package lk.ijse.helaOsuWedaGedara.repository;

import lk.ijse.helaOsuWedaGedara.entity.ReportConsultation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportConsultationRepository extends JpaRepository<ReportConsultation,Long> {
    List<ReportConsultation> findByReport_MedicalReportId(Long reportId);
}
