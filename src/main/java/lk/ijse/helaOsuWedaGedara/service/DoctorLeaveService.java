package lk.ijse.helaOsuWedaGedara.service;

import lk.ijse.helaOsuWedaGedara.dto.DoctorLeaveDTO;
import lk.ijse.helaOsuWedaGedara.enumiration.LeaveStatus;

import java.util.List;

public interface DoctorLeaveService {
    DoctorLeaveDTO applyLeave(DoctorLeaveDTO dto);
    DoctorLeaveDTO updateLeaveStatus(Long leaveId, LeaveStatus status);
    List<DoctorLeaveDTO> getLeavesByDoctor(Long doctorId);
    List<DoctorLeaveDTO> getAllLeaves();
}
