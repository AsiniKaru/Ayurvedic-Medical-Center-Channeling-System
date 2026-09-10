package lk.ijse.helaOsuWedaGedara.service;

import lk.ijse.helaOsuWedaGedara.dto.AuthDTO;

import lk.ijse.helaOsuWedaGedara.dto.UserDTO;

import java.util.List;

public interface UserService {
    AuthDTO login(AuthDTO authDTO);
    UserDTO getUserById(Long userId);
    List<UserDTO> getAllUsers();
    String deleteUser(Long userId);
}
