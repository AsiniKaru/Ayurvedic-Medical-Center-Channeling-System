package lk.ijse.helaOsuWedaGedara.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientDTO {
    private Long patientId;
    private String firstName;
    private String lastName;
    private LocalDate dob;
    private String gender;
    private String phoneNumber;
    private Long userId;
    private String email;

    public String getPatientName() {
        return firstName  + " " + lastName;
    }
}
