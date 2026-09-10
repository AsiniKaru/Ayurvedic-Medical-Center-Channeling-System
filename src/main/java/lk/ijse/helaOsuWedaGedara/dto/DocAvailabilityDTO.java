package lk.ijse.helaOsuWedaGedara.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocAvailabilityDTO {
    private Long availabilityId;
    private LocalDate availabilityDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private Boolean isActive;
    private Long doctorId;
}
