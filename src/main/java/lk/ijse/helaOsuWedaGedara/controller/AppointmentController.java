package lk.ijse.helaOsuWedaGedara.controller;

import lk.ijse.helaOsuWedaGedara.constant.CommonResponse;
import lk.ijse.helaOsuWedaGedara.dto.AppointmentDTO;
import lk.ijse.helaOsuWedaGedara.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static lk.ijse.helaOsuWedaGedara.constant.ResponseMessage.SUCCESS_MESSAGE;
import static lk.ijse.helaOsuWedaGedara.constant.ResponseStatusCode.OPERATION_SUCCESS;

@RestController
@RequestMapping("api/v1/appointments")
@RequiredArgsConstructor
public class AppointmentController {
    private final AppointmentService appointmentService;

    @PostMapping(value = "/book", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse bookAppointment(@RequestBody AppointmentDTO dto) {
        AppointmentDTO booked = appointmentService.bookAppointment(dto);
        return new CommonResponse(OPERATION_SUCCESS, booked, "Appointment booked successfully!");
    }

    @PutMapping(value = "/cancel/{appointmentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse cancelAppointment(@PathVariable Long appointmentId) {
        AppointmentDTO cancelled = appointmentService.cancelAppointment(appointmentId);
        return new CommonResponse(OPERATION_SUCCESS, cancelled, "Appointment cancelled successfully!");
    }

    @GetMapping(value = "/get/{appointmentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse getAppointmentById(@PathVariable Long appointmentId) {
        AppointmentDTO dto = appointmentService.getAppointmentById(appointmentId);
        return new CommonResponse(OPERATION_SUCCESS, dto, SUCCESS_MESSAGE);
    }

    @GetMapping(value = "/patient/{patientId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse getAppointmentsByPatient(@PathVariable Long patientId) {
        List<AppointmentDTO> list = appointmentService.getAppointmentsByPatient(patientId);
        return new CommonResponse(OPERATION_SUCCESS, list, SUCCESS_MESSAGE);
    }

    @GetMapping(value = "/session/{sessionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse getAppointmentsBySession(@PathVariable Long sessionId) {
        List<AppointmentDTO> list = appointmentService.getAppointmentsBySession(sessionId);
        return new CommonResponse(OPERATION_SUCCESS, list, SUCCESS_MESSAGE);
    }

    @GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse getAllAppointments() {
        List<AppointmentDTO> list = appointmentService.getAllAppointments();
        return new CommonResponse(OPERATION_SUCCESS, list, SUCCESS_MESSAGE);
    }
}
