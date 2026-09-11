package lk.ijse.helaOsuWedaGedara.service.impl;

import lk.ijse.helaOsuWedaGedara.dto.ClinicSessionDTO;
import lk.ijse.helaOsuWedaGedara.entity.ClinicSession;
import lk.ijse.helaOsuWedaGedara.entity.Doctor;
import lk.ijse.helaOsuWedaGedara.entity.Room;
import lk.ijse.helaOsuWedaGedara.enumiration.SessionStatus;
import lk.ijse.helaOsuWedaGedara.repository.ClinicSessionRepository;
import lk.ijse.helaOsuWedaGedara.repository.DoctorRepository;
import lk.ijse.helaOsuWedaGedara.repository.RoomRepository;
import lk.ijse.helaOsuWedaGedara.service.ClinicSessionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ClinicSessionServiceImpl implements ClinicSessionService {
    private final ClinicSessionRepository clinicSessionRepository;
    private final DoctorRepository doctorRepository;
    private final RoomRepository roomRepository;

    @Override
    public ClinicSessionDTO createSession(ClinicSessionDTO dto) {
        log.info("Execute Create Clinic Session for Doctor ID: {}", dto.getDoctorId());

        Doctor doctor = doctorRepository.findById(dto.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found with ID: " + dto.getDoctorId()));

        Room room = roomRepository.findById(dto.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found with ID: " + dto.getRoomId()));

        ClinicSession session = new ClinicSession();
        session.setDoctor(doctor);
        session.setRoom(room);
        session.setSessionDate(dto.getSessionDate());
        session.setStartTime(dto.getStartTime());
        session.setEndTime(dto.getEndTime());
        session.setMaxPatient(dto.getMaxPatient());
        session.setSessionStatus(SessionStatus.ACTIVE);

        ClinicSession saved = clinicSessionRepository.save(session);
        return mapToDTO(saved);
    }

    @Override
    public ClinicSessionDTO updateSession(ClinicSessionDTO dto) {
        log.info("Execute Update Clinic Session ID: {}", dto.getSessionId());

        ClinicSession session = clinicSessionRepository.findById(dto.getSessionId())
                .orElseThrow(() -> new RuntimeException("Clinic Session not found with ID: " + dto.getSessionId()));

        if (dto.getRoomId() != null) {
            Room room = roomRepository.findById(dto.getRoomId())
                    .orElseThrow(() -> new RuntimeException("Room not found with ID: " + dto.getRoomId()));
            session.setRoom(room);
        }

        session.setSessionDate(dto.getSessionDate());
        session.setStartTime(dto.getStartTime());
        session.setEndTime(dto.getEndTime());
        session.setMaxPatient(dto.getMaxPatient());

        if (dto.getSessionStatus() != null) {
            session.setSessionStatus(dto.getSessionStatus());
        }

        ClinicSession updated = clinicSessionRepository.save(session);
        return mapToDTO(updated);
    }

    @Override
    public String cancelSession(Long sessionId) {
        log.info("Execute Cancel Clinic Session ID: {}", sessionId);

        ClinicSession session = clinicSessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Clinic Session not found with ID: " + sessionId));

        session.setSessionStatus(SessionStatus.CANCELLED);
        clinicSessionRepository.save(session);
        return "Clinic Session cancelled successfully!";
    }

    @Override
    public ClinicSessionDTO getSessionById(Long sessionId) {
        ClinicSession session = clinicSessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Clinic Session not found with ID: " + sessionId));
        return mapToDTO(session);
    }

    @Override
    public List<ClinicSessionDTO> getAllSessions() {
        List<ClinicSession> list = clinicSessionRepository.findAll();
        List<ClinicSessionDTO> dtos = new ArrayList<>();
        for (ClinicSession session : list) {
            dtos.add(mapToDTO(session));
        }
        return dtos;
    }

    @Override
    public List<ClinicSessionDTO> getSessionsByDoctor(Long doctorId) {
        List<ClinicSession> list = clinicSessionRepository.findAll();
        List<ClinicSessionDTO> dtos = new ArrayList<>();
        for (ClinicSession s : list) {
            if (s.getDoctor().getDocId().equals(doctorId)) {
                dtos.add(mapToDTO(s));
            }
        }
        return dtos;
    }

    @Override
    public List<ClinicSessionDTO> getActiveSessionsByDate(LocalDate date) {
        List<ClinicSession> list = clinicSessionRepository.findAll();
        List<ClinicSessionDTO> dtos = new ArrayList<>();
        for (ClinicSession s : list) {
            if (s.getSessionDate().equals(date) && s.getSessionStatus() == SessionStatus.ACTIVE) {
                dtos.add(mapToDTO(s));
            }
        }
        return dtos;
    }

    private ClinicSessionDTO mapToDTO(ClinicSession session) {
        ClinicSessionDTO dto = new ClinicSessionDTO();
        dto.setSessionId(session.getSessionId());
        dto.setSessionDate(session.getSessionDate());
        dto.setStartTime(session.getStartTime());
        dto.setEndTime(session.getEndTime());
        dto.setMaxPatient(session.getMaxPatient());
        dto.setSessionStatus(session.getSessionStatus());
        dto.setDoctorId(session.getDoctor().getDocId());
        dto.setDoctorName(session.getDoctor().getFirstName() + " " + session.getDoctor().getLastName());
        dto.setRoomId(session.getRoom().getRoomId());
        dto.setRoomNumber(session.getRoom().getRoomNumber());
        return dto;
    }
}
