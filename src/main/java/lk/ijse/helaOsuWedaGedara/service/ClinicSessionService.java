package lk.ijse.helaOsuWedaGedara.service;

import lk.ijse.helaOsuWedaGedara.dto.ClinicSessionDTO;

import java.time.LocalDate;
import java.util.List;

public interface ClinicSessionService {
    ClinicSessionDTO createSession(ClinicSessionDTO sessionDTO);
    ClinicSessionDTO updateSession(ClinicSessionDTO sessionDTO);
    String cancelSession(Long sessionId);
    ClinicSessionDTO getSessionById(Long sessionId);
    List<ClinicSessionDTO> getAllSessions();
    List<ClinicSessionDTO> getSessionsByDoctor(Long doctorId);
    List<ClinicSessionDTO> getActiveSessionsByDate(LocalDate date);
}
