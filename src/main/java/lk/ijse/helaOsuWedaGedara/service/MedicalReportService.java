package lk.ijse.helaOsuWedaGedara.service;

import lk.ijse.helaOsuWedaGedara.dto.MedicalReportDTO;
import lk.ijse.helaOsuWedaGedara.dto.ReportConsultationDTO;

import java.util.List;

public interface MedicalReportService {
    MedicalReportDTO uploadReport(MedicalReportDTO dto);
    ReportConsultationDTO reviewReport(ReportConsultationDTO dto);
    List<MedicalReportDTO> getReportsByPatient(Long patientId);
    List<MedicalReportDTO> getReportsByDoctor(Long doctorId);
}
