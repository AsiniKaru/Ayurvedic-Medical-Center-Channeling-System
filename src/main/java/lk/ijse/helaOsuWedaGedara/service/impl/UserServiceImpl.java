package lk.ijse.helaOsuWedaGedara.service.impl;

import lk.ijse.helaOsuWedaGedara.dto.AuthDTO;
import lk.ijse.helaOsuWedaGedara.dto.UserDTO;
import lk.ijse.helaOsuWedaGedara.entity.User;
import lk.ijse.helaOsuWedaGedara.enumiration.ActiveStatus;
import lk.ijse.helaOsuWedaGedara.enumiration.UserRole;
import lk.ijse.helaOsuWedaGedara.repository.UserRepository;
import lk.ijse.helaOsuWedaGedara.security.CustomUserDetailsService;
import lk.ijse.helaOsuWedaGedara.security.JwtUtil;
import lk.ijse.helaOsuWedaGedara.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder; // <--- 2. Declare field

    public UserServiceImpl(UserRepository userRepository,
                           AuthenticationManager authenticationManager,
                           CustomUserDetailsService userDetailsService,
                           JwtUtil jwtUtil,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;

    }
    @Override
    public UserDTO saveAdmin(UserDTO userDTO) {
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            throw new RuntimeException("Username already exists!");
        }
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new RuntimeException("Email already exists!");
        }

        User admin = new User();
        admin.setUsername(userDTO.getUsername());
        admin.setEmail(userDTO.getEmail());
        admin.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        admin.setRole(UserRole.ADMIN);
        admin.setActiveStatus(ActiveStatus.ACTIVE);
        admin.setCreatedAt(LocalDateTime.now());

        User saved = userRepository.save(admin);

        userDTO.setUserId(saved.getUserId());
        userDTO.setPassword(null);
        userDTO.setRole(saved.getRole());
        return userDTO;
    }


    @Override
    public AuthDTO login(AuthDTO authDTO) {
        log.info("Execute User Login for username: {}", authDTO.getUsername());

        if (authDTO.getUsername() == null || authDTO.getUsername().trim().isEmpty()) {
            throw new RuntimeException("Username cannot be empty!");
        }
        if (authDTO.getPassword() == null || authDTO.getPassword().trim().isEmpty()) {
            throw new RuntimeException("Password cannot be empty!");
        }

        // Authenticate credentials against SecurityConfig's AuthenticationManager
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authDTO.getUsername(), authDTO.getPassword())
        );

        User user = userRepository.findByUsername(authDTO.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found with username: " + authDTO.getUsername()));

        if (user.getActiveStatus() == ActiveStatus.SUSPENDED) {
            throw new RuntimeException("Account is suspended!");
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());


        if (user.getActiveStatus() == ActiveStatus.SUSPENDED) {
            throw new RuntimeException("Account is suspended!");
        }

// 1. Build UserDTO as expected by your JwtUtil
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(user.getUserId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setRole(user.getRole());
        userDTO.setStatus(user.getActiveStatus());

        String token = jwtUtil.generateToken(userDTO);

        AuthDTO response = new AuthDTO();
        response.setUsername(user.getUsername());
        response.setRole(user.getRole().name());
        response.setToken(token);
        return response;
    }

    @Override
    public UserDTO getUserById(Long userId) {
        log.info("Execute Get User by ID: {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        UserDTO dto = new UserDTO();
        dto.setUserId(user.getUserId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        dto.setStatus(user.getActiveStatus());
        dto.setCreatedAt(user.getCreatedAt());
        return dto;
    }

    @Override
    public List<UserDTO> getAllUsers() {
        log.info("Execute Get All Users");
        List<User> users = userRepository.findAll();
        List<UserDTO> userDTOList = new ArrayList<>();

        for (User user : users) {
            if ((user.getActiveStatus() == ActiveStatus.SUSPENDED) ||(user.getActiveStatus() == ActiveStatus.INACTIVE)) {
                continue;
            }
            UserDTO dto = new UserDTO();
            dto.setUserId(user.getUserId());
            dto.setUsername(user.getUsername());
            dto.setEmail(user.getEmail());
            dto.setRole(user.getRole());
            dto.setStatus(user.getActiveStatus());
            dto.setCreatedAt(user.getCreatedAt());
            userDTOList.add(dto);
        }
        return userDTOList;
    }

    @Override
    public String deleteUser(Long userId) {
        log.info("Execute Delete User: {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        user.setActiveStatus(ActiveStatus.INACTIVE);
        userRepository.save(user);
        return "User deactivated successfully!";
    }
}