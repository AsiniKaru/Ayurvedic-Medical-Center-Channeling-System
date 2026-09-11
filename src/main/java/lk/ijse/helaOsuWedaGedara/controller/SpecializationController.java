package lk.ijse.helaOsuWedaGedara.controller;

import lk.ijse.helaOsuWedaGedara.constant.CommonResponse;
import lk.ijse.helaOsuWedaGedara.dto.SpecializationDTO;
import lk.ijse.helaOsuWedaGedara.service.SpecializationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static lk.ijse.helaOsuWedaGedara.constant.ResponseMessage.SUCCESS_MESSAGE;
import static lk.ijse.helaOsuWedaGedara.constant.ResponseStatusCode.OPERATION_SUCCESS;

@RestController
@RequestMapping("api/v1/specializations")
@RequiredArgsConstructor
public class SpecializationController {
    private final SpecializationService specializationService;

    @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse saveSpecialization(@RequestBody SpecializationDTO dto) {
        SpecializationDTO saved = specializationService.saveSpecialization(dto);
        return new CommonResponse(OPERATION_SUCCESS, saved, SUCCESS_MESSAGE);
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse updateSpecialization(@RequestBody SpecializationDTO dto) {
        SpecializationDTO updated = specializationService.updateSpecialization(dto);
        return new CommonResponse(OPERATION_SUCCESS, updated, SUCCESS_MESSAGE);
    }

    @DeleteMapping(value = "/delete/{specializationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse deleteSpecialization(@PathVariable Long specializationId) {
        String result = specializationService.deleteSpecialization(specializationId);
        return new CommonResponse(OPERATION_SUCCESS, result, SUCCESS_MESSAGE);
    }

    @GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse getAllSpecializations() {
        List<SpecializationDTO> list = specializationService.getAllSpecializations();
        return new CommonResponse(OPERATION_SUCCESS, list, SUCCESS_MESSAGE);
    }

    @GetMapping(value = "/get/{specializationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse getSpecialization(@PathVariable Long specializationId) {
        SpecializationDTO dto = specializationService.getSpecializationById(specializationId);
        return new CommonResponse(OPERATION_SUCCESS, dto, SUCCESS_MESSAGE);
    }
}
