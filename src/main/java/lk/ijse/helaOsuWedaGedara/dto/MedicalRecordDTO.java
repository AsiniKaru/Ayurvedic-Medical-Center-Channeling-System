package lk.ijse.helaOsuWedaGedara.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicalRecordDTO {
    private Long medicalRecordId;
    private String diagnosis;
    private String clinicalNote;
    private LocalDateTime createdAt;
    private Long appointmentId;
}
