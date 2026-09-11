package lk.ijse.helaOsuWedaGedara.service.impl;

import jakarta.transaction.Transactional;
import lk.ijse.helaOsuWedaGedara.dto.NotificationDTO;
import lk.ijse.helaOsuWedaGedara.entity.Appointment;
import lk.ijse.helaOsuWedaGedara.entity.Notification;
import lk.ijse.helaOsuWedaGedara.entity.User;
import lk.ijse.helaOsuWedaGedara.repository.AppointmentRepository;
import lk.ijse.helaOsuWedaGedara.repository.NotificationRepository;
import lk.ijse.helaOsuWedaGedara.repository.UserRepository;
import lk.ijse.helaOsuWedaGedara.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final AppointmentRepository appointmentRepository;

    @Override
    public NotificationDTO sendNotification(NotificationDTO dto) {
        log.info("Execute Send Notification to User ID: {}", dto.getUserId());

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + dto.getUserId()));

        Notification notification = new Notification();
        notification.setUser(user);
        notification.setType(dto.getType());
        notification.setMessage(dto.getMessage());
        notification.setSendAt(LocalDateTime.now());

        if (dto.getAppointmentId() != null) {
            Appointment appointment = appointmentRepository.findById(dto.getAppointmentId()).orElse(null);
            notification.setAppointment(appointment);
        }

        Notification saved = notificationRepository.save(notification);

        dto.setNotificationId(saved.getNotificationId());
        dto.setSendAt(saved.getSendAt());
        return dto;
    }

    @Override
    public List<NotificationDTO> getNotificationsByUser(Long userId) {
        List<Notification> list = notificationRepository.findAll();
        List<NotificationDTO> dtos = new ArrayList<>();
        for (Notification n : list) {
            if (n.getUser().getUserId().equals(userId)) {
                Long appointmentId = n.getAppointment() != null ? n.getAppointment().getAppointmentId() : null;
                dtos.add(new NotificationDTO(n.getNotificationId(), n.getType(), n.getMessage(),
                        n.getSendAt(), n.getUser().getUserId(), appointmentId));
            }
        }
        return dtos;
    }
}
