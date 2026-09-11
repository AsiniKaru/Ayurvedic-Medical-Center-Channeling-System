package lk.ijse.helaOsuWedaGedara.service.impl;

import lk.ijse.helaOsuWedaGedara.dto.AppointmentDTO;
import lk.ijse.helaOsuWedaGedara.entity.Appointment;
import lk.ijse.helaOsuWedaGedara.entity.ClinicSession;
import lk.ijse.helaOsuWedaGedara.entity.Patient;
import lk.ijse.helaOsuWedaGedara.entity.Payment;
import lk.ijse.helaOsuWedaGedara.enumiration.AppointmentStatus;
import lk.ijse.helaOsuWedaGedara.enumiration.PaymentMethod;
import lk.ijse.helaOsuWedaGedara.enumiration.PaymentStatus;
import lk.ijse.helaOsuWedaGedara.enumiration.SessionStatus;
import lk.ijse.helaOsuWedaGedara.repository.AppointmentRepository;
import lk.ijse.helaOsuWedaGedara.repository.ClinicSessionRepository;
import lk.ijse.helaOsuWedaGedara.repository.PatientRepository;
import lk.ijse.helaOsuWedaGedara.repository.PaymentRepository;
import lk.ijse.helaOsuWedaGedara.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final ClinicSessionRepository clinicSessionRepository;
    private final PatientRepository patientRepository;
    private final PaymentRepository paymentRepository;

    private static final BigDecimal HOSPITAL_SERVICE_FEE = new BigDecimal("500.00");

    @Override
    public AppointmentDTO bookAppointment(AppointmentDTO dto) {
        log.info("Execute Book Appointment for Patient ID: {} and Session ID: {}", dto.getPatientId(), dto.getSessionId());

        ClinicSession session = clinicSessionRepository.findById(dto.getSessionId())
                .orElseThrow(() -> new RuntimeException("Clinic Session not found with ID: " + dto.getSessionId()));

        if (session.getSessionStatus() != SessionStatus.ACTIVE) {
            throw new RuntimeException("Cannot book appointment. Session is not ACTIVE.");
        }

        Patient patient = patientRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found with ID: " + dto.getPatientId()));

        // Count existing non-cancelled appointments for this session
        List<Appointment> existingAppointments = appointmentRepository.findAll();
        int bookedCount = 0;
        for (Appointment a : existingAppointments) {
            if (a.getClinicSession().getSessionId().equals(session.getSessionId())
                    && a.getStatus() != AppointmentStatus.CANCELLED) {
                bookedCount++;
            }
        }

        if (bookedCount >= session.getMaxPatient()) {
            throw new RuntimeException("Session is already full. No available slots.");
        }

        int nextQueueNumber = bookedCount + 1;

        // Calculate Total Amount: Doctor Fee + Hospital Fee
        BigDecimal docFee = BigDecimal.valueOf(session.getDoctor().getConsultationFee() != null ? session.getDoctor().getConsultationFee() : 0.0);
        BigDecimal totalCharge = docFee.add(HOSPITAL_SERVICE_FEE);

        Appointment appointment = new Appointment();
        appointment.setClinicSession(session);
        appointment.setPatient(patient);
        appointment.setAppointmentNumber(nextQueueNumber);
        appointment.setCreatedAt(LocalDateTime.now());
        appointment.setStatus(AppointmentStatus.COMPLETED);

        Appointment savedAppointment = appointmentRepository.save(appointment);

        // Record Mock Payment
        Payment payment = new Payment();
        payment.setAppointment(savedAppointment);
        payment.setAmount(totalCharge);
        payment.setTransactionRef("TXN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        payment.setPaymentMethod(PaymentMethod.CASH);
        payment.setPaymentStatus(PaymentStatus.SUCCESS);
        payment.setPaidAt(LocalDateTime.now());
        paymentRepository.save(payment);

        return mapToDTO(savedAppointment, totalCharge);
    }

    @Override
    public AppointmentDTO cancelAppointment(Long appointmentId) {
        log.info("Execute Cancel Appointment ID: {}", appointmentId);

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found with ID: " + appointmentId));

        appointment.setStatus(AppointmentStatus.CANCELLED);
        Appointment updated = appointmentRepository.save(appointment);

        return mapToDTO(updated, null);
    }

    @Override
    public AppointmentDTO getAppointmentById(Long appointmentId) {
        Appointment a = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found with ID: " + appointmentId));
        return mapToDTO(a, null);
    }

    @Override
    public List<AppointmentDTO> getAppointmentsByPatient(Long patientId) {
        List<Appointment> list = appointmentRepository.findAll();
        List<AppointmentDTO> dtos = new ArrayList<>();
        for (Appointment a : list) {
            if (a.getPatient().getPatientId().equals(patientId)) {
                dtos.add(mapToDTO(a, null));
            }
        }
        return dtos;
    }

    @Override
    public List<AppointmentDTO> getAppointmentsBySession(Long sessionId) {
        List<Appointment> list = appointmentRepository.findAll();
        List<AppointmentDTO> dtos = new ArrayList<>();
        for (Appointment a : list) {
            if (a.getClinicSession().getSessionId().equals(sessionId)) {
                dtos.add(mapToDTO(a, null));
            }
        }
        return dtos;
    }

    @Override
    public List<AppointmentDTO> getAllAppointments() {
        List<Appointment> list = appointmentRepository.findAll();
        List<AppointmentDTO> dtos = new ArrayList<>();
        for (Appointment a : list) {
            dtos.add(mapToDTO(a, null));
        }
        return dtos;
    }

    private AppointmentDTO mapToDTO(Appointment a, BigDecimal totalCharge) {
        AppointmentDTO dto = new AppointmentDTO();
        dto.setAppointmentId(a.getAppointmentId());
        dto.setAppointmentNumber(a.getAppointmentNumber());
        dto.setCreatedAt(a.getCreatedAt());
        dto.setStatus(a.getStatus());
        dto.setSessionId(a.getClinicSession().getSessionId());
        dto.setPatientId(a.getPatient().getPatientId());
        dto.setDoctorName(a.getClinicSession().getDoctor().getFirstName() + " " + a.getClinicSession().getDoctor().getLastName());
        dto.setPatientName(a.getPatient().getFirstName() + " " + a.getPatient().getLastName());
        return dto;
    }
}
