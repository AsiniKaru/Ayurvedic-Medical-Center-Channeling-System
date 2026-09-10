package lk.ijse.helaOsuWedaGedara.dto;

import lk.ijse.helaOsuWedaGedara.enumiration.ActiveStatus;
import lk.ijse.helaOsuWedaGedara.enumiration.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long userId;
    private String username;
    private String password;
    private String email;
    private UserRole role;
    private ActiveStatus status;
    private LocalDateTime createdAt;
}
