package lk.ijse.helaOsuWedaGedara.service;

import lk.ijse.helaOsuWedaGedara.dto.SpecializationDTO;

import java.util.List;

public interface SpecializationService {
    SpecializationDTO saveSpecialization(SpecializationDTO dto);
    SpecializationDTO updateSpecialization(SpecializationDTO dto);
    String deleteSpecialization(Long specializationId);
    List<SpecializationDTO> getAllSpecializations();
    SpecializationDTO getSpecializationById(Long specializationId);
}
