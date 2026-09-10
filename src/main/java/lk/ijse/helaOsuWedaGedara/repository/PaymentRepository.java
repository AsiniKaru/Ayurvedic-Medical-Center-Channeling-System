package lk.ijse.helaOsuWedaGedara.repository;

import lk.ijse.helaOsuWedaGedara.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByAppointment_AppointmentId(Long appointmentId);
    Optional<Payment> findByTransactionRef(String transactionRef);
}
