package lk.ijse.helaOsuWedaGedara.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomDTO {
    private Long roomId;
    private String roomNumber;
    private String roomType;
}
