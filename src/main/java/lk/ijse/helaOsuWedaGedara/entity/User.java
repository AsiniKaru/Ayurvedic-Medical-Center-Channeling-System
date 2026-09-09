package lk.ijse.helaOsuWedaGedara.entity;

import jakarta.persistence.*;
import lk.ijse.helaOsuWedaGedara.enumiration.ActiveStatus;
import lk.ijse.helaOsuWedaGedara.enumiration.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String username;
    private String password;
    private String email;
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private ActiveStatus activeStatus;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private Doctor doctor;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private Patient patient;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Notification> notificationList = new ArrayList<>();


}
