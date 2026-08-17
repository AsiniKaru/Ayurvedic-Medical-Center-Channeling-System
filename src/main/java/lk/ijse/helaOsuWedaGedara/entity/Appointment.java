package lk.ijse.helaOsuWedaGedara.entity;

import jakarta.persistence.*;
import lk.ijse.helaOsuWedaGedara.enumiration.AppointmentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer appointmentId;
    private Integer tokenNumber;
    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;
    private LocalDateTime createdAt;
}
