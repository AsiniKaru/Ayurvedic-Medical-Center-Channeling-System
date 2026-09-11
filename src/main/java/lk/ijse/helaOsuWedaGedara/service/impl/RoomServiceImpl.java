package lk.ijse.helaOsuWedaGedara.service.impl;

import lk.ijse.helaOsuWedaGedara.dto.RoomDTO;
import lk.ijse.helaOsuWedaGedara.entity.Room;
import lk.ijse.helaOsuWedaGedara.repository.RoomRepository;
import lk.ijse.helaOsuWedaGedara.service.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;

    @Override
    public RoomDTO saveRoom(RoomDTO dto) {
        log.info("Execute Save Room");
        if (dto.getRoomNumber() == null || dto.getRoomNumber().trim().isEmpty()) {
            throw new RuntimeException("Room number cannot be empty!");
        }

        Room room = new Room();
        room.setRoomNumber(dto.getRoomNumber());
        room.setRoomType(dto.getRoomType());

        Room saved = roomRepository.save(room);
        dto.setRoomId(saved.getRoomId());
        return dto;
    }

    @Override
    public RoomDTO updateRoom(RoomDTO dto) {
        log.info("Execute Update Room: {}", dto.getRoomId());
        Room room = roomRepository.findById(dto.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found with ID: " + dto.getRoomId()));

        room.setRoomNumber(dto.getRoomNumber());
        room.setRoomType(dto.getRoomType());

        roomRepository.save(room);
        return dto;
    }

    @Override
    public String deleteRoom(Long roomId) {
        log.info("Execute Delete Room: {}", roomId);
        if (!roomRepository.existsById(roomId)) {
            throw new RuntimeException("Room not found with ID: " + roomId);
        }
        roomRepository.deleteById(roomId);
        return "Room deleted successfully!";
    }

    @Override
    public List<RoomDTO> getAllRooms() {
        log.info("Execute Get All Rooms");
        List<Room> list = roomRepository.findAll();
        List<RoomDTO> dtoList = new ArrayList<>();
        for (Room r : list) {
            dtoList.add(new RoomDTO(r.getRoomId(), r.getRoomNumber(), r.getRoomType()));
        }
        return dtoList;
    }

    @Override
    public RoomDTO getRoomById(Long roomId) {
        log.info("Execute Get Room: {}", roomId);
        Room r = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found with ID: " + roomId));
        return new RoomDTO(r.getRoomId(), r.getRoomNumber(), r.getRoomType());
    }
}
