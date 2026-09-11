package lk.ijse.helaOsuWedaGedara.service.impl;


import jakarta.transaction.Transactional;
import lk.ijse.helaOsuWedaGedara.dto.MedicalReportDTO;
import lk.ijse.helaOsuWedaGedara.dto.ReportConsultationDTO;
import lk.ijse.helaOsuWedaGedara.entity.Doctor;
import lk.ijse.helaOsuWedaGedara.entity.MedicalReport;
import lk.ijse.helaOsuWedaGedara.entity.Patient;
import lk.ijse.helaOsuWedaGedara.entity.ReportConsultation;
import lk.ijse.helaOsuWedaGedara.enumiration.ReportStatus;
import lk.ijse.helaOsuWedaGedara.repository.DoctorRepository;
import lk.ijse.helaOsuWedaGedara.repository.MedicalReportRepository;
import lk.ijse.helaOsuWedaGedara.repository.PatientRepository;
import lk.ijse.helaOsuWedaGedara.repository.ReportConsultationRepository;
import lk.ijse.helaOsuWedaGedara.service.MedicalReportService;
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
public class MedicalReportServiceImpl implements MedicalReportService {
    private final MedicalReportRepository reportRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final ReportConsultationRepository consultationRepository;

    @Override
    public MedicalReportDTO uploadReport(MedicalReportDTO dto) {
        log.info("Execute Upload Medical Report for Patient ID: {}", dto.getPatientId());

        Patient patient = patientRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found with ID: " + dto.getPatientId()));

        Doctor doctor = doctorRepository.findById(dto.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found with ID: " + dto.getDoctorId()));

        MedicalReport report = new MedicalReport();
        report.setPatient(patient);
        report.setDoctor(doctor);
        report.setMedicalReportName(dto.getMedicalReportName());
        report.setReportUrl(dto.getReportUrl());
        report.setReportDescription(dto.getReportDescription());
        report.setUploadedAt(LocalDateTime.now());

        MedicalReport saved = reportRepository.save(report);
        dto.setMedicalReportId(saved.getMedicalReportId());
        dto.setUploadedAt(saved.getUploadedAt());
        return dto;
    }

    @Override
    public ReportConsultationDTO reviewReport(ReportConsultationDTO dto) {
        log.info("Execute Review Report ID: {}", dto.getReportId());

        MedicalReport report = reportRepository.findById(dto.getReportId())
                .orElseThrow(() -> new RuntimeException("Medical Report not found with ID: " + dto.getReportId()));

        ReportConsultation consultation = new ReportConsultation();
        consultation.setReport(report);
        consultation.setDoctorReply(dto.getDoctorReply());
        consultation.setReviewedAt(LocalDateTime.now());
        consultation.setReportStatus(dto.getReportStatus() != null ? dto.getReportStatus() : ReportStatus.REPLIED);

        ReportConsultation saved = consultationRepository.save(consultation);

        dto.setReportConId(saved.getReportConId());
        dto.setReviewedAt(saved.getReviewedAt());
        dto.setReportStatus(saved.getReportStatus());
        return dto;
    }

    @Override
    public List<MedicalReportDTO> getReportsByPatient(Long patientId) {
        List<MedicalReport> list = reportRepository.findAll();
        List<MedicalReportDTO> dtos = new ArrayList<>();
        for (MedicalReport r : list) {
            if (r.getPatient().getPatientId().equals(patientId)) {
                dtos.add(new MedicalReportDTO(r.getMedicalReportId(), r.getMedicalReportName(),
                        r.getReportUrl(), r.getReportDescription(), r.getUploadedAt(),
                        r.getPatient().getPatientId(), r.getDoctor().getDocId()));
            }
        }
        return dtos;
    }

    @Override
    public List<MedicalReportDTO> getReportsByDoctor(Long doctorId) {
        List<MedicalReport> list = reportRepository.findAll();
        List<MedicalReportDTO> dtos = new ArrayList<>();
        for (MedicalReport r : list) {
            if (r.getDoctor().getDocId().equals(doctorId)) {
                dtos.add(new MedicalReportDTO(r.getMedicalReportId(), r.getMedicalReportName(),
                        r.getReportUrl(), r.getReportDescription(), r.getUploadedAt(),
                        r.getPatient().getPatientId(), r.getDoctor().getDocId()));
            }
        }
        return dtos;
    }
}
