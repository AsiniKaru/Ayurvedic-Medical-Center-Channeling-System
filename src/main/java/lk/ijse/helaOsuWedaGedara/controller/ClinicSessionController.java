package lk.ijse.helaOsuWedaGedara.controller;

import lk.ijse.helaOsuWedaGedara.constant.CommonResponse;
import lk.ijse.helaOsuWedaGedara.dto.ClinicSessionDTO;
import lk.ijse.helaOsuWedaGedara.service.ClinicSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static lk.ijse.helaOsuWedaGedara.constant.ResponseMessage.SUCCESS_MESSAGE;
import static lk.ijse.helaOsuWedaGedara.constant.ResponseStatusCode.OPERATION_SUCCESS;

@RestController
@RequestMapping("api/v1/sessions")
@RequiredArgsConstructor
public class ClinicSessionController {
    private final ClinicSessionService sessionService;

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse createSession(@RequestBody ClinicSessionDTO sessionDTO) {
        ClinicSessionDTO saved = sessionService.createSession(sessionDTO);
        return new CommonResponse(OPERATION_SUCCESS, saved, SUCCESS_MESSAGE);
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse updateSession(@RequestBody ClinicSessionDTO sessionDTO) {
        ClinicSessionDTO updated = sessionService.updateSession(sessionDTO);
        return new CommonResponse(OPERATION_SUCCESS, updated, SUCCESS_MESSAGE);
    }

    @PutMapping(value = "/cancel/{sessionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse cancelSession(@PathVariable Long sessionId) {
        String result = sessionService.cancelSession(sessionId);
        return new CommonResponse(OPERATION_SUCCESS, result, SUCCESS_MESSAGE);
    }

    @GetMapping(value = "/get/{sessionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse getSessionById(@PathVariable Long sessionId) {
        ClinicSessionDTO dto = sessionService.getSessionById(sessionId);
        return new CommonResponse(OPERATION_SUCCESS, dto, SUCCESS_MESSAGE);
    }

    @GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse getAllSessions() {
        List<ClinicSessionDTO> list = sessionService.getAllSessions();
        return new CommonResponse(OPERATION_SUCCESS, list, SUCCESS_MESSAGE);
    }

    @GetMapping(value = "/doctor/{doctorId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse getSessionsByDoctor(@PathVariable Long doctorId) {
        List<ClinicSessionDTO> list = sessionService.getSessionsByDoctor(doctorId);
        return new CommonResponse(OPERATION_SUCCESS, list, SUCCESS_MESSAGE);
    }

    @GetMapping(value = "/by-date", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse getActiveSessionsByDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<ClinicSessionDTO> list = sessionService.getActiveSessionsByDate(date);
        return new CommonResponse(OPERATION_SUCCESS, list, SUCCESS_MESSAGE);
    }
}
