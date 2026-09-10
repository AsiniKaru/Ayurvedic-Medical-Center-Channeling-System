package lk.ijse.helaOsuWedaGedara.controller;

import lk.ijse.helaOsuWedaGedara.constant.CommonResponse;
import lk.ijse.helaOsuWedaGedara.dto.AuthDTO;
import lk.ijse.helaOsuWedaGedara.dto.UserDTO;
import lk.ijse.helaOsuWedaGedara.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static lk.ijse.helaOsuWedaGedara.constant.ResponseMessage.SUCCESS_MESSAGE;
import static lk.ijse.helaOsuWedaGedara.constant.ResponseStatusCode.OPERATION_SUCCESS;

@RestController
@RequestMapping("api/v1/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse login(@RequestBody AuthDTO authDTO) {
        AuthDTO response = userService.login(authDTO);
        return new CommonResponse(OPERATION_SUCCESS, response, "Login successful");
    }

    @GetMapping(value = "/getUser/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse getUser(@PathVariable Long userId) {
        UserDTO userDTO = userService.getUserById(userId);
        return new CommonResponse(OPERATION_SUCCESS, userDTO, SUCCESS_MESSAGE);
    }

    @GetMapping(value = "/getAllUsers", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse getAllUsers() {
        List<UserDTO> userList = userService.getAllUsers();
        return new CommonResponse(OPERATION_SUCCESS, userList, SUCCESS_MESSAGE);
    }

    @DeleteMapping(value = "/deleteUser/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse deleteUser(@PathVariable Long userId) {
        String result = userService.deleteUser(userId);
        return new CommonResponse(OPERATION_SUCCESS, result, SUCCESS_MESSAGE);
    }
}
