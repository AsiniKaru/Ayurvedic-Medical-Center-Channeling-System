package lk.ijse.helaOsuWedaGedara.service;

import lk.ijse.helaOsuWedaGedara.dto.DoctorDTO;

import java.util.List;

public interface DoctorService {
    DoctorDTO registerDoctor(DoctorDTO doctorDTO, String username, String password, String email);
    DoctorDTO updateDoctor(DoctorDTO doctorDTO);
    DoctorDTO getDoctorById(Long docId);
    List<DoctorDTO> getAllDoctors();
    List<DoctorDTO> getDoctorsBySpecialization(Long specializationId);
}
