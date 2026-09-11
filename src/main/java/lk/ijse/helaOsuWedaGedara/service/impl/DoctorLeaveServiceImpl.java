package lk.ijse.helaOsuWedaGedara.service.impl;

import jakarta.transaction.Transactional;
import lk.ijse.helaOsuWedaGedara.dto.DoctorLeaveDTO;
import lk.ijse.helaOsuWedaGedara.entity.Doctor;
import lk.ijse.helaOsuWedaGedara.entity.DoctorLeave;
import lk.ijse.helaOsuWedaGedara.enumiration.LeaveStatus;
import lk.ijse.helaOsuWedaGedara.repository.DoctorLeaveRepository;
import lk.ijse.helaOsuWedaGedara.repository.DoctorRepository;
import lk.ijse.helaOsuWedaGedara.service.DoctorLeaveService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class DoctorLeaveServiceImpl implements DoctorLeaveService {
    private final DoctorLeaveRepository leaveRepository;
    private final DoctorRepository doctorRepository;

    @Override
    public DoctorLeaveDTO applyLeave(DoctorLeaveDTO dto) {
        log.info("Execute Apply Leave for Doctor ID: {}", dto.getDoctorId());

        Doctor doctor = doctorRepository.findById(dto.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found with ID: " + dto.getDoctorId()));

        DoctorLeave leave = new DoctorLeave();
        leave.setDoctor(doctor);
        leave.setLeaveStartDate(dto.getLeaveStartDate());
        leave.setLeaveEndDate(dto.getLeaveEndDate());
        leave.setNumOfLeaveDays(dto.getNumOfLeaveDays());
        leave.setReason(dto.getReason());
        leave.setStatus(LeaveStatus.PENDING);

        DoctorLeave saved = leaveRepository.save(leave);
        dto.setLeaveId(saved.getLeaveId());
        dto.setStatus(saved.getStatus());
        return dto;
    }

    @Override
    public DoctorLeaveDTO updateLeaveStatus(Long leaveId, LeaveStatus status) {
        log.info("Execute Update Leave ID: {} to Status: {}", leaveId, status);

        DoctorLeave leave = leaveRepository.findById(leaveId)
                .orElseThrow(() -> new RuntimeException("Leave record not found with ID: " + leaveId));

        leave.setStatus(status);
        DoctorLeave updated = leaveRepository.save(leave);

        return new DoctorLeaveDTO(updated.getLeaveId(), updated.getLeaveStartDate(),
                updated.getLeaveEndDate(), updated.getNumOfLeaveDays(), updated.getReason(),
                updated.getStatus(), updated.getDoctor().getDocId());
    }

    @Override
    public List<DoctorLeaveDTO> getLeavesByDoctor(Long doctorId) {
        List<DoctorLeave> list = leaveRepository.findAll();
        List<DoctorLeaveDTO> dtos = new ArrayList<>();
        for (DoctorLeave l : list) {
            if (l.getDoctor().getDocId().equals(doctorId)) {
                dtos.add(new DoctorLeaveDTO(l.getLeaveId(), l.getLeaveStartDate(),
                        l.getLeaveEndDate(), l.getNumOfLeaveDays(), l.getReason(),
                        l.getStatus(), l.getDoctor().getDocId()));
            }
        }
        return dtos;
    }

    @Override
    public List<DoctorLeaveDTO> getAllLeaves() {
        List<DoctorLeave> list = leaveRepository.findAll();
        List<DoctorLeaveDTO> dtos = new ArrayList<>();
        for (DoctorLeave l : list) {
            dtos.add(new DoctorLeaveDTO(l.getLeaveId(), l.getLeaveStartDate(),
                    l.getLeaveEndDate(), l.getNumOfLeaveDays(), l.getReason(),
                    l.getStatus(), l.getDoctor().getDocId()));
        }
        return dtos;
    }
}
