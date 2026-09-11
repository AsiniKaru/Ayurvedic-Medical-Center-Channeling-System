package lk.ijse.helaOsuWedaGedara.service;

import lk.ijse.helaOsuWedaGedara.dto.RoomDTO;

import java.util.List;

public interface RoomService {
    RoomDTO saveRoom(RoomDTO dto);
    RoomDTO updateRoom(RoomDTO dto);
    String deleteRoom(Long roomId);
    List<RoomDTO> getAllRooms();
    RoomDTO getRoomById(Long roomId);
}
