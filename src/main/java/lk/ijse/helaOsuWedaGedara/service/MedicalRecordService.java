package lk.ijse.helaOsuWedaGedara.service;

import lk.ijse.helaOsuWedaGedara.dto.MedicalRecordDTO;

import java.util.List;

public interface MedicalRecordService {
    MedicalRecordDTO addRecord(MedicalRecordDTO dto);
    MedicalRecordDTO getRecordByAppointment(Long appointmentId);
    List<MedicalRecordDTO> getRecordsByPatient(Long patientId);
}
