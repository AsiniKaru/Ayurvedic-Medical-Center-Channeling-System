package lk.ijse.helaOsuWedaGedara.service.impl;

import jakarta.transaction.Transactional;
import lk.ijse.helaOsuWedaGedara.dto.DocAvailabilityDTO;
import lk.ijse.helaOsuWedaGedara.entity.DocAvailability;
import lk.ijse.helaOsuWedaGedara.entity.Doctor;
import lk.ijse.helaOsuWedaGedara.repository.DocAvailabilityRepository;
import lk.ijse.helaOsuWedaGedara.repository.DoctorRepository;
import lk.ijse.helaOsuWedaGedara.service.DocAvailabilityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class DocAvailabilityServiceImpl  implements DocAvailabilityService {
    private final DocAvailabilityRepository availabilityRepository;
    private final DoctorRepository doctorRepository;

    @Override
    public DocAvailabilityDTO setAvailability(DocAvailabilityDTO dto) {
        log.info("Execute Set Availability for Doctor ID: {}", dto.getDoctorId());

        Doctor doctor = doctorRepository.findById(dto.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found with ID: " + dto.getDoctorId()));

        DocAvailability availability = new DocAvailability();
        availability.setDoctor(doctor);
        availability.setAvailabilityDate(dto.getAvailabilityDate());
        availability.setStartTime(dto.getStartTime());
        availability.setEndTime(dto.getEndTime());
        availability.setIsActive(dto.getIsActive() != null ? dto.getIsActive() : true);

        DocAvailability saved = availabilityRepository.save(availability);
        dto.setAvailabilityId(saved.getAvailabilityId());
        return dto;
    }

    @Override
    public DocAvailabilityDTO updateAvailability(DocAvailabilityDTO dto) {
        log.info("Execute Update Availability ID: {}", dto.getAvailabilityId());

        DocAvailability availability = availabilityRepository.findById(dto.getAvailabilityId())
                .orElseThrow(() -> new RuntimeException("Availability slot not found with ID: " + dto.getAvailabilityId()));

        availability.setAvailabilityDate(dto.getAvailabilityDate());
        availability.setStartTime(dto.getStartTime());
        availability.setEndTime(dto.getEndTime());
        availability.setIsActive(dto.getIsActive());

        availabilityRepository.save(availability);
        return dto;
    }

    @Override
    public String deleteAvailability(Long availabilityId) {
        log.info("Execute Delete Availability ID: {}", availabilityId);
        if (!availabilityRepository.existsById(availabilityId)) {
            throw new RuntimeException("Availability slot not found with ID: " + availabilityId);
        }
        availabilityRepository.deleteById(availabilityId);
        return "Availability slot removed successfully!";
    }

    @Override
    public List<DocAvailabilityDTO> getAvailabilityByDoctor(Long doctorId) {
        List<DocAvailability> list = availabilityRepository.findAll();
        List<DocAvailabilityDTO> dtos = new ArrayList<>();
        for (DocAvailability a : list) {
            if (a.getDoctor().getDocId().equals(doctorId)) {
                dtos.add(new DocAvailabilityDTO(a.getAvailabilityId(), a.getAvailabilityDate(),
                        a.getStartTime(), a.getEndTime(), a.getIsActive(), a.getDoctor().getDocId()));
            }
        }
        return dtos;
    }
}
