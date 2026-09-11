package lk.ijse.helaOsuWedaGedara.service;

import lk.ijse.helaOsuWedaGedara.dto.AppointmentDTO;

import java.util.List;

public interface AppointmentService {
    AppointmentDTO bookAppointment(AppointmentDTO dto);
    AppointmentDTO cancelAppointment(Long appointmentId);
    AppointmentDTO getAppointmentById(Long appointmentId);
    List<AppointmentDTO> getAppointmentsByPatient(Long patientId);
    List<AppointmentDTO> getAppointmentsBySession(Long sessionId);
    List<AppointmentDTO> getAllAppointments();
}
