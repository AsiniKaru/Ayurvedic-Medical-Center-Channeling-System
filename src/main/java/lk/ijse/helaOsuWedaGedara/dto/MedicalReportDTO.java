package lk.ijse.helaOsuWedaGedara.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicalReportDTO {
    private Long medicalReportId;
    private String medicalReportName;
    private String reportUrl;
    private String reportDescription;
    private LocalDateTime uploadedAt;
    private Long patientId;
    private Long doctorId;
}
