package lk.ijse.helaOsuWedaGedara.controller;

import lk.ijse.helaOsuWedaGedara.constant.CommonResponse;
import lk.ijse.helaOsuWedaGedara.dto.DocAvailabilityDTO;
import lk.ijse.helaOsuWedaGedara.service.DocAvailabilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static lk.ijse.helaOsuWedaGedara.constant.ResponseMessage.SUCCESS_MESSAGE;
import static lk.ijse.helaOsuWedaGedara.constant.ResponseStatusCode.OPERATION_SUCCESS;

@RestController
@RequestMapping("api/v1/doctor-availability")
@RequiredArgsConstructor
public class DocAvailabilityController {
    private final DocAvailabilityService availabilityService;

    @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse saveAvailability(@RequestBody DocAvailabilityDTO dto) {
        DocAvailabilityDTO saved = availabilityService.setAvailability(dto);
        return new CommonResponse(OPERATION_SUCCESS, saved, SUCCESS_MESSAGE);
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse updateAvailability(@RequestBody DocAvailabilityDTO dto) {
        DocAvailabilityDTO updated = availabilityService.updateAvailability(dto);
        return new CommonResponse(OPERATION_SUCCESS, updated, SUCCESS_MESSAGE);
    }

    @DeleteMapping(value = "/delete/{availabilityId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse deleteAvailability(@PathVariable Long availabilityId) {
        String result = availabilityService.deleteAvailability(availabilityId);
        return new CommonResponse(OPERATION_SUCCESS, result, SUCCESS_MESSAGE);
    }

    @GetMapping(value = "/doctor/{doctorId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse getByDoctor(@PathVariable Long doctorId) {
        List<DocAvailabilityDTO> list = availabilityService.getAvailabilityByDoctor(doctorId);
        return new CommonResponse(OPERATION_SUCCESS, list, SUCCESS_MESSAGE);
    }
}
