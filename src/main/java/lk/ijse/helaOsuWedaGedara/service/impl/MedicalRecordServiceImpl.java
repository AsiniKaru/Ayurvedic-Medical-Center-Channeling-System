package lk.ijse.helaOsuWedaGedara.service.impl;

import jakarta.transaction.Transactional;
import lk.ijse.helaOsuWedaGedara.dto.MedicalRecordDTO;
import lk.ijse.helaOsuWedaGedara.entity.Appointment;
import lk.ijse.helaOsuWedaGedara.entity.MedicalRecord;
import lk.ijse.helaOsuWedaGedara.enumiration.AppointmentStatus;
import lk.ijse.helaOsuWedaGedara.repository.AppointmentRepository;
import lk.ijse.helaOsuWedaGedara.repository.MedicalRecordRepository;
import lk.ijse.helaOsuWedaGedara.service.MedicalRecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class MedicalRecordServiceImpl implements MedicalRecordService {
    private final MedicalRecordRepository medicalRecordRepository;
    private final AppointmentRepository appointmentRepository;

    @Override
    public MedicalRecordDTO addRecord(MedicalRecordDTO dto) {
        log.info("Execute Add Medical Record for Appointment ID: {}", dto.getAppointmentId());

        Appointment appointment = appointmentRepository.findById(dto.getAppointmentId())
                .orElseThrow(() -> new RuntimeException("Appointment not found with ID: " + dto.getAppointmentId()));

        MedicalRecord record = new MedicalRecord();
        record.setAppointment(appointment);
        record.setDiagnosis(dto.getDiagnosis());
        record.setClinicalNote(dto.getClinicalNote());
        record.setCreatedAt(LocalDateTime.now());

        // Update appointment status to COMPLETED
        appointment.setStatus(AppointmentStatus.COMPLETED);
        appointmentRepository.save(appointment);

        MedicalRecord saved = medicalRecordRepository.save(record);

        dto.setMedicalRecordId(saved.getMedicalRecordId());
        dto.setCreatedAt(saved.getCreatedAt());
        return dto;
    }

    @Override
    public MedicalRecordDTO getRecordByAppointment(Long appointmentId) {
        List<MedicalRecord> list = medicalRecordRepository.findAll();
        for (MedicalRecord r : list) {
            if (r.getAppointment().getAppointmentId().equals(appointmentId)) {
                return new MedicalRecordDTO(r.getMedicalRecordId(), r.getDiagnosis(),
                        r.getClinicalNote(), r.getCreatedAt(), r.getAppointment().getAppointmentId());
            }
        }
        throw new RuntimeException("Medical record not found for Appointment ID: " + appointmentId);
    }

    @Override
    public List<MedicalRecordDTO> getRecordsByPatient(Long patientId) {
        List<MedicalRecord> list = medicalRecordRepository.findAll();
        List<MedicalRecordDTO> dtos = new ArrayList<>();
        for (MedicalRecord r : list) {
            if (r.getAppointment().getPatient().getPatientId().equals(patientId)) {
                dtos.add(new MedicalRecordDTO(r.getMedicalRecordId(), r.getDiagnosis(),
                        r.getClinicalNote(), r.getCreatedAt(), r.getAppointment().getAppointmentId()));
            }
        }
        return dtos;
    }
}
