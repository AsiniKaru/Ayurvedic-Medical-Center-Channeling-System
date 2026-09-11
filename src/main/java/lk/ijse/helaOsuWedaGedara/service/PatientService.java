package lk.ijse.helaOsuWedaGedara.service;

import lk.ijse.helaOsuWedaGedara.dto.PatientDTO;

import java.util.List;

public interface PatientService {
    PatientDTO registerPatient(PatientDTO patientDTO, String username, String password);
    PatientDTO updatePatient(PatientDTO patientDTO);
    PatientDTO getPatientById(Long patientId);
    PatientDTO getPatientByUserId(Long userId);
    List<PatientDTO> getAllPatients();
}
