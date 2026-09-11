package lk.ijse.helaOsuWedaGedara.service.impl;

import lk.ijse.helaOsuWedaGedara.dto.PatientDTO;
import lk.ijse.helaOsuWedaGedara.entity.Patient;
import lk.ijse.helaOsuWedaGedara.entity.User;
import lk.ijse.helaOsuWedaGedara.enumiration.ActiveStatus;
import lk.ijse.helaOsuWedaGedara.enumiration.UserRole;
import lk.ijse.helaOsuWedaGedara.repository.PatientRepository;
import lk.ijse.helaOsuWedaGedara.repository.UserRepository;
import lk.ijse.helaOsuWedaGedara.service.PatientService;
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
public class PatientServiceImpl implements PatientService {
    private final PatientRepository patientRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public PatientDTO registerPatient(PatientDTO patientDTO, String username, String password) {
        log.info("Execute Register Patient: {}", username);

        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("Username already exists!");
        }
        if (userRepository.existsByEmail(patientDTO.getEmail())) {
            throw new RuntimeException("Email already in use!");
        }

        User user = new User();
        user.setUsername(username);
        user.setEmail(patientDTO.getEmail());
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(UserRole.PATIENT);
        user.setActiveStatus(ActiveStatus.ACTIVE);
        user.setCreatedAt(LocalDateTime.now());
        User savedUser = userRepository.save(user);

        Patient patient = new Patient();
        patient.setUser(savedUser);
        patient.setFirstName(patientDTO.getFirstName());
        patient.setLastName(patientDTO.getLastName());
        patient.setPhoneNumber(patientDTO.getPhoneNumber());
        patient.setGender(patientDTO.getGender());
        patient.setDob(patientDTO.getDob());

        Patient savedPatient = patientRepository.save(patient);

        patientDTO.setPatientId(savedPatient.getPatientId());
        patientDTO.setUserId(savedUser.getUserId());
        return patientDTO;
    }

    @Override
    public PatientDTO updatePatient(PatientDTO patientDTO) {
        log.info("Execute Update Patient ID: {}", patientDTO.getPatientId());
        Patient patient = patientRepository.findById(patientDTO.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found with ID: " + patientDTO.getPatientId()));

        patient.setFirstName(patientDTO.getFirstName());
        patient.setLastName(patientDTO.getLastName());
        patient.setPhoneNumber(patientDTO.getPhoneNumber());
        patient.setGender(patientDTO.getGender());
        patient.setDob(patientDTO.getDob());

        patientRepository.save(patient);
        return patientDTO;
    }

    @Override
    public PatientDTO getPatientById(Long patientId) {
        Patient p = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found with ID: " + patientId));

        return new PatientDTO(p.getPatientId(), p.getFirstName(), p.getLastName(),
                p.getDob(), p.getGender(), p.getPhoneNumber(), p.getUser().getUserId(), p.getUser().getEmail());
    }

    @Override
    public PatientDTO getPatientByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        Patient p = user.getPatient();
        if (p == null) {
            throw new RuntimeException("No patient profile found for this user!");
        }

        return new PatientDTO(p.getPatientId(), p.getFirstName(), p.getLastName(),
                p.getDob(), p.getGender(), p.getPhoneNumber(), user.getUserId(), user.getEmail());
    }

    @Override
    public List<PatientDTO> getAllPatients() {
        List<Patient> list = patientRepository.findAll();
        List<PatientDTO> dtoList = new ArrayList<>();
        for (Patient p : list) {
            dtoList.add(new PatientDTO(p.getPatientId(), p.getFirstName(), p.getLastName(),
                    p.getDob(), p.getGender(), p.getPhoneNumber(), p.getUser().getUserId(), p.getUser().getEmail()));
        }
        return dtoList;
    }
}
