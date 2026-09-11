package lk.ijse.helaOsuWedaGedara.controller;

import lk.ijse.helaOsuWedaGedara.constant.CommonResponse;
import lk.ijse.helaOsuWedaGedara.dto.DoctorLeaveDTO;
import lk.ijse.helaOsuWedaGedara.enumiration.LeaveStatus;
import lk.ijse.helaOsuWedaGedara.service.DoctorLeaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static lk.ijse.helaOsuWedaGedara.constant.ResponseMessage.SUCCESS_MESSAGE;
import static lk.ijse.helaOsuWedaGedara.constant.ResponseStatusCode.OPERATION_SUCCESS;

@RestController
@RequestMapping("api/v1/doctor-leaves")
@RequiredArgsConstructor
public class DoctorLeaveController {
    private final DoctorLeaveService leaveService;

    @PostMapping(value = "/apply", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse applyLeave(@RequestBody DoctorLeaveDTO dto) {
        DoctorLeaveDTO saved = leaveService.applyLeave(dto);
        return new CommonResponse(OPERATION_SUCCESS, saved, "Leave application submitted successfully!");
    }

    @PutMapping(value = "/status/{leaveId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse updateStatus(@PathVariable Long leaveId, @RequestParam LeaveStatus status) {
        DoctorLeaveDTO updated = leaveService.updateLeaveStatus(leaveId, status);
        return new CommonResponse(OPERATION_SUCCESS, updated, "Leave status updated successfully!");
    }

    @GetMapping(value = "/doctor/{doctorId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse getByDoctor(@PathVariable Long doctorId) {
        List<DoctorLeaveDTO> list = leaveService.getLeavesByDoctor(doctorId);
        return new CommonResponse(OPERATION_SUCCESS, list, SUCCESS_MESSAGE);
    }

    @GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse getAllLeaves() {
        List<DoctorLeaveDTO> list = leaveService.getAllLeaves();
        return new CommonResponse(OPERATION_SUCCESS, list, SUCCESS_MESSAGE);
    }
}
