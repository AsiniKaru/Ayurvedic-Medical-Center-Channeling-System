package lk.ijse.helaOsuWedaGedara.service.impl;

import lk.ijse.helaOsuWedaGedara.dto.SpecializationDTO;
import lk.ijse.helaOsuWedaGedara.entity.Specialization;
import lk.ijse.helaOsuWedaGedara.repository.SpecializationRepository;
import lk.ijse.helaOsuWedaGedara.service.SpecializationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class SpecializationServiceImpl implements SpecializationService {
    private final SpecializationRepository specializationRepository;

    @Override
    public SpecializationDTO saveSpecialization(SpecializationDTO dto) {
        log.info("Execute Save Specialization");
        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
            throw new RuntimeException("Specialization name cannot be empty!");
        }

        Specialization specialization = new Specialization();
        specialization.setSpecialization(dto.getName());
        specialization.setDescription(dto.getDescription());

        Specialization saved = specializationRepository.save(specialization);
        dto.setSpecializationId(saved.getSpecializationId());
        return dto;
    }

    @Override
    public SpecializationDTO updateSpecialization(SpecializationDTO dto) {
        log.info("Execute Update Specialization: {}", dto.getSpecializationId());
        Specialization specialization = specializationRepository.findById(dto.getSpecializationId())
                .orElseThrow(() -> new RuntimeException("Specialization not found with ID: " + dto.getSpecializationId()));

        specialization.setDescription(dto.getName());
        specialization.setDescription(dto.getDescription());

        specializationRepository.save(specialization);
        return dto;
    }

    @Override
    public String deleteSpecialization(Long specializationId) {
        log.info("Execute Delete Specialization: {}", specializationId);
        if (!specializationRepository.existsById(specializationId)) {
            throw new RuntimeException("Specialization not found with ID: " + specializationId);
        }
        specializationRepository.deleteById(specializationId);
        return "Specialization deleted successfully!";
    }

    @Override
    public List<SpecializationDTO> getAllSpecializations() {
        log.info("Execute Get All Specializations");
        List<Specialization> list = specializationRepository.findAll();
        List<SpecializationDTO> dtoList = new ArrayList<>();
        for (Specialization s : list) {
            dtoList.add(new SpecializationDTO(s.getSpecializationId(), s.getSpecialization(), s.getDescription()));
        }
        return dtoList;
    }

    @Override
    public SpecializationDTO getSpecializationById(Long specializationId) {
        log.info("Execute Get Specialization: {}", specializationId);
        Specialization s = specializationRepository.findById(specializationId)
                .orElseThrow(() -> new RuntimeException("Specialization not found with ID: " + specializationId));
        return new SpecializationDTO(s.getSpecializationId(), s.getSpecialization(), s.getDescription());
    }
}
