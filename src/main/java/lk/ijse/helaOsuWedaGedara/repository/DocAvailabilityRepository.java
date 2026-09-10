package lk.ijse.helaOsuWedaGedara.repository;

import lk.ijse.helaOsuWedaGedara.entity.DocAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocAvailabilityRepository extends JpaRepository<DocAvailability,Long> {
    List<DocAvailability> findByDoctor_DocId(Long docId);
}
