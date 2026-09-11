package lk.ijse.helaOsuWedaGedara.controller;

import lk.ijse.helaOsuWedaGedara.constant.CommonResponse;
import lk.ijse.helaOsuWedaGedara.dto.MedicalReportDTO;
import lk.ijse.helaOsuWedaGedara.dto.ReportConsultationDTO;
import lk.ijse.helaOsuWedaGedara.service.MedicalReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static lk.ijse.helaOsuWedaGedara.constant.ResponseMessage.SUCCESS_MESSAGE;
import static lk.ijse.helaOsuWedaGedara.constant.ResponseStatusCode.OPERATION_SUCCESS;

@RestController
@RequestMapping("api/v1/medical-reports")
@RequiredArgsConstructor
public class MedicalReportController {
    private final MedicalReportService reportService;

    @PostMapping(value = "/upload", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse uploadReport(@RequestBody MedicalReportDTO dto) {
        MedicalReportDTO saved = reportService.uploadReport(dto);
        return new CommonResponse(OPERATION_SUCCESS, saved, "Report uploaded successfully!");
    }

    @PostMapping(value = "/review", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse reviewReport(@RequestBody ReportConsultationDTO dto) {
        ReportConsultationDTO reviewed = reportService.reviewReport(dto);
        return new CommonResponse(OPERATION_SUCCESS, reviewed, "Report review submitted successfully!");
    }

    @GetMapping(value = "/patient/{patientId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse getReportsByPatient(@PathVariable Long patientId) {
        List<MedicalReportDTO> list = reportService.getReportsByPatient(patientId);
        return new CommonResponse(OPERATION_SUCCESS, list, SUCCESS_MESSAGE);
    }

    @GetMapping(value = "/doctor/{doctorId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse getReportsByDoctor(@PathVariable Long doctorId) {
        List<MedicalReportDTO> list = reportService.getReportsByDoctor(doctorId);
        return new CommonResponse(OPERATION_SUCCESS, list, SUCCESS_MESSAGE);

    }
}
