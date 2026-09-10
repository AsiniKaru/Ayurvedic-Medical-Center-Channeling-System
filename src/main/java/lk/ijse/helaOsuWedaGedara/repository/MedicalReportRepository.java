package lk.ijse.helaOsuWedaGedara.repository;

import lk.ijse.helaOsuWedaGedara.entity.MedicalRecord;
import lk.ijse.helaOsuWedaGedara.entity.MedicalReport;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicalReportRepository extends CrudRepository<MedicalRecord,Long> {
    List<MedicalReport> findByPatient_PatientId(Long patientId);
    List<MedicalReport> findByDoctor_DocId(Long docId);
}
