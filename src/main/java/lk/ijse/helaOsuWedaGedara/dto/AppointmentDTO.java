package lk.ijse.helaOsuWedaGedara.dto;

import lk.ijse.helaOsuWedaGedara.enumiration.AppointmentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDTO {
    private Long appointmentId;
    private Integer appointmentNumber;
    private LocalDateTime createdAt;
    private AppointmentStatus status;
    private Long sessionId;
    private Long patientId;
    private String patientName;
    private String doctorName;
}
