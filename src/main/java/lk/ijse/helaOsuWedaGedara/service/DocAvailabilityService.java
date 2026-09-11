package lk.ijse.helaOsuWedaGedara.service;

import lk.ijse.helaOsuWedaGedara.dto.DocAvailabilityDTO;

import java.util.List;

public interface DocAvailabilityService {
    DocAvailabilityDTO setAvailability(DocAvailabilityDTO dto);
    DocAvailabilityDTO updateAvailability(DocAvailabilityDTO dto);
    String deleteAvailability(Long availabilityId);
    List<DocAvailabilityDTO> getAvailabilityByDoctor(Long doctorId);
}
