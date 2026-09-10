package lk.ijse.helaOsuWedaGedara.repository;

import lk.ijse.helaOsuWedaGedara.entity.DoctorLeave;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DoctorLeaveRpository extends CrudRepository<DoctorLeave,Long> {
    List<DoctorLeave> findByDoctor_DocId(Long docId);
    List<DoctorLeave> findByDoctor_DocIdAndLeaveStartDateLessThanEqualAndLeaveEndDateGreaterThanEqual(
            Long docId, LocalDate date1, LocalDate date2
    );
}
