package lk.ijse.helaOsuWedaGedara.repository;

import lk.ijse.helaOsuWedaGedara.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomRepository  extends JpaRepository<Room,Long> {
    Optional<Room> findByRoomNumber(String roomNumber);
}
