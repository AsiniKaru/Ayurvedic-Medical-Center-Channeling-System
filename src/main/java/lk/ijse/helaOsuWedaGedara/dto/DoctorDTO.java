package lk.ijse.helaOsuWedaGedara.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorDTO {
    private Long docId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Double consultationFee;
    private String bio;
    private Long userId;
    private Long specializationId;
    private String specializationName;
}
