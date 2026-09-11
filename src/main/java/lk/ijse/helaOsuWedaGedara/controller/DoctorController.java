package lk.ijse.helaOsuWedaGedara.controller;

import lk.ijse.helaOsuWedaGedara.constant.CommonResponse;
import lk.ijse.helaOsuWedaGedara.dto.DoctorDTO;
import lk.ijse.helaOsuWedaGedara.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static lk.ijse.helaOsuWedaGedara.constant.ResponseMessage.SUCCESS_MESSAGE;
import static lk.ijse.helaOsuWedaGedara.constant.ResponseStatusCode.OPERATION_SUCCESS;

@RestController
@RequestMapping("api/v1/doctors")
@RequiredArgsConstructor
public class DoctorController {
    private final DoctorService doctorService;

    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse registerDoctor(@RequestBody DoctorDTO doctorDTO,
                                         @RequestParam String username,
                                         @RequestParam String password,
                                         @RequestParam String email) {
        DoctorDTO saved = doctorService.registerDoctor(doctorDTO, username, password, email);
        return new CommonResponse(OPERATION_SUCCESS, saved, SUCCESS_MESSAGE);
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse updateDoctor(@RequestBody DoctorDTO doctorDTO) {
        DoctorDTO updated = doctorService.updateDoctor(doctorDTO);
        return new CommonResponse(OPERATION_SUCCESS, updated, SUCCESS_MESSAGE);
    }

    @GetMapping(value = "/get/{docId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse getDoctorById(@PathVariable Long docId) {
        DoctorDTO dto = doctorService.getDoctorById(docId);
        return new CommonResponse(OPERATION_SUCCESS, dto, SUCCESS_MESSAGE);
    }

    @GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse getAllDoctors() {
        List<DoctorDTO> list = doctorService.getAllDoctors();
        return new CommonResponse(OPERATION_SUCCESS, list, SUCCESS_MESSAGE);
    }

    @GetMapping(value = "/specialization/{specId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse getDoctorsBySpecialization(@PathVariable Long specId) {
        List<DoctorDTO> list = doctorService.getDoctorsBySpecialization(specId);
        return new CommonResponse(OPERATION_SUCCESS, list, SUCCESS_MESSAGE);
    }
}
