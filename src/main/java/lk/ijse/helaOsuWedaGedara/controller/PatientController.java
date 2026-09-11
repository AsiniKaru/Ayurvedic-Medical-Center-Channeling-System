package lk.ijse.helaOsuWedaGedara.controller;

import lk.ijse.helaOsuWedaGedara.constant.CommonResponse;
import lk.ijse.helaOsuWedaGedara.dto.PatientDTO;
import lk.ijse.helaOsuWedaGedara.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static lk.ijse.helaOsuWedaGedara.constant.ResponseMessage.SUCCESS_MESSAGE;
import static lk.ijse.helaOsuWedaGedara.constant.ResponseStatusCode.OPERATION_SUCCESS;

@RestController
@RequestMapping("api/v1/patients")
@RequiredArgsConstructor
public class PatientController {
    private final PatientService patientService;

    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse registerPatient(@RequestBody PatientDTO patientDTO,
                                          @RequestParam String username,
                                          @RequestParam String password) {
        PatientDTO saved = patientService.registerPatient(patientDTO, username, password);
        return new CommonResponse(OPERATION_SUCCESS, saved, SUCCESS_MESSAGE);
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse updatePatient(@RequestBody PatientDTO patientDTO) {
        PatientDTO updated = patientService.updatePatient(patientDTO);
        return new CommonResponse(OPERATION_SUCCESS, updated, SUCCESS_MESSAGE);
    }

    @GetMapping(value = "/get/{patientId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse getPatientById(@PathVariable Long patientId) {
        PatientDTO dto = patientService.getPatientById(patientId);
        return new CommonResponse(OPERATION_SUCCESS, dto, SUCCESS_MESSAGE);
    }

    @GetMapping(value = "/user/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse getPatientByUserId(@PathVariable Long userId) {
        PatientDTO dto = patientService.getPatientByUserId(userId);
        return new CommonResponse(OPERATION_SUCCESS, dto, SUCCESS_MESSAGE);
    }

    @GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse getAllPatients() {
        List<PatientDTO> list = patientService.getAllPatients();
        return new CommonResponse(OPERATION_SUCCESS, list, SUCCESS_MESSAGE);
    }
}
