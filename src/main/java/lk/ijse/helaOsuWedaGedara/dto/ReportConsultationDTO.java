package lk.ijse.helaOsuWedaGedara.dto;

import lk.ijse.helaOsuWedaGedara.enumiration.ReportStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportConsultationDTO {
    private Long reportConId;
    private String doctorReply;
    private LocalDateTime reviewedAt;
    private ReportStatus reportStatus;
    private Long reportId;
}
