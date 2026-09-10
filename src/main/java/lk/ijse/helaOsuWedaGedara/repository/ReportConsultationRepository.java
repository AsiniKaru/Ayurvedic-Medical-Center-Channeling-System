package lk.ijse.helaOsuWedaGedara.repository;

import lk.ijse.helaOsuWedaGedara.entity.MedicalRecord;
import lk.ijse.helaOsuWedaGedara.entity.ReportConsultation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportConsultationRepository extends CrudRepository<MedicalRecord,Long> {
    List<ReportConsultation> findByReport_MedicalReportId(Long reportId);
}
