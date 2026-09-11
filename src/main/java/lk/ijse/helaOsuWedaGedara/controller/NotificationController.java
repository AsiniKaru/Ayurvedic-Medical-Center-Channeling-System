package lk.ijse.helaOsuWedaGedara.controller;

import lk.ijse.helaOsuWedaGedara.constant.CommonResponse;
import lk.ijse.helaOsuWedaGedara.dto.NotificationDTO;
import lk.ijse.helaOsuWedaGedara.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static lk.ijse.helaOsuWedaGedara.constant.ResponseMessage.SUCCESS_MESSAGE;
import static lk.ijse.helaOsuWedaGedara.constant.ResponseStatusCode.OPERATION_SUCCESS;

@RestController
@RequestMapping("api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @PostMapping(value = "/send", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse sendNotification(@RequestBody NotificationDTO dto) {
        NotificationDTO saved = notificationService.sendNotification(dto);
        return new CommonResponse(OPERATION_SUCCESS, saved, "Notification dispatched successfully!");
    }

    @GetMapping(value = "/user/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse getNotificationsByUser(@PathVariable Long userId) {
        List<NotificationDTO> list = notificationService.getNotificationsByUser(userId);
        return new CommonResponse(OPERATION_SUCCESS, list, SUCCESS_MESSAGE);
    }
}
