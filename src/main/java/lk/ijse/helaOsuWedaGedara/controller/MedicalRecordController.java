package lk.ijse.helaOsuWedaGedara.controller;

import lk.ijse.helaOsuWedaGedara.constant.CommonResponse;
import lk.ijse.helaOsuWedaGedara.dto.MedicalRecordDTO;
import lk.ijse.helaOsuWedaGedara.service.MedicalRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static lk.ijse.helaOsuWedaGedara.constant.ResponseMessage.SUCCESS_MESSAGE;
import static lk.ijse.helaOsuWedaGedara.constant.ResponseStatusCode.OPERATION_SUCCESS;

@RestController
@RequestMapping("api/v1/medical-records")
@RequiredArgsConstructor
public class MedicalRecordController {
    private final MedicalRecordService medicalRecordService;

    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse addRecord(@RequestBody MedicalRecordDTO dto) {
        MedicalRecordDTO saved = medicalRecordService.addRecord(dto);
        return new CommonResponse(OPERATION_SUCCESS, saved, "Medical record created and appointment marked completed!");
    }

    @GetMapping(value = "/appointment/{appointmentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse getRecordByAppointment(@PathVariable Long appointmentId) {
        MedicalRecordDTO dto = medicalRecordService.getRecordByAppointment(appointmentId);
        return new CommonResponse(OPERATION_SUCCESS, dto, SUCCESS_MESSAGE);
    }

    @GetMapping(value = "/patient/{patientId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse getRecordsByPatient(@PathVariable Long patientId) {
        List<MedicalRecordDTO> list = medicalRecordService.getRecordsByPatient(patientId);
        return new CommonResponse(OPERATION_SUCCESS, list, SUCCESS_MESSAGE);
    }
}
