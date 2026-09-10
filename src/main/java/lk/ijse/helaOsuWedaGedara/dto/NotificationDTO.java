package lk.ijse.helaOsuWedaGedara.dto;

import lk.ijse.helaOsuWedaGedara.enumiration.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDTO {
    private Long notificationId;
    private NotificationType type;
    private String message;
    private LocalDateTime sendAt;
    private Long userId;
    private Long appointmentId;
}
