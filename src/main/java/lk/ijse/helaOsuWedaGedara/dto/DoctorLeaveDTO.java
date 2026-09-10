package lk.ijse.helaOsuWedaGedara.dto;

import lk.ijse.helaOsuWedaGedara.enumiration.LeaveStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorLeaveDTO {
    private Long leaveId;
    private LocalDate leaveStartDate;
    private LocalDate leaveEndDate;
    private Integer numOfLeaveDays;
    private String reason;
    private LeaveStatus status;
    private Long doctorId;
}
