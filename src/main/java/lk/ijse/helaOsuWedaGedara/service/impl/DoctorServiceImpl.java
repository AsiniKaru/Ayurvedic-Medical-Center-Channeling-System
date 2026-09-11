package lk.ijse.helaOsuWedaGedara.service.impl;

import lk.ijse.helaOsuWedaGedara.dto.DoctorDTO;
import lk.ijse.helaOsuWedaGedara.entity.Doctor;
import lk.ijse.helaOsuWedaGedara.entity.Specialization;
import lk.ijse.helaOsuWedaGedara.entity.User;
import lk.ijse.helaOsuWedaGedara.enumiration.ActiveStatus;
import lk.ijse.helaOsuWedaGedara.enumiration.UserRole;
import lk.ijse.helaOsuWedaGedara.repository.DoctorRepository;
import lk.ijse.helaOsuWedaGedara.repository.SpecializationRepository;
import lk.ijse.helaOsuWedaGedara.repository.UserRepository;
import lk.ijse.helaOsuWedaGedara.service.DoctorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class DoctorServiceImpl  implements DoctorService {
    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;
    private final SpecializationRepository specializationRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public DoctorDTO registerDoctor(DoctorDTO doctorDTO, String username, String password, String email) {
        log.info("Execute Register Doctor: {}", username);

        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("Username already exists!");
        }
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already in use!");
        }

        Specialization spec = specializationRepository.findById(doctorDTO.getSpecializationId())
                .orElseThrow(() -> new RuntimeException("Specialization not found with ID: " + doctorDTO.getSpecializationId()));

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(UserRole.DOCTOR);
        user.setActiveStatus(ActiveStatus.ACTIVE);
        user.setCreatedAt(LocalDateTime.now());
        User savedUser = userRepository.save(user);

        Doctor doctor = new Doctor();
        doctor.setUser(savedUser);
        doctor.setFirstName(doctorDTO.getFirstName());
        doctor.setLastName(doctorDTO.getLastName());
        doctor.setPhoneNumber(doctorDTO.getPhoneNumber());
        doctor.setConsultationFee(doctorDTO.getConsultationFee());
        doctor.setBio(doctorDTO.getBio());
        doctor.setSpecialization(spec);

        Doctor savedDoctor = doctorRepository.save(doctor);

        doctorDTO.setDocId(savedDoctor.getDocId());
        doctorDTO.setUserId(savedUser.getUserId());
        doctorDTO.setSpecializationName(spec.getSpecialization());
        return doctorDTO;
    }

    @Override
    public DoctorDTO updateDoctor(DoctorDTO doctorDTO) {
        log.info("Execute Update Doctor ID: {}", doctorDTO.getDocId());
        Doctor doctor = doctorRepository.findById(doctorDTO.getDocId())
                .orElseThrow(() -> new RuntimeException("Doctor not found with ID: " + doctorDTO.getDocId()));

        if (doctorDTO.getSpecializationId() != null) {
            Specialization spec = specializationRepository.findById(doctorDTO.getSpecializationId())
                    .orElseThrow(() -> new RuntimeException("Specialization not found with ID: " + doctorDTO.getSpecializationId()));
            doctor.setSpecialization(spec);
        }

        doctor.setFirstName(doctorDTO.getFirstName());
        doctor.setLastName(doctorDTO.getLastName());
        doctor.setPhoneNumber(doctorDTO.getPhoneNumber());
        doctor.setConsultationFee(doctorDTO.getConsultationFee());
        doctor.setBio(doctorDTO.getBio());

        doctorRepository.save(doctor);
        return doctorDTO;
    }

    @Override
    public DoctorDTO getDoctorById(Long docId) {
        Doctor d = doctorRepository.findById(docId)
                .orElseThrow(() -> new RuntimeException("Doctor not found with ID: " + docId));

        return new DoctorDTO(d.getDocId(), d.getFirstName(), d.getLastName(), d.getPhoneNumber(),
                d.getConsultationFee(), d.getBio(), d.getUser().getUserId(),
                d.getSpecialization().getSpecializationId(), d.getSpecialization().getDescription());
    }

    @Override
    public List<DoctorDTO> getAllDoctors() {
        List<Doctor> list = doctorRepository.findAll();
        List<DoctorDTO> dtoList = new ArrayList<>();
        for (Doctor d : list) {
            dtoList.add(new DoctorDTO(d.getDocId(), d.getFirstName(), d.getLastName(), d.getPhoneNumber(),
                    d.getConsultationFee(), d.getBio(), d.getUser().getUserId(),
                    d.getSpecialization().getSpecializationId(), d.getSpecialization().getSpecialization()));
        }
        return dtoList;
    }

    @Override
    public List<DoctorDTO> getDoctorsBySpecialization(Long specializationId) {
        Specialization spec = specializationRepository.findById(specializationId)
                .orElseThrow(() -> new RuntimeException("Specialization not found!"));

        List<Doctor> list = doctorRepository.findAll();
        List<DoctorDTO> dtoList = new ArrayList<>();
        for (Doctor d : list) {
            if (d.getSpecialization().getSpecializationId().equals(specializationId)) {
                dtoList.add(new DoctorDTO(d.getDocId(), d.getFirstName(), d.getLastName(), d.getPhoneNumber(),
                        d.getConsultationFee(), d.getBio(), d.getUser().getUserId(),
                        d.getSpecialization().getSpecializationId(), d.getSpecialization().getSpecialization()));
            }
        }
        return dtoList;
    }
}
